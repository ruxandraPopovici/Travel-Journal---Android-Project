package com.example.project.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.project.ui.recycler_view.RecyclerTouchListener;
import com.example.project.ui.recycler_view.TripAdapter;
import com.example.project.ui.recycler_view.TripsClickListener;
import com.example.project.ui.trip_crud.AddTripFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    private TripViewModel tripViewModel;
    private RecyclerView recyclerViewTrips;
    private List<Trip> trips;
    private TripAdapter tripAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewTrips = view.findViewById(R.id.recyclerViewTrips);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        setupRecyclerViewTrips();

        handleFabButton(view);
        setRecyclerViewListener();

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
        tripAdapter = new TripAdapter(trips, tripViewModel);

        tripViewModel.getTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> dbTrips) {
                tripAdapter.setTrips(dbTrips);
                Log.e("trips", trips.toString());
                trips = dbTrips;
            }
        });
    }
    // step 2: get adapter
    private TripAdapter getTripsAdapter() {
        return tripAdapter;
    }
    // step 3: map the adapter to the RecyclerView
    // step 3.1: tells to the RecyclerView to display the items in a linear way (list style)
    private void setupLayoutManager() {
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));
    }
    // step 3.2: combine the code: populate data source, set LayoutManager, set adapter
    private void setupRecyclerViewTrips() {
        getTrips();
        setupLayoutManager();
        recyclerViewTrips.setAdapter(getTripsAdapter());
    }

    private void handleFabButton(View currentView) {
        FloatingActionButton floatingActionButton = currentView.findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(
                        R.id.action_nav_home_to_addTripFragment,
                        null,
                        new NavOptions.Builder()
                                .setEnterAnim(android.R.animator.fade_in)
                                .setExitAnim(android.R.animator.fade_out)
                                .build()
                );
            }
        });
    }

    private void setRecyclerViewListener() {
        recyclerViewTrips.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(),
                recyclerViewTrips, new TripsClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Trip trip = trips.get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable("currentTrip", trip);
                HomeFragment.this.setArguments(bundle);

                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(
                        R.id.action_nav_home_to_viewTripFragment,
                        bundle,
                        new NavOptions.Builder()
                                .build()
                );

            }

            @Override
            public void onLongClick(View view, int position) {
                Trip trip = trips.get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable("currentTrip", trip);
                HomeFragment.this.setArguments(bundle);

                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(
                        R.id.action_nav_home_to_updateTripFragment,
                        bundle,
                        new NavOptions.Builder()
                                .setEnterAnim(android.R.animator.fade_in)
                                .setExitAnim(android.R.animator.fade_out)
                                .build()
                );
            }
        }));
    }

}
