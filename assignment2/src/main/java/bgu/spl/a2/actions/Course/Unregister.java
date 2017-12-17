package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Student.DeleteStudentCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class Unregister extends Action {

    String student;

    public Unregister(String student){
        this.student = student;
    }

    @Override
    protected void start() {
        CoursePrivateState coursePS =(CoursePrivateState) actorPS;
        StudentPrivateState studentPS = new StudentPrivateState();
        DeleteStudentCourse sub1 = new DeleteStudentCourse(actorID);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise p = sendMessage(sub1,student,studentPS);
        then(subActions,()-> {
            if(p.get().equals(true))
                complete(coursePS.removeFromCourse(student));
            else
                complete (false);});
    }
}
