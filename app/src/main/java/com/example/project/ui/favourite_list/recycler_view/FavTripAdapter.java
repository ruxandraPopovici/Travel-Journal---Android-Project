package com.example.project.ui.favourite_list.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavTripAdapter extends RecyclerView.Adapter<FavTripViewHolder> {

    private List<Trip> trips;

    public FavTripAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    // this method creates the template of the item (the views of that layout item, in our case item_email)
    // and returns a new instance of EmailViewHolder associated to our current item (1:1 mapping java to xml)
    @NonNull
    @Override
    public FavTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_trip, parent, false);
        return new FavTripViewHolder(item);
    }

    // this method populates the views with the data of the object from the data source (in our case emails) from the position "position"
    @Override
    public void onBindViewHolder(@NonNull FavTripViewHolder holder, int position) {
        Trip currentTrip = trips.get(position);
        holder.getTextViewTripName().setText(currentTrip.getName());
        holder.getTextViewTripDestination().setText(currentTrip.getDestination());
        holder.getTextViewTripRating().setText(String.valueOf(currentTrip.getRating()));
        holder.getTextViewTripPrice().setText(String.valueOf(currentTrip.getPrice()));
    }

    // this method decides how many items to include inside of the RecyclerView
    @Override
    public int getItemCount() {
        return trips.size();
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

}