package com.example.project.ui.favourite_list;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.data.Trip;
import com.example.project.data.TripViewModel;
import com.example.project.ui.favourite_list.recycler_view.FavTripAdapter;
import com.example.project.ui.home.HomeFragment;
import com.example.project.ui.recycler_view.RecyclerTouchListener;
import com.example.project.ui.recycler_view.TripAdapter;
import com.example.project.ui.recycler_view.TripsClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FavouriteListFragment extends Fragment {

    private TripViewModel tripViewModel;
    private RecyclerView recyclerViewTrips;
    private List<Trip> trips;
    private FavTripAdapter tripAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_trips, container, false);
        recyclerViewTrips = view.findViewById(R.id.recyclerViewFavouriteTrips);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        setupRecyclerViewTrips();
        setRecyclerViewListener();
        setAnimation(view);

        return view;
    }

    // step 1: get data source
    private void getTrips() {
        /*trips = new ArrayList<>();
        Trip initialTrip = new Trip();
        initialTrip.setName("Your new trip awaits you!");
        initialTrip.setDestination("Where would you like to go?");
        initialTrip.setRating(5);
        trips.add(initialTrip);*/
        trips = new ArrayList<>();

        tripAdapter = new FavTripAdapter(trips);

        tripViewModel.getFavTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> dbTrips) {
                tripAdapter.setTrips(dbTrips);
                Log.e("FavTrips", dbTrips.toString());
                trips = dbTrips;
                tripAdapter.notifyDataSetChanged();
                Log.e("FavTrips_after_change", dbTrips.toString());
            }
        });

    }

    // step 2: get adapter
    private FavTripAdapter getTripsAdapter() {
        return tripAdapter;
    }

    // step 3: map the adapter to the RecyclerView
    // step 3.1: tells to the RecyclerView to display the items in a linear way (list style)
    private void setupLayoutManager() {
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(FavouriteListFragment.this.getActivity()));
    }

    // step 3.2: combine the code: populate data source, set LayoutManager, set adapter
    private void setupRecyclerViewTrips() {
        getTrips();
        Log.e("SETUP_RECYCLER", trips.toString());
        setupLayoutManager();
        recyclerViewTrips.setAdapter(getTripsAdapter());
    }


    private void setRecyclerViewListener() {
        recyclerViewTrips.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(),
                recyclerViewTrips, new TripsClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            //remove
            @Override
            public void onLongClick(View view, int position) {
                Trip trip = trips.get(position);
                trip.setIsFavourite(0);
                tripViewModel.update(trip);

                trips.remove(position);
                tripAdapter.notifyItemRemoved(position);
                tripAdapter.notifyItemRangeChanged(position, tripAdapter.getItemCount());
            }
        }));
    }

    private void setAnimation(View view) {
        ConstraintLayout constraintLayout = view.findViewById(R.id.fav_list_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}

