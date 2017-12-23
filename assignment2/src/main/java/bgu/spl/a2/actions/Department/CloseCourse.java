package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Course.RemoveCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class CloseCourse extends Action {

    private String course;

    public  CloseCourse(String course){
        this.course = course;
        promise = new Promise();
    }

    @Override
    protected void start() {
        DepartmentPrivateState dep = (DepartmentPrivateState)actorPS;
        if(!dep.getCourseList().contains(course)) {
            promise.resolve(false);
            return;
        }
        RemoveCourse remove = new RemoveCourse();
        subActions.add(remove);
        numSubAction = subActions.size();
        Promise prom = sendMessage(remove, course, new CoursePrivateState());
        then(subActions, ()-> {
            if(prom.get().equals(true) && dep.removeCourse(course)){
                promise.resolve(true);
                return;
            }
            promise.resolve(false);});
    }

    private void addRecord(){
        actorPS.addRecord("\"Action\": \"Close Course\",\n" +
                "\"Department\": \"" + actorID + "\",\n" +
                "\"Course\": \"" + course + "\"");
    }
}
