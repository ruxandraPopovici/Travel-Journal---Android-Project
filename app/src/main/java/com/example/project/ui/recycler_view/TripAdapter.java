package com.example.project.ui.recycler_view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private List<Trip> trips;
    private TripViewModel tripViewModel;

    public TripAdapter(List<Trip> trips, TripViewModel tripViewModel) {
        this.trips = trips;
        this.tripViewModel = tripViewModel;
    }

    // this method creates the template of the item (the views of that layout item, in our case item_email)
    // and returns a new instance of EmailViewHolder associated to our current item (1:1 mapping java to xml)
    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(item);
    }

    // this method populates the views with the data of the object from the data source (in our case emails) from the position "position"
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip currentTrip = trips.get(position);
        holder.getTextViewTripName().setText(currentTrip.getName());
        holder.getTextViewTripDestination().setText(currentTrip.getDestination());
        holder.getTextViewTripRating().setText(String.valueOf(currentTrip.getRating()));
        holder.getTextViewTripPrice().setText(String.valueOf(currentTrip.getPrice()));

        if(currentTrip.getIsFavourite() == 1){
            holder.getFavButton().setVisibility(View.VISIBLE);
        }
        else {
            holder.getFavButton().setVisibility(View.GONE);
        }

        /*holder.getFavButton().setOnClickListener(new Listener (
                holder.getFavButton(),
                position,
                trips.get(position)));*/
    }

    /*class Listener implements View.OnClickListener
    {
        ImageButton favButton;
        Trip trip;
        int position;
        Listener (ImageButton favButton, int position, Trip trip) {
            this.favButton = favButton;
            this.trip = trip;
            this.position = position;
        }
        @Override
        public void onClick(View view) {

            if(trip.getIsFavourite() == 0) {
                favButton.setBackgroundResource(R.drawable.ic_heart_fill);
                //add(trip, position);
                trip.setIsFavourite(1);
            }
            else {
                favButton.setBackgroundResource(R.drawable.ic_heart_empty);
                //remove(trip, position);
                trip.setIsFavourite(0);
            }
            tripViewModel.update(trip);
        }
    }*/

    // this method decides how many items to include inside of the RecyclerView
    @Override
    public int getItemCount() {
        return trips.size();
    }

    public void setTrips(List<Trip> trips){
        this.trips = trips;
        notifyDataSetChanged();
    }
}