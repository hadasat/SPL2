package bgu.spl.a2.actions.Department;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;
import java.util.Map;

public class CheckObs<Computer> extends Action {//Check administrative obligations
    List<String> studentCourses;
    Warehouse warehouse;
    String student;

    public  CheckObs(List<String> studentCourses, Warehouse ware, String stu){
        this.studentCourses = studentCourses;
        warehouse = ware;
        student = stu;
    }

    @Override
    protected void start() {
        PrivateState p = pool.getPrivateState(student);
        if(p == null || !(p instanceof StudentPrivateState))
            throw new RuntimeException("wtf");
        StudentPrivateState studentPS = (StudentPrivateState)p;
        DepartmentPrivateState course = (DepartmentPrivateState) actorPS;
        numSubAction = 1;
        SuspendingMutex mutex = warehouse.allocate(this);
        if(mutex != null) {
            Map grades = studentPS.getGrades();
            long answer = mutex.getComputer().checkAndSign(studentCourses, grades);
            mutex.up();
            complete(answer);
            return;
        }

    }
}
