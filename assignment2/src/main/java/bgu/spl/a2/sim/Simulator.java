/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.actions.Course.OpenPlaceInCourse;
import bgu.spl.a2.actions.Course.PartInCourse;
import bgu.spl.a2.actions.Course.Unregister;
import bgu.spl.a2.actions.Department.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	
	public static ActorThreadPool actorThreadPool = new ActorThreadPool(0);
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/

    public static void start(){
			Gson gson = new Gson();
		try (FileReader r = new FileReader("gsonFile.txt")) {
            UniversitySystem uniSystem = gson.fromJson(r, UniversitySystem.class);
            List<GeneralAction> p1 = uniSystem.phase1, p2 = uniSystem.phase2, p3 = uniSystem.phase3;
            List<StringComputer> computers = uniSystem.Computers;
            ConcurrentLinkedQueue<Computer> q = new ConcurrentLinkedQueue();
            //create the computers
            for (StringComputer com : computers) {
                Computer toenter = com.getComputer();
                q.add(toenter);
            }
            Warehouse warehouse = Warehouse.getInstance();
            warehouse.setMutex(q);
            String thread = uniSystem.threads;
            ActorThreadPool pool = new ActorThreadPool(Integer.parseInt(thread));
            pool.start();
            //create the actions
            CountDownLatch count = new CountDownLatch(p1.size());
            List<List <GeneralAction>> phases = new ArrayList<>();
            phases.add(p1);
            phases.add(p2);
            phases.add(p3);
            commitPhases(pool,warehouse,phases,0);
        }
		catch (IOException e){
		    System.out.println("not goog at all");
        }
    }

    private static void commitPhases(ActorThreadPool pool, Warehouse warehouse , List<List <GeneralAction>> phases, int index){
        List<GeneralAction> phase = phases.get(index);
        List<GeneralAction> nextPhase;
        //check that the next phase is exists
        if(index + 1 == phases.size())
            nextPhase = null;
        else
            nextPhase= phases.get(index+1);
        CountDownLatch count = new CountDownLatch(phase.size());
        //run over all the general actions and translate them into Action
        for(GeneralAction action : phase){
            Action cur = null;
            switch (action.Action) {
                case ("Open Course"):
                    cur = new NewCourse(action.Course, Integer.parseInt(action.Space), action.Prerequisites);
                    pool.submit(cur, action.Department, new DepartmentPrivateState());
                    break;
                case ("Add Student"):
                    cur = new AddStudent(action.Student);
                    pool.submit(cur, action.Department, new DepartmentPrivateState());
                    break;
                case ("Participate In Course"):
                    cur = new PartInCourse(Integer.parseInt(action.Grade.get(0)), action.Student);
                    pool.submit(cur, action.Course, new CoursePrivateState());
                    break;
                case ("Add Spaces"):
                    cur = new OpenPlaceInCourse(Integer.parseInt(action.Number));
                    pool.submit(cur, action.Course, new CoursePrivateState());
                    break;
                case ("Register With Preferences"):
                    cur = new Preference(action.Student, action.Preferences, action.Grade);
                    pool.submit(cur, action.Student, new StudentPrivateState());
                    break;
                case ("Unregister"):
                    cur = new Unregister(action.Student);
                    pool.submit(cur, action.Course, new CoursePrivateState());
                    break;
                case ("Close Course"):
                    cur = new CloseCourse(action.Course);
                    pool.submit(cur, action.Department, new DepartmentPrivateState());
                    break;
                case ("Administrative Check"):
                    cur = new CheckObs(action.Conditions, warehouse, action.Computer, action.Students);
                    pool.submit(cur, action.Department, new DepartmentPrivateState());
                    break;
            }
            if(cur == null){
                throw  new RuntimeException("??");
            }
            //add callback to every action
            else{
                System.out.println(Thread.currentThread().toString());
                System.out.println(cur);
                cur.subscribe(()->{
                    count.countDown();
                    if(count.getCount() == 0) {
                        if (nextPhase != null)
                            commitPhases(pool, warehouse, phases,index+1);
                    }
                });
            }
        }

    }

	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool)
	{
		actorThreadPool = myActorThreadPool;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String,PrivateState> end(){
        HashMap<String,PrivateState> end = new HashMap<>();
		ConcurrentHashMap<String, PrivateState> privates = actorThreadPool.getData();
		end.putAll(privates);
		return end;

	}

	public void staam(){System.out.println(getClass().toString());}
	
	public static void main(String[] args){
        start();

/*
			start();
		//end();
		//return 0;
        try{
            writeOut();
        }
        catch (IOException e){
            System.out.println("not good");
        }
*/
	}

	private static void writeOut() throws IOException{
	    Gson gson = new Gson();
        HashMap<String, PrivateState> SimulationResoult;
        SimulationResoult = Simulator.end();
        gson.toJson(SimulationResoult, new FileWriter("result.ser"));
        String jsonInString = gson.toJson(SimulationResoult);
        System.out.println(jsonInString.toString());

        /*
        FileOutputStream fout = new FileOutputStream("result.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.(SimulationResoult);
        */
    }
}
