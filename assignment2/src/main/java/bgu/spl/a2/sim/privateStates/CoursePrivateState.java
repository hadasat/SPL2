package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.ArrayList;
import java.util.List;

/**
 * this class describe course's private state
 */


public class CoursePrivateState extends PrivateState{

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;


	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		super();
		availableSpots = new Integer(0);
		registered = new Integer(0);
		regStudents = new ArrayList<String>();
		prequisites = new ArrayList<String>();

	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public void setAvailableSpots(Integer num){availableSpots = num;}

	public void setPrequisites(List<String> preq){ prequisites = preq;}

	public boolean addRegister(String student){
		if(availableSpots > 0 && regStudents.add(student)) {
			availableSpots--;
			registered++;
			return true;
		}
		return false;
	}

	public void setRegistered(int num){registered = num;}

	public boolean removeFromCourse(String name){
		for(int i =0; i < regStudents.size() ; i++){
			if(regStudents.get(i).equals(name)){
				regStudents.remove(i);
				registered--;
				availableSpots++;
				return true;
			}
		}
		return false;
	}

}
