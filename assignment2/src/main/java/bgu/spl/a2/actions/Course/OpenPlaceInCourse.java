package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import com.google.gson.Gson;

public class OpenPlaceInCourse extends Action {
    Integer places;

    public OpenPlaceInCourse(Integer places){
        name = "OpenPlaceInCourse";
        this.places = places;
        promise = new Promise();
    }
    @Override
    protected void start() {
        CoursePrivateState priv = (CoursePrivateState)actorPS;
        priv.setAvailableSpots(priv.getAvailableSpots()+places.intValue());
        addRecord();
        complete(true);
    }


}
