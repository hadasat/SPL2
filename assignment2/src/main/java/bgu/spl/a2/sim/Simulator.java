/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.GsonFiles.UniversitySystem;
import bgu.spl.a2.PrivateState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	
	public static ActorThreadPool actorThreadPool;
	
	/**
	* Begin the simulation Should not be called before attachActorThreadPool()
	*/
    public static void start(){
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Itay\\Desktop\\school\\spl\\Spl2\\new\\SPL2\\assignment2\\src\\main\\java\\bgu\\spl\\a2\\GsonFiles\\gsonFile.txt"));
			UniversitySystem uniSystem = gson.fromJson(reader, UniversitySystem.class);
			System.out.println("good");
		}
		catch (IOException e){}

    }
	
	/**
	* attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	* 
	* @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	*/
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool){
		actorThreadPool = myActorThreadPool;
	}
	
	/**
	* shut down the simulation
	* returns list of private states
	*/
	public static HashMap<String,PrivateState> end(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
	
	
	public static void main(String [] args){
	 start();
	}
}
