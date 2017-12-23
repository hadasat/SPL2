package bgu.spl.a2;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the action result type
 */
public abstract class Action<R> {

    protected callback finalCallBack;
    protected Promise<R> promise;
    protected ConcurrentLinkedQueue <Action> subActions;
    protected int numSubAction;
    protected String actorID;
    protected PrivateState actorPS;
    protected AtomicBoolean hasStarted = new AtomicBoolean(false);
    protected ActorThreadPool pool;
	/**.
     * start handling the action - note that this method is protected, a thread
     * cannot call it directly.
     */
    protected abstract void start();
    

    /**
    *
    * start/continue handling the action
    *
    * this method should be called in order to start this action
    * or continue its execution in the case where it has been already started.
    *
    * IMPORTANT: this method is package protected, i.e., only classes inside
    * the same package can access it - you should *not* change it to
    * public/private/protected
    *
    */
   /*package*/ final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {
        if(hasStarted.compareAndSet(false,true)){
            this.subActions = new ConcurrentLinkedQueue<>();
            this.actorID = actorId;
            this.actorPS = actorState;
            this.pool = pool;
            this.start();
        }
        else{
            finalCallBack.call();
        }
    }
    
    
    /**
     * add a callback to be executed once *all* the given actions results are
     * resolved
     * 
     * Implementors note: make sure that the callback is running only once when
     * all the given actions completed.
     *
     * @param actions
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void then(Collection<? extends Action<?>> actions, callback callback) {
       	finalCallBack = callback;
       	if(actions == null || actions.size() == 0) {
            finalCallBack.call();
            return;
        }
        for (Action ac : actions) {
            ac.promise.subscribe(this::down);
        }
    }

    public synchronized void down(){
            numSubAction--;
            if(numSubAction <= 0){
                pool.submit(this, actorID, actorPS);
            }
    }

    /**
     * resolve the internal result - should be called by the action derivative
     * once it is done.
     *
     * @param result - the action calculated result
     */

    protected final void complete(R result) {
       	promise.resolve(result);
    }
    
    /**
     * @return action's promise (result)
     */
    public final Promise<R> getResult() {
    	return promise;
    }
    
    /**
     * send an action to an other actor
     * 
     * @param action
     * 				the action
     * @param actorId
     * 				actor's id
     * @param actorState
	 * 				actor's private state (actor's information)
	 *    
     * @return promise that will hold the result of the sent action
     */
	public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState){
        pool.submit(action,actorId,actorState);
        return action.promise;
	}

    public Promise<R> getPromise() {
        return promise;
    }

    public void subscribe(callback c){
	    promise.subscribe(c);
    }
}
