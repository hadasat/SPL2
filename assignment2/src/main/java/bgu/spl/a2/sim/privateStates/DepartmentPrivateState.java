package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.ArrayList;
import java.util.List;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState {
	private List<String> courseList;
	private List<String> studentList;

	/**
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {
		super();
		courseList = new ArrayList<>();
		studentList = new ArrayList<>();
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public boolean addStudent(String student) {
		if (!studentList.contains(student)) {
			studentList.add(student);
			return true;
		}
		return false;
	}

	public boolean addCourse(String course) {
		if (!courseList.contains(course)) {
			courseList.add(course);
			return true;
		}
		return false;
	}

	public boolean removeCourse(String course){
		return (courseList.remove(course));
	}
}
