package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class Unregister extends Action {

    CoursePrivateState coursPS;

    public Unregister(CoursePrivateState coursPS, StudentPrivateState studentPS){
        this.coursPS = coursPS;
        ActorPS = studentPS;
    }

    @Override
    protected void start() {

    }
}
