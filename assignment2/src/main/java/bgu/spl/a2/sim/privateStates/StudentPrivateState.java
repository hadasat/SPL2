package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.HashMap;
import java.util.Map;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState {

	private HashMap<String, Integer> grades;
	private long signature;

	/**
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
		grades = new HashMap<>();
		signature = 0;
	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}

	public void addGrade(String name, Integer grade) {
		grades.put(name, grade);
	}

	public boolean removeGradeFromSheet(String name){
		for(Map.Entry<String, Integer> grade : grades.entrySet()){
			if(grade.getKey().equals(name)) {
				grades.remove(name);
				return true;
			}
		}
		return false;
	}
}
