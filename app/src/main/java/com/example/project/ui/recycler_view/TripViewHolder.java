package com.example.project.ui.recycler_view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class TripViewHolder extends RecyclerView.ViewHolder {

    private final TextView textViewTripName, textViewTripDestination,
                            textViewTripRating, textViewTripPrice;
    ImageView favButton;

    public TripViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTripName = itemView.findViewById(R.id.trip_name);
        textViewTripDestination = itemView.findViewById(R.id.trip_destination);
        textViewTripRating = itemView.findViewById(R.id.trip_rating);
        textViewTripPrice = itemView.findViewById(R.id.trip_price);

        favButton = itemView.findViewById(R.id.fav_button);
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

    public ImageView getFavButton() { return favButton; }


}
