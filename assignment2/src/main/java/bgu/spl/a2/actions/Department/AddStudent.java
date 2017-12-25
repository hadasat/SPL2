package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.StubAction;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudent extends Action {

    private String studentName;

    public AddStudent(String studentName){
        name = "Add student";
        this.studentName = studentName;
        promise = new Promise();
    }

    @Override
    protected void start() {
        StubAction sub1 = new StubAction(true);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1,studentName, new StudentPrivateState());
        then(subActions, ()-> {
            if(((DepartmentPrivateState)actorPS).addStudent(studentName))
            {
                complete(prom.get());
                return;
            }
            complete(false);
        });

    }

}
