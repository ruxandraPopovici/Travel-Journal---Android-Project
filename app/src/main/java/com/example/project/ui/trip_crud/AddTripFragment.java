package com.example.project.ui.trip_crud;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTripFragment extends Fragment {

    private TripViewModel tripViewModel;
    private int year, month, day;
    SeekBar priceSeekBar;
    private Button startDateButton, endDateButton;

    //fields to add to the database:
    private String tripName = "", tripDestination = "", tripType = "", tripStartDate = "", tripEndDate = "";
    private float tripRating = 0;
    private int tripPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        handlePriceBar(view);

        ///// Date /////
        startDateButton = view.findViewById(R.id.start_date_button);
        endDateButton = view.findViewById(R.id.end_date_button);
        startDateButton.setOnClickListener(view1 -> {
            // TODO validate the content from the EditText and also if they are not null
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    tripStartDate = String.format("%d-%d-%d", selectedDay, selectedMonth + 1, selectedYear);
                    startDateButton.setText(tripStartDate);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        endDateButton.setOnClickListener(view1 -> {
            // TODO validate the content from the EditText and also if they are not null
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    tripEndDate = String.format("%d-%d-%d", selectedDay, selectedMonth + 1, selectedYear);
                    endDateButton.setText(tripEndDate);
                }
            }, year, month, day);
            //set value for database field
            datePickerDialog.show();
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        ////////////////

        view.findViewById(R.id.save_new_trip_button).setOnClickListener(view1 -> {
            insertDataToDataBase(view);
        });

        return view;
    }

    private String getSelectedRadioButton(View view) {
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.trip_type_radio_input);

        int id = radioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            return "";
        }
        return (String) ((RadioButton) view.findViewById(id)).getText();
    }

    private void handlePriceBar(View view){
        priceSeekBar = view.findViewById(R.id.trip_price_input);
        TextView priceTextView = view.findViewById(R.id.text_price);
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priceTextView.setText("$$ -- " + i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    private int getPriceFromSeekbar(View view) {
        return priceSeekBar.getProgress();
    }

    private float getRatingFromRatingbar(View view) {
        RatingBar priceSeekBar = view.findViewById(R.id.trip_rating_input);
        return priceSeekBar.getRating();
    }

    private boolean validateTripDate() {
        if (tripStartDate == "" || tripEndDate == "") {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            if (!sdf.parse(tripStartDate).before(sdf.parse(tripEndDate))) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void insertDataToDataBase(View view) {
        //get input text values:
        tripName = ((EditText) view.findViewById(R.id.trip_name_input))
                .getText().toString();
        if (tripName.equals("")) {
            Toast.makeText(AddTripFragment.this.getContext(), "Please don't forget the trip's name!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        tripDestination = ((EditText) view.findViewById(R.id.trip_destination_input))
                .getText().toString();

        if (tripDestination.equals("")) {
            Toast.makeText(AddTripFragment.this.getContext(), "Please don't forget the trip's destination!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        tripType = getSelectedRadioButton(view);
        if (tripType.equals("")) {
            Toast.makeText(AddTripFragment.this.getContext(), "Please select one of the 3 type options!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validateTripDate()){
            Toast.makeText(AddTripFragment.this.getContext(), "Invalid dates!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        tripPrice = getPriceFromSeekbar(view);
        tripRating = getRatingFromRatingbar(view);
        //Toast.makeText(AddTripFragment.this.getContext(), price + " " + rating,
        //        Toast.LENGTH_SHORT).show();

        Trip newTrip = new Trip(0, -1, tripName, tripDestination, tripType, tripPrice, tripRating,
                                tripStartDate, tripEndDate, 0);
        tripViewModel.insert(newTrip);
        Toast.makeText(AddTripFragment.this.getContext(), "Trip successfully added!",
                Toast.LENGTH_SHORT).show();

        //Navigate back

        NavController navController = NavHostFragment.findNavController(AddTripFragment.this);
        navController.navigate(
                R.id.action_addTripFragment_to_nav_home,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }
}