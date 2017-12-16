package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Course.PartInCourse;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.List;

/**
 * Created by מחשב on 16/12/2017.
 */
public class Preference extends Action {

    Integer index;
    String student;
    List<String> pref;

    public Preference(String student, List<String> pref){
        index = new Integer(0);
        this.student = student;
        this.pref = pref;
        promise = new Promise();
    }
    @Override

    protected void start() {





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
