package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.StubAction;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.List;

public class NewCourse<Boolean> extends Action {

    String courseName;
    Integer availbleSpaces;
    List<String> prerequisites;

    public NewCourse(String CourseName, Integer avPlaces, List<String> preq){
        this.courseName = CourseName;
        availbleSpaces= avPlaces;
        prerequisites = preq;
        promise = new Promise<Boolean>();
    }

    @Override
    protected void start() {
        StubAction courseOpen = new StubAction(true);
        CoursePrivateState newCourse =new CoursePrivateState();
        newCourse.setAvailableSpots(availbleSpaces);
        newCourse.setPrequisites(prerequisites);
        Promise p = sendMessage(courseOpen,courseName,newCourse);
        subActions.add(courseOpen);
        numSubAction = subActions.size();
        then(subActions , ()->{
            ((DepartmentPrivateState)actorPS).addCourse(courseName);
            if(!p.get().equals(false)){
                addRecord();
            }
             complete(p.get());});

    }

    private void addRecord(){
        String toadd = "\"Action\":\"Open Course\",\n" +
                "\"Department\": \"" + actorPS + "\",\n" +
                "\"Course\": \"" + courseName + "\",\n" +
                "\"Space\": \"" + availbleSpaces.intValue() +"\",\n" + "\"Prerequisites\" : [";

        for(String str : prerequisites){
            toadd = toadd + "\"" + str + "\",";
        }
        toadd = toadd.substring(0, toadd.length()-1) + "\"]\n";
        actorPS.addRecord(toadd);
    }
}
