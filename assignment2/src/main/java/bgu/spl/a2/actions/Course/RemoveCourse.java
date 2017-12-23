package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

/**
 * Created by מחשב on 16/12/2017.
 */
public class RemoveCourse extends Action {

    @Override
    protected void start() {
        CoursePrivateState priv = (CoursePrivateState)actorPS;
        for (String student : priv.getRegStudents()){
            Unregister un = new Unregister(student);
            subActions.add(un);
            sendMessage(un, actorID, new CoursePrivateState());
        }
        numSubAction = subActions.size();
        then(subActions, ()-> {
            priv.setAvailableSpots(-1);
            addRecord();
            complete(true);
        });
    }

    private void addRecord(){
        actorPS.addRecord("\"Action\": \"Close Course\",\n" +
                "\"Department\": \"CS\",\n" +
                "\"Course\": \"Data Bases\"");
    }
}
