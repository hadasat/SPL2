package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class SubStudToCurs<Boolean> extends Action {
    private String student;

    public  SubStudToCurs(String student){
        this.student = student;
        promise = new Promise();
    }
    @Override
    protected void start() {
        boolean isin = false;
        CoursePrivateState p = (CoursePrivateState)ActorPS;
        isin = p.
    }
}
