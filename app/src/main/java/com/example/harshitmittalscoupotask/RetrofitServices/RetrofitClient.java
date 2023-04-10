package com.example.harshitmittalscoupotask.RetrofitServices;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String base_url = "https://vpic.nhtsa.dot.gov/api/vehicles/";//base url
    private static RetrofitClient instance;
    private Retrofit retrofit; //retrofit object

    private RetrofitClient() { //constructor
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;

    }

    public ApiService getApiCall() {
        return retrofit.create(ApiService.class);
    }
}
