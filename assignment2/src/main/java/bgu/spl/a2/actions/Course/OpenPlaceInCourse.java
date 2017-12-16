package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpenPlaceInCourse extends Action {
    String course;

    public OpenPlaceInCourse(String course){
        this.course = course;
        promise = new Promise();
    }
    @Override
    protected void start() {
        CoursePrivateState priv = (CoursePrivateState)actorPS;
        priv.setAvailableSpots(priv.getAvailableSpots()+1);
        complete(true);
    }
}
