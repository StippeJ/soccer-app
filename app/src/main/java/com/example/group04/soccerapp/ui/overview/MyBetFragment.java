package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.adapter.BetAdapter;
import com.example.group04.soccerapp.adapter.SwipeToDeleteCallback;

/**
 * Fragment to show the saved bets
 * @author Jan Stippe
 */
public class MyBetFragment extends Fragment {

    TextView errorMessage;
    RecyclerView recyclerView;
    BetAdapter betAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bet, container, false);

        errorMessage = view.findViewById(R.id.betListNoBets);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.betRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        // Create Adapter and register AdapterDataObserver
        betAdapter = new BetAdapter(view.getContext());
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

        return view;
    }

    /**
     * Reload data in Adapter each time Fragment is opened
     * @author Jan Stippe
     */
    @Override
    public void onStart() {
        super.onStart();
        betAdapter.reloadData();
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