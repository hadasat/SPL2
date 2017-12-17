package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Course.PartInCourse;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by מחשב on 16/12/2017.
 */
public class Preference extends Action {

    private Integer index;
    private String student;
    private List<String> pref;
    private callback backup;

    public Preference(String student, List<String> pref){
        index = new Integer(0);
        this.student = student;
        this.pref = pref;
        promise = new Promise();
    }
    @Override

    protected void start() {
        if(pref.size() == 0) {
            complete(false);
            return;
        }
        PartInCourse part = new PartInCourse(null, student);
        subActions.add(part);
        numSubAction = subActions.size();
        String curr = pref.get(0);
        pref.remove(0);
        List<Promise> prom = new LinkedList<>();
        prom.add(sendMessage(part, curr, new CoursePrivateState()));
        then(subActions, ()->{
            if(prom.get(prom.size()-1).get().equals(true)){
                complete(true);
                return;
            }
            Preference pre = new Preference(student, pref);
            prom.add(sendMessage(pre, actorID, actorPS));
            subActions.add(pre);
            numSubAction++;
            then(subActions, finalCallBack);//ulay leapes et subactions lifnei
        });
        /*
        if(index.intValue() >= pref.size()) {
            complete(false);
            return;
        }
        PartInCourse part = new PartInCourse(null, student);
        subActions.add(part);
        numSubAction = subActions.size();
        Promise prom = sendMessage(part, pref.get(index), new CoursePrivateState());
        then(subActions,()->{
                if(prom.get().equals(true)){
                    complete(true);
                    return;
                }
                if(index++ >= pref.size()){
                    complete(false);
                    return;
                }
                numSubAction = 1;
                prom.sendMessage(part, pref.get(index), new CoursePrivateState());
                then(subActions, finalCallBack);
            });
            */
    }

}
