package com.example.harshitmittalscoupotask.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.harshitmittalscoupotask.Models.CarModel;
import com.example.harshitmittalscoupotask.Models.MakerModel;
import com.example.harshitmittalscoupotask.Repository.Repository;
import com.example.harshitmittalscoupotask.RoomDB.Users;

public class LoginViewModel {
    private final Repository repository;
    public LoginViewModel(Application context) { this.repository = new Repository(context);}
    public void addUser(Users users) {
        repository.addUser(users);
    }
    public void findUserWithUsername(String email){
        repository.isUsername(email);
    }
    public void login(String email,String password){
        repository.isLogin(email,password);
    }
    public LiveData<Users> getIsUsername(){
        return repository.userDetailsFromUsername;
    }
    public LiveData<Users> getLogin(){
        return repository.userDetailsFromUsername;
    }

//    public LiveData<CarModel> getModel() {
//        return repository.model;
//    }
}
