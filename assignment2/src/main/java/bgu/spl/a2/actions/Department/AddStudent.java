package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.StubAction;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudent<Boolean> extends Action {

    private String studentName;

    public AddStudent(String studentName){
        this.studentName = studentName;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        StubAction sub1 = new StubAction(true);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1,studentName, new StudentPrivateState());
        then(subActions, ()-> {
            ((DepartmentPrivateState)actorPS).addStudent(studentName);
            complete(prom.get());
            if(!getResult().equals(false))
                addRecored();
        });

    }

    private void addRecored(){
        actorPS.addRecord("\"Action\": \"Add Student\",\n" +
                "\"Department\": \" " + actorID + " \",\n" +
                "\"Student\": \"" +studentName + "\"");
    }
}
