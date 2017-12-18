package bgu.spl.a2.sim;
import bgu.spl.a2.Promise;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	Computer computer;
	AtomicBoolean flag;
	Queue<Promise> promises;

	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		this.computer = computer;
		flag = new AtomicBoolean(true);
		promises = new ArrayDeque<>();
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(){
		if(!flag.compareAndSet(true, false)){
			Promise newPromise = new Promise();
			newPromise.subscribe(()->{});
			promises.add(newPromise);
			return newPromise;
		}
		Promise<Computer> prom = new Promise<>();
		prom.resolve(computer);
		return prom;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){
		if(!flag.compareAndSet(false, true))
			throw new RuntimeException("already released kapara");
		for (Promise pro : promises){
			if(!pro.isResolved())
				pro.resolve(computer);
		}
		promises.clear();
	}

	public Computer getComputer() {
		return computer;
	}
}
