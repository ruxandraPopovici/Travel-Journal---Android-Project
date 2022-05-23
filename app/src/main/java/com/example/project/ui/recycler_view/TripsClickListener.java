package com.example.project.ui.recycler_view;

import android.view.View;

public interface TripsClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
