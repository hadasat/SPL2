package bgu.spl.a2;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {
	private  VersionMonitor version;
	private Runnable runnable;
	private int size;
	private ConcurrentHashMap<String,ConcurrentLinkedQueue<Action>> actors;
	private ConcurrentHashMap<String,PrivateState> data;
	private ConcurrentHashMap<String,AtomicBoolean> avilableActor;
	private Thread[] threads;
	private AtomicBoolean entity;
	private boolean shutDown;

	public Map<String,ConcurrentLinkedQueue<bgu.spl.a2.Action>> getActor(){return actors;}
	public Map<String,PrivateState> getData(){return data;}
	public Thread[] getThreads(){return threads;}


	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads) {
		shutDown = false;
		version = new VersionMonitor();
		version.addActorThreadPool(this);
		size = nthreads;
		actors = new ConcurrentHashMap();
		data = new ConcurrentHashMap();
		avilableActor = new ConcurrentHashMap<>();
		threads = new Thread[nthreads];
		runnable = new LikeAThread(this);
		entity = new AtomicBoolean(false);
		for(int i=0; i<nthreads ; i++){
			Thread t = new Thread(runnable);
			threads[i] = t;
		}
	}


	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {

		for (Map.Entry<String, ConcurrentLinkedQueue<Action>> entry : actors.entrySet())
		{
			if(entry.getKey().equals(actorId)){
				entry.getValue().add(action);
				version.inc();
				return;
			}
		}
		ConcurrentLinkedQueue<Action> q = new ConcurrentLinkedQueue();
		q.add(action);
		actors.put(actorId, q);
		data.put(actorId, actorState);
		avilableActor.put(actorId, new AtomicBoolean(true));
		version.inc();
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	synchronized public void shutdown() throws InterruptedException {
		shutDown = true;
		version.inc();
		for (int i = 0; i < threads.length; i ++){
			threads[i].join();
		}
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		if (entity.compareAndSet(false, true)) {
			for (int i = 0; i < threads.length; i++) {
				threads[i].start();
			}
		}
	}

	public void execute() {
		while(!shutDown) {
			boolean wait = false;
			for (Map.Entry<String, AtomicBoolean> entry : avilableActor.entrySet()) {
				if (entry.getValue().compareAndSet(true, false)) {
					ConcurrentLinkedQueue<Action> q = actors.get(entry.getKey());
					PrivateState p = data.get(entry.getKey());
					Action a = q.poll();
					if(a != null) a.handle(this, entry.getKey(), p);
					entry.getValue().compareAndSet(false, true);
					version.inc();
					wait = true;
				}
			}
			if (!wait) {
				try {
					version.await(version.getVersion());
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public PrivateState getPrivateState(String key){
		return data.get(key);
	}

}

