package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Department.CloseCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

/**
 * Created by מחשב on 16/12/2017.
 */
public class TimeHasPassedKapara extends Action{
    private String dep;

    public TimeHasPassedKapara(String dep){
        this.dep = dep;
        promise = new Promise();
    }
    @Override
    protected void start() {
        CoursePrivateState c = (CoursePrivateState)actorPS;
        int reg = c.getRegistered().intValue();
        if(reg <=5){
            CloseCourse close = new CloseCourse(actorID);
            subActions.add(close);
            sendMessage(close, dep, new DepartmentPrivateState());
            numSubAction = subActions.size();
            then(subActions, ()-> {
                complete(true);
            });
            return;
        }
        c.setAvailableSpots((-1) * c.getAvailableSpots());
        promise.resolve(true);
    }
}
