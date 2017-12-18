package bgu.spl.a2;

/**
 * Describes a monitor that supports the concept of versioning - its idea is
 * simple, the monitor has a version number which you can receive via the method
 * {@link #getVersion()} once you have a version number, you can call
 * {@link #await(int)} with this version number in order to wait until this
 * version number changes.
 *
 * you can also increment the version number by one using the {@link #inc()}
 * method.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class VersionMonitor {
    private ActorThreadPool pool;
    private int version;
    private final  Object sync = new Object();
    int await;

    public VersionMonitor(){
        pool = null;
        await = 0;
        version = (0);
    }

    public void addActorThreadPool(ActorThreadPool pool){

        this.pool = pool;
    }

    public int getVersion() {
        return version;
    }

    public void inc() {
        synchronized (sync){
            version++;
            await = 0;
            sync.notifyAll();
        }
    }

    public void await(int version) throws InterruptedException {
        synchronized (sync) {
            if(await == pool.getThreads().length-1) {
                while (this.version == (version))
                {
                    await++;
                    sync.wait();
                }
            }
        }
    }
}
