package bgu.spl.a2;

import bgu.spl.a2.Banks.BankStates;
import bgu.spl.a2.Banks.Transmission;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ActorThreadPoolTest {

    @Test
    public void submit() {

    }


    @Test
    public void shutdown() {
    }

    @Test
    public void start() {

        ActorThreadPool pool = new ActorThreadPool(10);
        Action<String> trans = new Transmission(100, "A","B","bank2", "bank1");
        pool.start();
        pool.submit(trans, "bank1", new BankStates());
        CountDownLatch e = new CountDownLatch(1);
        trans.getResult().subscribe(()-> {
            e.countDown();
        });
        try{
            e.await();}
        catch (InterruptedException ex){}
        try{ pool.shutdown();}
        catch (InterruptedException ex){}
    }
}