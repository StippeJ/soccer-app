package com.example.group04.soccerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;

import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends BaseActivity {

    // Object to access API
    ApiHelper apiHelper;

    // Event that is currently visible
    Event currentEvent;

    // Views to show data from API
    Group viewGroup;
    ProgressBar progressBar;
    ImageView imageHomeTeam;
    ImageView imageAwayTeam;
    TextView headline;
    TextView result;
    TextView date;
    TextView location;
    TextView league;
    TextView round;
    TextView errorMessage;

    /**
     * Get Intent-extra that contains the id of a match.
     * Load the result of the match and the badges of the two clubs.
     * @param savedInstanceState Saved state of the Activity
     * @author Jan Stippe
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        apiHelper = new ApiHelper();

        viewGroup = findViewById(R.id.eventGroup);
        progressBar = findViewById(R.id.eventProgressSpinner);
        imageHomeTeam = findViewById(R.id.eventImageHomeTeam);
        imageAwayTeam = findViewById(R.id.eventImageAwayTeam);
        result = findViewById(R.id.eventResult);
        headline = findViewById(R.id.eventHeadline);
        date = findViewById(R.id.eventDate);
        location = findViewById(R.id.eventLocation);
        league = findViewById(R.id.eventLeague);
        round = findViewById(R.id.eventRound);
        errorMessage = findViewById(R.id.eventErrorMessage);

        // Show a progressbar
        progressBar.setVisibility(View.VISIBLE);
        viewGroup.setVisibility(View.INVISIBLE);

        // Get Intent that should contain the eventId
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int eventId = bundle.getInt("eventId");

            apiHelper.getSoccerRepo().getEventById(new Callback<EventsResponse>() {

                @Override
                public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                    if (response.isSuccessful()) {
                        // Get the event
                        EventsResponse er = response.body();
                        currentEvent = er.getEvents().get(0);

                        loadContentIntoViews();

                        progressBar.setVisibility(View.INVISIBLE);
                        viewGroup.setVisibility(View.VISIBLE);
                    } else {
                        // Show error message
                        showErrorMessage(R.string.apiResponseError);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                    // Show error message
                    showErrorMessage(R.string.apiErrorOnFailure);
                }

            }, eventId);
        }
    }

    /**
     * Set onClickListeners for the images
     * Will send Intent to start ClubActivity
     * @author Jan Stippe
     */
    @Override
    protected void onStart() {
        super.onStart();

        imageHomeTeam.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamActivity.class);
            intent.putExtra("clubId", currentEvent.getIdHomeTeam());
            startActivity(intent);
        });

        imageAwayTeam.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeamActivity.class);
            intent.putExtra("clubId", currentEvent.getIdAwayTeam());
            startActivity(intent);
        });
    }

    /**
     * Show an error-message with a given string
     * @param errorMessageId Id of the string that should be shown
     * @author Jan Stippe
     */
    private void showErrorMessage(int errorMessageId) {
        progressBar.setVisibility(View.INVISIBLE);
        viewGroup.setVisibility(View.INVISIBLE);
        errorMessage.setText(getString(errorMessageId));
        errorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * Load the data of the currentEvent into the layout's Views
     * @author Jan Stippe
     */
    private void loadContentIntoViews() {
        // Load the two images
        apiHelper.loadTeamBadge(currentEvent.getIdHomeTeam(), imageHomeTeam);
        apiHelper.loadTeamBadge(currentEvent.getIdAwayTeam(), imageAwayTeam);

        // Show the result of the current event (if match is finished)
        if (currentEvent.getIntHomeScore() != null && currentEvent.getIntAwayScore() != null) {
            result.setText(String.format(Locale.getDefault(),"%d : %d", currentEvent.getIntHomeScore(), currentEvent.getIntAwayScore()));
        } else {
            result.setText("- : -");
        }

        // Fill in other TextViews
        headline.setText(currentEvent.getStrEvent());
        date.setText(currentEvent.getFormattedDateAndTime());
        location.setText(currentEvent.getStrVenue());
        league.setText(currentEvent.getStrLeague());
        round.setText(String.format(Locale.getDefault(),"%d", currentEvent.getIntRound()));
    }

}