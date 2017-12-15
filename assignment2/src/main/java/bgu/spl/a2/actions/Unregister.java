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


    // TODO: 16/12/2017  finish start
    
    @Override
    protected void start() {

    }
}
