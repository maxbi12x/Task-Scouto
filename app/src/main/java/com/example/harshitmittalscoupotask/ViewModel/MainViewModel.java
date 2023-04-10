package com.example.harshitmittalscoupotask.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.harshitmittalscoupotask.Models.CarModel;
import com.example.harshitmittalscoupotask.Models.MakerModel;
import com.example.harshitmittalscoupotask.Repository.Repository;
import com.example.harshitmittalscoupotask.RoomDB.Cars;
import com.example.harshitmittalscoupotask.RoomDB.Users;

import java.util.List;

public class MainViewModel {
    private final Repository repository;
    public MainViewModel(Application context) {
        this.repository = new Repository(context);
    }
    public void requestMaker() {
        repository.getMaker();
    }
    public void requestModel(int makeId) {repository.getModel(makeId);}
    public LiveData<MakerModel> getMaker() {
        return repository.maker;
    }
    public LiveData<CarModel> getModel() {
        return repository.model;
    }
    public LiveData<List<Cars>> getCarsList(){return repository.carsListLive;};
    public void addCar(Cars car) {
        repository.addCar(car);
    }
    public void deleteCar(Cars car){
        repository.deleteCar(car);
    }
    public void updateCar(Cars car){
        repository.updateCar(car);
    }
    public void carList(int id) { repository.carsList(id);};
}

