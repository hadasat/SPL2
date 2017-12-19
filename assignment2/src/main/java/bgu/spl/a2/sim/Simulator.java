/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

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
		try {
			UniversitySystem uniSystem = gson.fromJson(new FileReader("C:\\hadas\\semester_3\\newSPL\\SPL2\\assignment2\\src\\main\\java\\bgu\\spl\\a2\\GsonFiles\\gsonFile.txt"), UniversitySystem.class);
			List<GenralAction> p1 = uniSystem.phase1, p2 = uniSystem.phase2, p3 = uniSystem.phase3;
			List<StingComputer> computers = uniSystem.Computers;
			String thread = uniSystem.threads;
			ActorThreadPool pool = new ActorThreadPool(Integer.getInteger(thread));

		}
		catch (Exception e){}

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
		return (HashMap<String, PrivateState>) actorThreadPool.getData();
	}
	
	public static int main(String [] args){
		start();
	}
}
