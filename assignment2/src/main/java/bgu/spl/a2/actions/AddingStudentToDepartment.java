package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddingStudentToDepartment<Boolean> extends Action {
    private String student;

    public AddingStudentToDepartment(String student){
        this.student = student;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        DepartmentPrivateState d = (DepartmentPrivateState) actorPS;
        promise.resolve(d.addStudent(student));
    }
}
