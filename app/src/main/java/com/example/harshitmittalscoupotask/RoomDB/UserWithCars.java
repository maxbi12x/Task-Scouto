package com.example.harshitmittalscoupotask.RoomDB;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithCars {
    @Embedded
    public Users user;

    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Cars> cars;
}