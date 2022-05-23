package com.example.project.ui.trip_crud;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;
import com.example.project.ui.home.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;

public class UpdateTripFragment extends Fragment {

    private Trip currentTrip;
    private TripViewModel tripViewModel;

    private EditText tripNameEdit, tripDestinationEdit;
    private TextView tripPriceText;
    Spinner tripTypeSpinner;
    SeekBar priceSeekBar;
    RatingBar ratingBar;
    private Button startDateButton, endDateButton;
    private int year, month, day;

    private String [] tripTypes = {
            "City Break",
            "Sea Side",
            "Mountains"
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_trip, container, false);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        currentTrip = (Trip) getArguments().getParcelable("currentTrip");

        showTripData(view);

        view.findViewById(R.id.update_trip_button).setOnClickListener(view1 -> {
            updateItem(view);
        });

        setHasOptionsMenu(true);

        return view;
    }

    //don't show the setting menu
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.app_bar_menu);
        if(item != null)
            item.setVisible(false);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_delete){
            deleteTrip();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTripData(View view){

        tripNameEdit = view.findViewById(R.id.update_trip_name);
        tripNameEdit.setText(currentTrip.getName());

        tripDestinationEdit = view.findViewById(R.id.update_trip_destination);
        tripDestinationEdit.setText(currentTrip.getDestination());

        handleSpinnerValues(view);
        handlePriceBar(view);

        startDateButton = view.findViewById(R.id.update_start_date_button);
        startDateButton.setText(currentTrip.getStartDate());
        endDateButton = view.findViewById(R.id.update_end_date_button);
        endDateButton.setText(currentTrip.getEndDate());

        ratingBar = view.findViewById(R.id.update_trip_rating);
        ratingBar.setRating(currentTrip.getRating());

    }
    private void handleSpinnerValues(View view){
        tripTypeSpinner = view.findViewById(R.id.update_trip_type_spinner);

        // Create the instance of ArrayAdapter
        ArrayAdapter ad = new ArrayAdapter(
                UpdateTripFragment.this.getContext(),
                android.R.layout.simple_spinner_item,
                tripTypes);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        tripTypeSpinner.setAdapter(ad);

        //set initial value of spinner to the trip's initial type
        tripTypeSpinner.setSelection(ad.getPosition(currentTrip.getType()));
    }
    private void handlePriceBar(View view){
        priceSeekBar = view.findViewById(R.id.update_trip_price);
        tripPriceText = view.findViewById(R.id.update_text_price);

        //initial trip price
        tripPriceText.setText("$$ -- " + currentTrip.getPrice());
        priceSeekBar.setProgress(currentTrip.getPrice());
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tripPriceText.setText("$$ -- " + i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void handleDateChanges(View view){
        startDateButton.setOnClickListener(view1 -> {
            // TODO validate the content from the EditText and also if they are not null
            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTripFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    startDateButton.setText(String.format("%d-%d-%d", selectedDay, selectedMonth + 1, selectedYear));
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        endDateButton.setOnClickListener(view1 -> {
            // TODO validate the content from the EditText and also if they are not null
            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTripFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                    endDateButton.setText(String.format("%d-%d-%d", selectedDay, selectedMonth + 1, selectedYear));
                }
            }, year, month, day);
            //set value for database field
            datePickerDialog.show();
        });

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    private boolean validateTripDate(String tripStartDate, String tripEndDate) {
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

    private void updateItem(View view){
        String tripName = tripNameEdit.getText().toString();
        if (tripName.equals("")) {
            Toast.makeText(UpdateTripFragment.this.getContext(), "Trip's name cannot be null!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String tripDestination = tripDestinationEdit.getText().toString();
        if (tripDestination.equals("")) {
            Toast.makeText(UpdateTripFragment.this.getContext(), "Trip's destination can't be null!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String tripType = tripTypeSpinner.getSelectedItem().toString();

        handleDateChanges(view);

        String tripStartDate = startDateButton.getText().toString();
        String tripEndDate = endDateButton.getText().toString();

        if(!validateTripDate(tripStartDate, tripEndDate)){
            Toast.makeText(UpdateTripFragment.this.getContext(), "Invalid dates!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int tripPrice = priceSeekBar.getProgress();;
        float tripRating = ratingBar.getRating();

        Trip updateTrip = new Trip(currentTrip.getId(), -1, tripName, tripDestination, tripType, tripPrice, tripRating,
                tripStartDate, tripEndDate, currentTrip.getIsFavourite());
        tripViewModel.update(updateTrip);
        Toast.makeText(UpdateTripFragment.this.getContext(), "Trip successfully updated!",
                Toast.LENGTH_SHORT).show();

        //Navigate back
        NavController navController = NavHostFragment.findNavController(UpdateTripFragment.this);
        navController.navigate(
                R.id.action_updateTripFragment_to_nav_home,
                null,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build()
        );
    }
    private void deleteTrip(){
        showAlert(UpdateTripFragment.this.getContext());
    }
    private void showAlert(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + currentTrip.getName())
                .setMessage("Are you sure you'd like to delete this trip?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tripViewModel.delete(currentTrip);
                        Toast.makeText(UpdateTripFragment.this.getContext(), "Successfully removed!",
                                Toast.LENGTH_SHORT).show();

                        //Navigate back
                        NavController navController = NavHostFragment.findNavController(UpdateTripFragment.this);
                        navController.navigate(
                                R.id.action_updateTripFragment_to_nav_home,
                                null,
                                new NavOptions.Builder()
                                        .setEnterAnim(android.R.animator.fade_in)
                                        .setExitAnim(android.R.animator.fade_out)
                                        .build()
                        );
                    }
                })
                .setNegativeButton("No", null);



        AlertDialog dialog = builder.create();
        dialog.show();
    }
}