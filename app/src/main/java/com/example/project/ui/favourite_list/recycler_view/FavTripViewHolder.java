package com.example.project.ui.favourite_list.recycler_view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class FavTripViewHolder extends RecyclerView.ViewHolder {

    private final TextView textViewTripName, textViewTripDestination,
            textViewTripRating, textViewTripPrice;

    public FavTripViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTripName = itemView.findViewById(R.id.fav_trip_name);
        textViewTripDestination = itemView.findViewById(R.id.fav_trip_destination);
        textViewTripRating = itemView.findViewById(R.id.fav_trip_rating);
        textViewTripPrice = itemView.findViewById(R.id.fav_trip_price);
    }

    public TextView getTextViewTripDestination() {
        return textViewTripDestination;
    }

    public TextView getTextViewTripRating() {
        return textViewTripRating;
    }

    public TextView getTextViewTripPrice() {
        return textViewTripPrice;
    }

    public TextView getTextViewTripName() {
        return textViewTripName;
    }

}
