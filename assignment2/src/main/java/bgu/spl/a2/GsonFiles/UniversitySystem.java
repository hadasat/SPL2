package bgu.spl.a2.GsonFiles;

import bgu.spl.a2.sim.Computer;
import com.google.gson.annotations.SerializedName;

public class UniversitySystem {

    int threds;
    Computer[] Computers;

    @SerializedName(value = "phase1" ,alternate = "Phase 1")
    private GenralAction[] phase1;

    @SerializedName(value = "phase2" ,alternate = "Phase 2")
    private GenralAction[] phase2;

    @SerializedName(value = "phase3" ,alternate = "Phase 3")
    private GenralAction[] phase3;

}
