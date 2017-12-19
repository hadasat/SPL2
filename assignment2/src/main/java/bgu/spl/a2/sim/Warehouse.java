package bgu.spl.a2.sim;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;

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



    //TODO :return and check it
    private static class SingletonHolder {
        private static Warehouse instance = new Warehouse();
    }

    private Warehouse(){
        suspendings = new ConcurrentLinkedQueue<>();

    }

    public void setMutex(Collection<Computer> col){
        size = col.size();
        for(Computer com : col){
            suspendings.add(new SuspendingMutex(com));
        }
    }
    public static Warehouse getInstance() {
        return SingletonHolder.instance;
    }

    public SuspendingMutex allocate(String computerType) {
        for (SuspendingMutex sus : suspendings) {
            if(sus.getComputer().getComputerType().equals(computerType))
                return sus;
        }
        return  null;
    }
}
