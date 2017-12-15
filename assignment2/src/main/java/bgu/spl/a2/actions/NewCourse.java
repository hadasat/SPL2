package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class NewCourse<Boolean> extends Action {
    String dep;

    public NewCourse(String dep){
        this.dep = dep;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        Action<Boolean> sub1 = new SubCursToDep();
        subActions.add(sub1);
        sendMessage(sub1,dep, new DepartmentPrivateState());
        then(subActions, ()-> {complete(true);});
    }
}
