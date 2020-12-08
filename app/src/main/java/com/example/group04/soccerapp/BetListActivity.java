package com.example.group04.soccerapp;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.group04.soccerapp.adapter.BetAdapter;
import com.example.group04.soccerapp.adapter.SwipeToDeleteCallback;

/**
 * Activity to show all saved bets in a RecyclerView
 * @author Jan Stippe
 */
public class BetListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_list);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.betRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Add a ItemTouchHelper to allow deleting bets through swiping
        BetAdapter betAdapter = new BetAdapter(getApplicationContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(betAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(betAdapter);
    }

}