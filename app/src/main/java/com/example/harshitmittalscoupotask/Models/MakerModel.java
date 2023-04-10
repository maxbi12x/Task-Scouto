package com.example.harshitmittalscoupotask.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakerModel {

    @SerializedName("Count")
    private int count;

    @SerializedName("Message")
    private String message;

    @SerializedName("SearchCriteria")
    private String searchCriteria;

    @SerializedName("Results")
    private List<MakerDetailsModel> results;

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

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<MakerDetailsModel> getResults() {
        return results;
    }

    public void setResults(List<MakerDetailsModel> results) {
        this.results = results;
    }

}
