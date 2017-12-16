package bgu.spl.a2.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class NewCourse<Boolean> extends Action {

    String courseName;

    public NewCourse(String CourseName){
        this.courseName = courseName;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        stubAction courseOpen = new stubAction(true);
        CoursePrivateState newCourse =new CoursePrivateState();
        Promise p = sendMessage(courseOpen,courseName,newCourse);
        subActions.add(courseOpen);
        numSubAction = subActions.size();
        then(subActions , ()->{
            ((DepartmentPrivateState)actorPS).addCourse(courseName);
             promise.resolve(p.get());});
        
        /*
        Action<Boolean> sub1 = new AddingCourseToDepartment(actorID);
        subActions.add(sub1);
        Promise prom = sendMessage(sub1,department, new DepartmentPrivateState());
        then(subActions, ()-> {complete(prom.get());});
        */
    }
}
