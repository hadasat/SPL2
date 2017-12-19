package bgu.spl.a2.Banks;
import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;

import java.util.*;


public class Confirmation extends Action<Boolean> {

    String sender;
    String reciver;
    String reciverBank;
    PrivateState bankStates;
    String actionName="Confirmation";

    public Confirmation(String sender, String reciver, String reciverBank, PrivateState bankStates){
        this.sender=sender;
        this.reciver=reciver;
        this.reciverBank=reciverBank;
        this.bankStates=bankStates;
        this.promise = new Promise<Boolean>();
    }

    protected void start(){
        List<Action<Boolean>> actions = new ArrayList <>();

            complete(true);

    }

}