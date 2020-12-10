package com.example.group04.soccerapp;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.group04.soccerapp.adapter.BetAdapter;
import com.example.group04.soccerapp.adapter.SwipeToDeleteCallback;

/**
 * Activity to show all saved bets in a RecyclerView
 * @author Jan Stippe
 */
public class BetListActivity extends BaseActivity {

    TextView errorMessage;
    RecyclerView recyclerView;
    BetAdapter betAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_list);

        errorMessage = findViewById(R.id.betListNoBets);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.betRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Create Adapter and register AdapterDataObserver
        betAdapter = new BetAdapter(getApplicationContext());
        betAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }
        });

        // Add a ItemTouchHelper to allow deleting bets through swiping
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(betAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(betAdapter);
        checkEmpty();
    }

    /**
     * Check the number of bets saved in the adapter
     * Set the visibility of the error-message and RecyclerView according to the length of the list
     * @author Jan Stippe
     */
    private void checkEmpty() {
        if (betAdapter != null) {
            errorMessage.setVisibility(betAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(betAdapter.getItemCount() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
    }

}