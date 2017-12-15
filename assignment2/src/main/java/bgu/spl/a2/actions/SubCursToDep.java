package bgu.spl.a2.actions;
import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.Map;

public class SubCursToDep<Boolean> extends Action {

    private String course;

    public SubCursToDep(String name) {
        course = name;
        promise = new Promise();

    }

    @Override
    protected void start() {
        DepartmentPrivateState departmrntPS =(DepartmentPrivateState) ActorPS;
        promise.resolve(departmrntPS.addCourse(course));
    }
}



