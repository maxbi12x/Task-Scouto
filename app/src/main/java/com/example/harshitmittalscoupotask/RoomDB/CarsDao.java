package com.example.harshitmittalscoupotask.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarsDao {
    @Insert
    void insert(Cars cars);

    @Update
    int update(Cars cars);

    @Delete
    int delete(Cars cars);

    @Query("SELECT * FROM cars WHERE user_id = :userId")
    List<Cars> getCarsByUserId(int userId);
}
