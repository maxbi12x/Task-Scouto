package com.example.harshitmittalscoupotask.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface UserDao {
    @Insert
    void insert(Users user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    Users login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email")
    Users username(String email);

    @Transaction
    @Query("SELECT * FROM users WHERE email = :email")
    UserWithCars getUserWithToDos(String email);
}
