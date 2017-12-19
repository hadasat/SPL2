package bgu.spl.a2.sim;

import bgu.spl.a2.actions.Department.NewCourse;

import java.util.List;

public class GenralAction {


    String Action;
    String Department;
    String Course;
    String Student;
    List<String> Prerequisites;
    List<String> Grade;
    String Computer;
    List<String> Students;
    List<String> Conditions;
    String Space;
    String Number;
    String Preferences;


    public Action read(){
        switch (Action){
            case("Open Course"):
                return new NewCourse<>(Course, Space, Prerequisites);
            case("")
        }
    }

}
