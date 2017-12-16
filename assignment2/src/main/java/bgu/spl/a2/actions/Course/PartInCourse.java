package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Student.AddingCourseToStudent;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class PartInCourse<Boolean> extends Action {
    private Integer grade;
    private String student;

    public  PartInCourse(Integer grade, String student){
        this.grade = grade;
        this.student = student;
        promise = new Promise<Boolean>();
    }
    @Override
    protected void start() {
        CoursePrivateState c = (CoursePrivateState)actorPS;
        if(c.getAvailableSpots().intValue() <= 0){
            promise.resolve(false);
            return;
        }
        AddingCourseToStudent sub1 = new AddingCourseToStudent(actorID, c, grade);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1, student, new StudentPrivateState());
        then(subActions, ()-> {
            if((prom.get()).equals(true) && c.addRegister(student))
                complete(prom.get());
            else
                complete(false);
        });


        /*
        Action<Boolean> sub1 = new AddingCourseToStudent(actorID, actorPS);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1,course, new CoursePrivateState());
        then(subActions, ()-> {
            if((Boolean)prom.get()){
                CoursePrivateState st = (CoursePrivateState) actorPS;
                st.addGrade(course, grade);
            }
            complete(prom.get());
        });*/
    }
}
