package com.example.project.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trip trip);

    @Query("SELECT * from trip_table ORDER BY id ASC")
    LiveData<List<Trip>> getTrips();

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * from trip_table WHERE isFavourite = 1")
    LiveData<List<Trip>> getFavouriteTrips();
}
