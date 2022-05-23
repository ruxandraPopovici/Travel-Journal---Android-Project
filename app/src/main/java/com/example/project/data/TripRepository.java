package com.example.project.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TripRepository {

    private TripDao tripDao;
    private LiveData<List<Trip>> trips;
    private LiveData<List<Trip>> favTrips;

    TripRepository(Application application){
        TripDatabase tripDatabase = TripDatabase.getDatabase(application);
        tripDao = tripDatabase.tripDao();
        trips = tripDao.getTrips();
        favTrips = tripDao.getFavouriteTrips();
    }

    public LiveData<List<Trip>> getTrips(){
        return trips;
    }
    public LiveData<List<Trip>> getFavouriteTrips(){
        return favTrips;
    }

    // we must call this method on a different thread
    // it is a long running operations that should be done on a thread different than the UI (main) thread
    void insert(Trip trip) {
        TripDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.insert(trip);
        });
    }

    void update(Trip trip){
        TripDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.update(trip);
        });
    }

    void delete(Trip trip){
        TripDatabase.databaseWriteExecutor.execute(() -> {
            tripDao.delete(trip);
        });
    }
}
