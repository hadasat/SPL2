package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class NewCourse<Boolean> extends Action {
    String department;

    public NewCourse(String department){
        this.department = department;
        promise = new Promise<Boolean>();

    }

    @Override
    protected void start() {
        Action<Boolean> sub1 = new SubCursToDep();
        subActions.add(sub1);
        sendMessage(sub1,department, new DepartmentPrivateState());
        then(subActions, ()-> {complete(true);});
    }
}