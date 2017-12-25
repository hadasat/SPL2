package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddingStudentToDepartment extends Action {
    private String student;

    public AddingStudentToDepartment(String student){
        name ="Adding student to department";
        this.student = student;
        promise = new Promise();
    }

    @Override
    protected void start() {
        DepartmentPrivateState d = (DepartmentPrivateState) actorPS;
        promise.resolve(d.addStudent(student));
    }
}
