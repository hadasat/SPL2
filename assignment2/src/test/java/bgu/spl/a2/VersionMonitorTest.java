package bgu.spl.a2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class VersionMonitorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }




    @Test
    public void testInc(){
        bgu.spl.a2.VersionMonitor v = new bgu.spl.a2.VersionMonitor();
        int a = v.getVersion();
        assertEquals(a, 0);
        v.inc();
        assertEquals(a+1, v.getVersion());
    }


    @Test
    public void testAwait(){
        bgu.spl.a2.VersionMonitor v = new bgu.spl.a2.VersionMonitor();
        Thread t = new Thread(()-> {while(true)v.inc();});
        t.run();
        try{
            v.await(v.getVersion());
            v.inc();
            Assert.fail();
        }
        catch(InterruptedException ex){

        }
    }


}