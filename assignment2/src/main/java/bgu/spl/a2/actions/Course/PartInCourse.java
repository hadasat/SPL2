package bgu.spl.a2.actions.Course;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Student.AddingCourseToStudent;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class PartInCourse extends Action {
    private Integer grade;
    private String student;

    public  PartInCourse(Integer grade, String student){
        this.grade = grade;
        this.student = student;
        promise = new Promise();
    }
    @Override
    protected void start() {
        CoursePrivateState c = (CoursePrivateState) actorPS;
        if (c.getAvailableSpots().intValue() <= 0) {
            promise.resolve(false);
            return;
        }
        AddingCourseToStudent sub1 = new AddingCourseToStudent(actorID, c, grade);
        subActions.add(sub1);
        numSubAction = subActions.size();
        Promise prom = sendMessage(sub1, student, new StudentPrivateState());
        then(subActions, () -> {
            if ((prom.get()).equals(false))
                complete(false);
            else {
                c.addRegister(student);
                addRecord();
                complete(actorID);
            }
        });
    }


    private void addRecord(){
        actorPS.addRecord("\"Action\": \"Participate In Course\",\n" +
                "\"Student\": \"" + actorID + "\",\n" +
                "\"Course\": \"SPL\",\n" +
                "\"Grade\": [\"98\"]");
    }
}
