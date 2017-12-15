package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class PartInCourse extends Action {
    private int grade;
    private String course;

    @Override
    protected void start() {
        Action<Boolean> sub1 = new SubStudToCurs(ActorID);
        subActions.add(sub1);
        then(subActions, ()-> {complete(sendMessage(sub1,course, new CoursePrivateState()).get());});
    }
}
