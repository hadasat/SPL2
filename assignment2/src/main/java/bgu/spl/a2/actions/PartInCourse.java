package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class PartInCourse extends Action {
    private int grade;
    private String course;


    public  PartInCourse(int grade, String course){
        this.grade = grade;
        this.course = course;
        promise = new Promise();
    }
    @Override
    protected void start() {
        Action<Boolean> sub1 = new SubStudToCurs(ActorID, ActorPS);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1,course, new CoursePrivateState());
        then(subActions, ()-> {
            if((Boolean)prom.get()){
                StudentPrivateState st = (StudentPrivateState)ActorPS;
                st.addGrade(course, new Integer(grade));
            }
            complete(prom.get());
        });
    }
}
