package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;
import java.util.Map;

public class SubStudToCurs<Boolean> extends Action {
    private String student;
    private PrivateState priv;

    public  SubStudToCurs(String student, PrivateState priv){
        this.student = student;
        this.priv = priv;
        promise = new Promise<Boolean>();
    }
    @Override
    protected void start() {
        StudentPrivateState s = (StudentPrivateState)priv;
        Map<String,Integer> map = s.getGrades();
        CoursePrivateState p = (CoursePrivateState)ActorPS;
        if(p.getAvailableSpots().intValue() == 0){
            promise.resolve(false);
            return;
        }
        List<String> prequisites = p.getPrequisites();
        for (String pre : prequisites) {
            if (!map.containsKey(pre)) {
                promise.resolve(false);
                return;
            }
        }
        promise.resolve(p.addRegister(student));
        return;
    }
}
