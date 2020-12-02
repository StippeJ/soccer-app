package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group04.soccerapp.model.ClubDetails;
import com.example.group04.soccerapp.model.ClubDetailsResponse;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    // SoccerRepo to access API
    SoccerRepo soccerRepo;

    // Event that is currently visible
    Event currentEvent;

    // Views to show data from API
    Group eventGroup;
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

        soccerRepo = new SoccerRepo();

        eventGroup = findViewById(R.id.eventGroup);
        imageHomeTeam = findViewById(R.id.imageHomeTeam);
        imageAwayTeam = findViewById(R.id.imageAwayTeam);
        eventResult = findViewById(R.id.matchResult);
        eventHeadline = findViewById(R.id.eventHeadline);
        eventDate = findViewById(R.id.eventDate);
        eventLocation = findViewById(R.id.eventLocation);
        eventLeague = findViewById(R.id.eventLeague);
        eventRound = findViewById(R.id.eventRound);
        errorMessage = findViewById(R.id.errorMessage);

        // Get Intent that should contain the eventId
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int eventId = bundle.getInt("eventId");

            soccerRepo.getEventById(new Callback<EventsResponse>() {

                @Override
                public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                    if (response.isSuccessful()) {
                        // Get the event
                        EventsResponse er = response.body();
                        currentEvent = er.getEvents().get(0);

                        // Load the two images
                        loadClubBadge(currentEvent.getIdHomeTeam(), imageHomeTeam);
                        loadClubBadge(currentEvent.getIdAwayTeam(), imageAwayTeam);

                        // Show the result of the current event (if match is finished)
                        if (currentEvent.getStrStatus().equals("Match Finished")) {
                            eventResult.setText(String.format("%d : %d", currentEvent.getIntHomeScore(), currentEvent.getIntAwayScore()));
                        } else {
                            eventResult.setText("- : -");
                        }
                        eventHeadline.setText(currentEvent.getStrEvent());
                        eventDate.setText(getDateAndTime());
                        eventLocation.setText(currentEvent.getStrVenue());
                        eventLeague.setText(currentEvent.getStrLeague());
                        eventRound.setText(Integer.toString(currentEvent.getIntRound()));
                    } else {
                        // Show error message
                        eventGroup.setVisibility(View.INVISIBLE);
                        errorMessage.setText(getString(R.string.apiResponseError));
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                    // Show error message
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

    /**
     * Load the image of a clubs badge into a specified ImageView.
     * @param clubId Id of the club, for which the image should be requested from the API
     * @param imageView ImageView into which the image will be loaded
     * @author Jan Stippe
     */
    private void loadClubBadge(int clubId, ImageView imageView) {
        soccerRepo.getClubDetails(new Callback<ClubDetailsResponse>() {

            @Override
            public void onResponse(@NotNull Call<ClubDetailsResponse> call, @NotNull Response<ClubDetailsResponse> response) {
                if (response.isSuccessful()) {
                    ClubDetailsResponse cdr = response.body();
                    ClubDetails cd = cdr.getTeams().get(0);
                    Picasso.get().load(cd.getStrTeamBadge()).into(imageView);
                } else {
                    Log.d("MatchdayActivity", "onResponse not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClubDetailsResponse> call, @NotNull Throwable t) {
                Log.d("MatchdayActivity", "onFailure");
            }

        }, clubId);
    }

    /**
     * Get the local date and time of the event in a suitable format
     * @return Date as string (dd.MM.yyyy, HH:mm)
     * @author Jan Stippe
     */
    private String getDateAndTime() {
        String[] dateParts = currentEvent.getDateEvent().split("-");
        String[] timeParts = currentEvent.getStrTime().split(":");
        return String.format("%s.%s.%s, %s:%s", dateParts[2], dateParts[1], dateParts[0], timeParts[0], timeParts[1]);
    }
}