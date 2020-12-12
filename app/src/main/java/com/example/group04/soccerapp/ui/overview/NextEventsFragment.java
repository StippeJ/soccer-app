package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.BetActivity;
import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.adapter.EventAdapter;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.EventsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment to show the next events of the Bundesliga
 * @author Jan Stippe
 */
public class NextEventsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    ApiHelper apiHelper;
    EventAdapter nextEventAdapter;

    public static NextEventsFragment newInstance(int index) {
        NextEventsFragment fragment = new NextEventsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_next_events, container, false);

        // Show a loading bar while data is requested from the API
        RecyclerView recyclerView = view.findViewById(R.id.nextEventsRecyclerView);
        ProgressBar progressBar = view.findViewById(R.id.nextEventsProgressBar);
        TextView errorTextView = view.findViewById(R.id.nextEventsError);

        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        apiHelper = new ApiHelper();

        // Get data from the API
        apiHelper.getSoccerRepo().getNextEventsOfLeague(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                if (response.isSuccessful()) {
                    // Give data to adapter and hide ProgressBar
                    EventsResponse er = response.body();
                    nextEventAdapter.updateData(er.getAscendingEvents());
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    // Show error message
                    errorTextView.setText(getString(R.string.apiResponseError));
                    progressBar.setVisibility(View.INVISIBLE);
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                // Show error message
                errorTextView.setText(getString(R.string.apiErrorOnFailure));
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView and its Adapter
        RecyclerView nextEventsRecView = view.findViewById(R.id.nextEventsRecyclerView);
        nextEventsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        nextEventAdapter = new EventAdapter(new ArrayList<>(), BetActivity.class);
        nextEventsRecView.setAdapter(nextEventAdapter);
    }
}