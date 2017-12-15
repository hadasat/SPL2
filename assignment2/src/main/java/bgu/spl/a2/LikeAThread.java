package bgu.spl.a2;
import java.lang.Runnable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class LikeAThread implements Runnable {

    private ActorThreadPool atp;

    public  LikeAThread(ActorThreadPool atp){
        this.atp = atp;
    }


    @Override
    public void run() {
        atp.execute();
    }
}
