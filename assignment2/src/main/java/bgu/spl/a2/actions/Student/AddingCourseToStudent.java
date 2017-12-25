package bgu.spl.a2.actions.Student;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;
import java.util.Map;

public class AddingCourseToStudent extends Action {
    private String course;
    private CoursePrivateState priv;
    private Integer grade;
    public AddingCourseToStudent(String course, CoursePrivateState priv, Integer grade){
        this.course = course;
        this.priv = priv;
        this.grade = grade;
        promise = new Promise();
    }
    @Override
    protected void start() {
        StudentPrivateState s = (StudentPrivateState)actorPS;
        Map<String,Integer> map = s.getGrades();

        List<String> prequisites = priv.getPrequisites();
        for (String pre : prequisites) {
            if (!map.containsKey(pre)) {
                promise.resolve(false);
                return;
            }
        }
        s.addGrade(course, grade);
        complete(true);
        return;
    }
}
