package bgu.spl.a2.sim;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {
    /*package*/Collection<SuspendingMutex> suspendings;
    Integer size;


    public Warehouse(Collection<Computer> col){
        size = col.size();
        suspendings = new ConcurrentLinkedQueue<>();
        for(Computer com : col){
            suspendings.add(new SuspendingMutex(com));
        }
    }

    public SuspendingMutex allocate(String computerType) {
        for (SuspendingMutex sus : suspendings) {
            if(sus.getComputer().getComputerType().equals(computerType))
                return sus;
        }
        return  null;
    }
}
