package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author André Bautz
 */
public class MainActivity extends AppCompatActivity {

    // Variables needed for getting the objects from the api
    Context context;
    SoccerRepo soccerRepo;
    // Variables for the objects in the layout
    TextView error;
    TextView headline;
    Button bundesligaButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the values of the variables
        context = this;
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
    }

    /**
     * Gets the last 15 matches from the league from the api and puts them in the RecyclerView
     * @author André Bautz
     */
    public void getLastMatchesFromApi() {
        soccerRepo.getLastEventsOfLeague(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if(response.isSuccessful()) {
                    EventsResponse eventsResponse = response.body();
                    List<Event> eventList = eventsResponse.getEvents();
                    setRecyclerViewContent(eventList, context);
                }else {
                    showError(false);
                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                showError(true);
            }
        });
    }

    /**
     * This method is setting the content from the api into the RecyclerView
     * @param eventList is the complete list of all the recent events
     * @param context is the value of the context of the Activity
     * @author André Bautz
     */
    public void setRecyclerViewContent(List<Event> eventList ,Context context) {
        if(!eventList.isEmpty()) {
            recyclerView = findViewById(R.id.lastMatchResults);
            LastMatchAdapter lastMatchAdapter = new LastMatchAdapter(eventList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(lastMatchAdapter);
        }
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
        recyclerView.setVisibility(View.INVISIBLE);
        bundesligaButton.setVisibility(View.INVISIBLE);

        if (onFailure) {
            error.setText(R.string.apiErrorOnFailure);
        } else {
            error.setText(R.string.apiResponseError);
        }

    }
}