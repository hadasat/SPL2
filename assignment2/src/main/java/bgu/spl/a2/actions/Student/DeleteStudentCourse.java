package bgu.spl.a2.actions.Student;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Created by מחשב on 16/12/2017.
 */

public class DeleteStudentCourse extends Action {

    String course;

    public DeleteStudentCourse(String course){
        this.course = course;
        promise = new Promise();
    }

    @Override
    protected void start() {
        StudentPrivateState studentPS = (StudentPrivateState)actorPS;
        promise.resolve(studentPS.removeGradeFromSheet(course));
    }
}
