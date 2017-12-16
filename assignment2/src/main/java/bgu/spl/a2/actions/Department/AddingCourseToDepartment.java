package bgu.spl.a2.actions.Department;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddingCourseToDepartment<Boolean> extends Action {

    private String course;

    public AddingCourseToDepartment(String name) {
        course = name;
        promise = new Promise();

    }

    @Override
    protected void start() {
        DepartmentPrivateState departmrntPS =(DepartmentPrivateState) actorPS;
        promise.resolve(departmrntPS.addCourse(course));
    }
}



