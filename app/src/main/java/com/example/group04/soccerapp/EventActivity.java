package com.example.group04.soccerapp;

import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    Group eventGroup;
    ProgressBar progressBar;
    ImageView imageHomeTeam;
    ImageView imageAwayTeam;
    TextView eventHeadline;
    TextView eventResult;
    TextView eventDate;
    TextView eventLocation;
    TextView eventLeague;
    TextView eventRound;
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

        eventGroup = findViewById(R.id.eventGroup);
        progressBar = findViewById(R.id.eventProgressSpinner);
        imageHomeTeam = findViewById(R.id.imageHomeTeam);
        imageAwayTeam = findViewById(R.id.imageAwayTeam);
        eventResult = findViewById(R.id.eventResult);
        eventHeadline = findViewById(R.id.eventHeadline);
        eventDate = findViewById(R.id.eventDate);
        eventLocation = findViewById(R.id.eventLocation);
        eventLeague = findViewById(R.id.eventLeague);
        eventRound = findViewById(R.id.eventRound);
        errorMessage = findViewById(R.id.errorMessage);

        // Show a progressbar
        progressBar.setVisibility(View.VISIBLE);
        eventGroup.setVisibility(View.INVISIBLE);

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

                        // Load the two images
                        apiHelper.loadClubBadge(currentEvent.getIdHomeTeam(), imageHomeTeam);
                        apiHelper.loadClubBadge(currentEvent.getIdAwayTeam(), imageAwayTeam);

                        // Show the result of the current event (if match is finished)
                        if (currentEvent.getStrStatus().equals("Match Finished")) {
                            eventResult.setText(String.format(Locale.getDefault(),"%d : %d", currentEvent.getIntHomeScore(), currentEvent.getIntAwayScore()));
                        } else {
                            eventResult.setText("- : -");
                        }
                        eventHeadline.setText(currentEvent.getStrEvent());
                        eventDate.setText(currentEvent.getFormattedDateAndTime());
                        eventLocation.setText(currentEvent.getStrVenue());
                        eventLeague.setText(currentEvent.getStrLeague());
                        eventRound.setText(String.format(Locale.getDefault(),"%d", currentEvent.getIntRound()));

                        progressBar.setVisibility(View.INVISIBLE);
                        eventGroup.setVisibility(View.VISIBLE);
                    } else {
                        // Show error message
                        progressBar.setVisibility(View.INVISIBLE);
                        eventGroup.setVisibility(View.INVISIBLE);
                        errorMessage.setText(getString(R.string.apiResponseError));
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                    // Show error message
                    progressBar.setVisibility(View.INVISIBLE);
                    eventGroup.setVisibility(View.INVISIBLE);
                    errorMessage.setText(getString(R.string.apiErrorOnFailure));
                    errorMessage.setVisibility(View.VISIBLE);
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
            Intent intent = new Intent(this, ClubActivity.class);
            intent.putExtra("clubId", currentEvent.getIdHomeTeam());
            startActivity(intent);
        });

        imageAwayTeam.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClubActivity.class);
            intent.putExtra("clubId", currentEvent.getIdAwayTeam());
            startActivity(intent);
        });
    }

}