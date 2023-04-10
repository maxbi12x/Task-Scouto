package com.example.harshitmittalscoupotask.Models;

import com.google.gson.annotations.SerializedName;

public class MakerDetailsModel {

    @SerializedName("Make_ID")
    private int makeId;

    @SerializedName("Make_Name")
    private String makeName;

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }
}
