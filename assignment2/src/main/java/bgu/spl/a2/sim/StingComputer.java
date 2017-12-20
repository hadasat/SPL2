package bgu.spl.a2.sim;

import com.google.gson.annotations.SerializedName;

public class StingComputer {

    String Type;
    @SerializedName("Sig Success")
    String SigSuccess;
    @SerializedName("Sig Fail")
    String SigFail;

    public Computer getComputer(){
        Computer com = new Computer(Type);
        com.setFailSig(Long.parseLong(SigFail));
        com.setSuccessSig(Long.parseLong(SigSuccess));
        return com;
    }
}
