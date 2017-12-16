package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Course.TimeHasPassedKapara;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class EndRegPeriod extends Action {

    public  EndRegPeriod(){
        promise = new Promise();
    }
    @Override
    protected void start() {
        DepartmentPrivateState dep = (DepartmentPrivateState)actorPS;
        for (String course : dep.getCourseList()){
            TimeHasPassedKapara t = new TimeHasPassedKapara();
            subActions.add(t);
            sendMessage(t, course, new CoursePrivateState());
        }
        numSubAction = subActions.size();
        then(subActions, ()->complete(true));
    }
}
