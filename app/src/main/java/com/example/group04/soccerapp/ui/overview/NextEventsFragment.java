package com.example.group04.soccerapp.ui.overview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.adapter.NextEventAdapter;
import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class NextEventsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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
        View root = inflater.inflate(R.layout.fragment_nextevents, container, false);

        ApiHelper apiHelper = new ApiHelper();

        apiHelper.getSoccerRepo().getNextEventsOfLeague(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                if (response.isSuccessful()) {
                    EventsResponse er = response.body();
                    List<Event> eventList = er.getEvents();
                    RecyclerView nextEventsRecView = root.findViewById(R.id.nextEventsRecyclerView);
                    NextEventAdapter nextEventAdapter = new NextEventAdapter(eventList);
                    nextEventsRecView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));
                    nextEventsRecView.setAdapter(nextEventAdapter);
                } else {
                    Log.d("PlaceholderFragment", "onResponse not successful");
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                Log.d("PlaceholderFragment", "onFailure");
            }
        });
        return root;
    }
}