package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.actions.Student.ChangeSignature;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;

public class CheckObs extends Action {//Check administrative obligations
    List<String> studentCourses;
    Warehouse warehouse;
    String computerType;
    List<String> students;

    public  CheckObs(List<String> studentCourses, Warehouse ware, String computerType, List<String> students){
        this.studentCourses = studentCourses;
        warehouse = ware;
        this.computerType = computerType;
        this.students = students;
        promise = new Promise();
    }

    @Override
    protected void start() {
        DepartmentPrivateState dep = (DepartmentPrivateState) actorPS;
        SuspendingMutex mutex = warehouse.allocate(computerType);
        if (mutex != null) {
            Promise prom = mutex.down();
            prom.subscribe(() -> {
                Computer comp = (Computer) prom.get();
                for (String student : dep.getStudentList()) {
                    PrivateState p = pool.getPrivateState(student);
                    if (p == null || !(p instanceof StudentPrivateState))
                        throw new RuntimeException("wtf");
                    StudentPrivateState studentps = (StudentPrivateState) p;
                    Action sub = new ChangeSignature(comp.checkAndSign(studentCourses, studentps.getGrades()));
                    subActions.add(sub);
                    numSubAction++;
                }
                mutex.up();
                then(subActions, () -> {
                    complete(true);
                });
            });
        }
        else{throw new RuntimeException("computer key lo nimtza");}

    }
}
