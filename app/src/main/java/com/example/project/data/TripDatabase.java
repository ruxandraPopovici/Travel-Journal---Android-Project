package com.example.project.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trip.class}, version = 1, exportSchema = false)
public abstract class TripDatabase extends RoomDatabase {

    public abstract TripDao tripDao();

    private static volatile TripDatabase INSTANCE;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(5);


    //singleton (a single database for the whole app)
    static TripDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripDatabase.class, "trip_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
