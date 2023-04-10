package com.example.harshitmittalscoupotask.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CarModel {

    @SerializedName("Count")
    private int count;

    @SerializedName("Message")
    private String message;

    @SerializedName("Criteria")
    private String criteria;

    @SerializedName("Results")
    private List<CarDetailsModel> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<CarDetailsModel> getResults() {
        return results;
    }

    public void setResults(List<CarDetailsModel> results) {
        this.results = results;
    }

}

