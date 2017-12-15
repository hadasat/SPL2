package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class AddStudent<Boolean> extends Action {
    private String dep;

    public AddStudent(String dep){
        this.dep = dep;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        SubStudToDep sub1 = new SubStudToDep(dep);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1,dep, new DepartmentPrivateState());
        then(subActions, ()-> {complete(prom.get());});
    }
}
