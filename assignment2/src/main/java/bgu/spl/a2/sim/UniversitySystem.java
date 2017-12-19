package bgu.spl.a2.sim;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UniversitySystem {

    String threads;
    List<StingComputer> Computers;
    @SerializedName("Phase 1")
    List<GenralAction> phase1;
    @SerializedName("Phase 2")
    List<GenralAction> phase2;
    @SerializedName("Phase 3")
    List<GenralAction> phase3;



}
