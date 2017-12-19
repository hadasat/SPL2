package bgu.spl.a2.GsonFiles;

import com.google.gson.annotations.SerializedName;

public class StingComputer {

    String Type;
    @SerializedName(value = "SigSuccess" ,alternate = "Sig Success")
    String SigSuccess;
    @SerializedName(value = "SigFail" ,alternate = "Sig Fail")
    String SigFail;
}
