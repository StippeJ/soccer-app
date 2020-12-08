package com.example.group04.soccerapp;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.group04.soccerapp.adapter.EventAdapter;
import com.example.group04.soccerapp.api.SoccerRepo;
import com.example.group04.soccerapp.model.EventsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author André Bautz
 */
public class MainActivity extends BaseActivity {

    // Variables needed for getting the objects from the api
    SoccerRepo soccerRepo;
    EventAdapter eventAdapter;
    // Variables for the objects in the layout
    TextView error;
    TextView headline;
    Button bundesligaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the values of the variables
        soccerRepo = new SoccerRepo();
        error = findViewById(R.id.errorTextMain);
        headline = findViewById(R.id.newsHeadline);
        bundesligaButton = findViewById(R.id.bundesligaButton);

        // Method for getting the values of the last matches from the api
        getLastMatchesFromApi();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Method for opening a new activity by clicking a button
        openOverViewActivity();
        setRecyclerViewContent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getLastMatchesFromApi();
    }

    /**
     * Gets the last 15 matches from the league from the api and puts them in the RecyclerView
     * @author André Bautz
     */
    public void getLastMatchesFromApi() {
        Group contentGroup = findViewById(R.id.mainContentGroup);
        ProgressBar progressBar = findViewById(R.id.mainProgressSpinner);
        contentGroup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        soccerRepo.getLastEventsOfLeague(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                if(response.isSuccessful()) {
                    EventsResponse eventsResponse = response.body();
                    eventAdapter.updateData(eventsResponse.getEvents());
                    progressBar.setVisibility(View.INVISIBLE);
                    contentGroup.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    showError(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                showError(true);
            }
        });
    }

    /**
     * This method is setting the content from the api into the RecyclerView
     * @author André Bautz
     */
    public void setRecyclerViewContent() {
        RecyclerView recyclerView = findViewById(R.id.lastMatchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        eventAdapter = new EventAdapter(new ArrayList<>(), EventActivity.class);
        recyclerView.setAdapter(eventAdapter);
    }

    /**
     * This method is used for opening up a new activity
     * @author André Bautz
     */
    public void openOverViewActivity() {
        bundesligaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Give out a error message, if an error occurred while getting the last events of the league
     * @param onFailure boolean value to indicate if the error occurred in the onFailure method or in the else block
     * @author André Bautz
     */
    public void showError(Boolean onFailure) {
        //set  visibility of error message to VISIBLE
        error.setVisibility(View.VISIBLE);
        //set visibilities of objects to INVISIBLE
        headline.setVisibility(View.INVISIBLE);
        bundesligaButton.setVisibility(View.INVISIBLE);

        if (onFailure) {
            error.setText(R.string.apiErrorOnFailure);
        } else {
            error.setText(R.string.apiResponseError);
        }

    }
}