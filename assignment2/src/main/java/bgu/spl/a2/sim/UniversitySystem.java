package bgu.spl.a2.sim;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UniversitySystem {

    String threads;
    List<StringComputer> Computers;
    @SerializedName("Phase 1")
    List<GeneralAction> phase1;
    @SerializedName("Phase 2")
    List<GeneralAction> phase2;
    @SerializedName("Phase 3")
    List<GeneralAction> phase3;



}
