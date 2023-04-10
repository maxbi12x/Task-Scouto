package com.example.harshitmittalscoupotask.RetrofitServices.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.harshitmittalscoupotask.Models.CarModel;
import com.example.harshitmittalscoupotask.Models.MakerModel;
import com.example.harshitmittalscoupotask.RetrofitServices.ApiService;
import com.example.harshitmittalscoupotask.RetrofitServices.RetrofitClient;
import com.example.harshitmittalscoupotask.RoomDB.AppDatabase;
import com.example.harshitmittalscoupotask.RoomDB.Cars;
import com.example.harshitmittalscoupotask.RoomDB.Users;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private final ApiService service;
    private AppDatabase appDatabase;
    private final MutableLiveData<MakerModel> makerMutableData = new MutableLiveData<>();
    public LiveData<MakerModel> maker;
    private final MutableLiveData<CarModel> modelMutableData = new MutableLiveData<>();
    public LiveData<CarModel> model;
    public Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Users> _userDetailsFormUsername = new MutableLiveData<>();
    public LiveData<Users> userDetailsFromUsername;
    private final MutableLiveData<Users> _userLogin = new MutableLiveData<>();
    public LiveData<Users> userLogin;
    private final MutableLiveData<List<Cars>> _carsListLive = new MutableLiveData<>();
    public LiveData<List<Cars>> carsListLive;
    public Repository(Context context) {
        this.service = RetrofitClient.getInstance().getApiCall();;
        appDatabase = AppDatabase.getInstance(context);
        userDetailsFromUsername = _userDetailsFormUsername;
        this.maker = makerMutableData;
        this.model = modelMutableData;
        carsListLive= _carsListLive;
    }
    public void getMaker() {
        Log.e("AAAAAAA","error");
        service.getMakerService().enqueue(new Callback<MakerModel>() {
            @Override
            public void onResponse(@NonNull Call<MakerModel> call, @NonNull Response<MakerModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("AAAAAAA",String.valueOf(response.body().getCount()));
                        makerMutableData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MakerModel> call, Throwable t) {
                Log.e("Internet","Not Connected");
            }
        });

    }


    public void getModel(int makeId) {
        Log.e("AAAAAAA", "getModel");
        service.getModelService(makeId).enqueue(new Callback<CarModel>() {
            @Override
            public void onResponse(@NonNull Call<CarModel> call, @NonNull Response<CarModel> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("ModelsCount", String.valueOf(response.body().getCount()));
                        modelMutableData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<CarModel> call, Throwable t) {
                Log.e("Erorr","Notworking call");
            }
        });
    }



    public void addUser(Users users) {
        executor.execute(() -> appDatabase.userDao().insert(users));
    }
    public void isUsername(String email) {
        executor.execute(() -> {
            Users result = appDatabase.userDao().username(email);
//            Log.e("VERY IMP",result.toString());
            _userDetailsFormUsername.postValue(result);
//            if(result!=null){
//
//            }
        });

    }

    public void carsList(int id){
        executor.execute(() ->{
            List<Cars> result = appDatabase.carsDao().getCarsByUserId(id);
            if(result!=null){
                _carsListLive.postValue(result);
            }

        });
    }
    public void isLogin(String email,String password) {
        executor.execute(() -> {
            Users result = appDatabase.userDao().login(email,password);
            if(result!=null){
                _userDetailsFormUsername.postValue(result);
            }
        } );


    }

    public void addCar(Cars cars) {
        executor.execute(() -> appDatabase.carsDao().insert(cars));
    }
    public void deleteCar(Cars cars){
        executor.execute(()-> appDatabase.carsDao().delete(cars));
    }
    public void updateCar(Cars cars){
        executor.execute(()-> appDatabase.carsDao().update(cars));
    }
}
