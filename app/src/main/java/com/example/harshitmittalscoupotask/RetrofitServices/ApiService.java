package com.example.harshitmittalscoupotask.RetrofitServices;

import com.example.harshitmittalscoupotask.Models.CarModel;
import com.example.harshitmittalscoupotask.Models.MakerModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("getallmakes?format=json")
    Call<MakerModel> getMakerService();

    @GET("GetModelsForMakeId/{makeId}?format=json")
    Call<CarModel> getModelService(@Path("makeId") int makeId);
}

