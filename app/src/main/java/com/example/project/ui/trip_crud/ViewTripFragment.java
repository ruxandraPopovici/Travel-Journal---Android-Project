package com.example.project.ui.trip_crud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;

public class ViewTripFragment extends Fragment {

    private Trip currentTrip;

    private TripViewModel tripViewModel;

    private TextView tripNameText, tripDestinationText;
    private TextView tripStartDateText, tripEndDateText;
    private TextView tripTypeText, tripPriceText;
    private RatingBar ratingBar;
    private ImageButton isFavouriteImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_trip_info, container, false);

        currentTrip = (Trip) getArguments().getParcelable("currentTrip");

        showTripData(view);

        return view;
    }

    private void showTripData(View view){
        tripNameText = view.findViewById(R.id.info_name_text);
        tripNameText.setText(currentTrip.getName());

        tripDestinationText = view.findViewById(R.id.info_destination_text);
        tripDestinationText.setText(currentTrip.getDestination());

        tripStartDateText = view.findViewById(R.id.info_start_date_text);
        tripStartDateText.setText(currentTrip.getStartDate());

        tripEndDateText = view.findViewById(R.id.info_end_date_text);
        tripEndDateText.setText(currentTrip.getEndDate());

        tripTypeText = view.findViewById(R.id.info_type_text);
        tripTypeText.setText(currentTrip.getType());

        tripPriceText = view.findViewById(R.id.info_price_text);
        tripPriceText.setText(Integer.toString(currentTrip.getPrice()));

        ratingBar = view.findViewById(R.id.info_rating_bar);
        ratingBar.setRating(currentTrip.getRating());

        isFavouriteImage = view.findViewById(R.id.info_is_fav);
        if(currentTrip.getIsFavourite() == 0){
            isFavouriteImage.setBackgroundResource(R.drawable.ic_big_heart_empty);
        } else{
            isFavouriteImage.setBackgroundResource(R.drawable.ic_big_heart);
        }

        isFavouriteImage.setOnClickListener( view1 -> {
            if(currentTrip.getIsFavourite() == 0) {
                isFavouriteImage.setBackgroundResource(R.drawable.ic_big_heart);
                //add(trip, position);
                currentTrip.setIsFavourite(1);
            }
            else {
                isFavouriteImage.setBackgroundResource(R.drawable.ic_big_heart_empty);
                //remove(trip, position);
                currentTrip.setIsFavourite(0);
            }
            tripViewModel.update(currentTrip);
        });

        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                currentTrip.setRating(rating);
                tripViewModel.update(currentTrip);
            }
        });
    }
}
