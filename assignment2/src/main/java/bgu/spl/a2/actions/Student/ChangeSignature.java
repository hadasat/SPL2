package bgu.spl.a2.actions.Student;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class ChangeSignature extends Action {

    long signature;

    public ChangeSignature(long signature){
        this.signature = signature;
        promise = new Promise();
    }

    @Override
    protected void start() {
        StudentPrivateState studentps = (StudentPrivateState)actorPS;
        studentps.setSignature(signature);
        complete(true);
    }
}
