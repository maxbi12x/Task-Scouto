package com.example.harshitmittalscoupotask.Models;

import com.google.gson.annotations.SerializedName;

public class CarDetailsModel {
    @SerializedName("Make_ID")
    private Integer Make_ID;
    @SerializedName("Make_Name")
    private String Make_Name;
    @SerializedName("Model_ID")
    private Integer Model_ID;
    @SerializedName("Model_Name")
    private String Model_Name;

    public Integer getMake_ID() {
        return Make_ID;
    }

    public void setMake_ID(Integer make_ID) {
        Make_ID = make_ID;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public void setMake_Name(String make_Name) {
        Make_Name = make_Name;
    }

    public Integer getModel_ID() {
        return Model_ID;
    }

    public void setModel_ID(Integer model_ID) {
        Model_ID = model_ID;
    }

    public String getModel_Name() {
        return Model_Name;
    }

    public void setModel_Name(String model_Name) {
        Model_Name = model_Name;
    }
}


