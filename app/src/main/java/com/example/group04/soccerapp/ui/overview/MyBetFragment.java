package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private static final String ARG_SECTION_NUMBER = "section_number";

    TextView errorMessage;
    RecyclerView recyclerView;
    BetAdapter betAdapter;

    public MyBetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param index Index of the tab
     * @return A new instance of fragment MyBetFragment.
     */
    public static MyBetFragment newInstance(int index) {
        MyBetFragment fragment = new MyBetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(args);
        return fragment;
    }

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
     * Reload the Adapter's data when Fragment is openes again
     * @param view
     * @param savedInstanceState
     * @author Jan Stippe
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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