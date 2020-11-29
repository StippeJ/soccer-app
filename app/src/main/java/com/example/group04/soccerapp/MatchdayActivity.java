package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class MatchdayActivity extends AppCompatActivity {

    // SoccerRepo to access API
    SoccerRepo soccerRepo;

    // Views to show data from API
    ImageView imageHomeTeam;
    ImageView imageAwayTeam;
    TextView matchdayHeadline;
    TextView matchResult;

    /**
     * Get Intent-extra that contains the id of a match.
     * Load the result of the match and the badges of the two clubs.
     * @param savedInstanceState Saved state of the Activity
     * @author Jan Stippe
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchday);

        soccerRepo = new SoccerRepo();

        imageHomeTeam = findViewById(R.id.imageHomeTeam);
        imageAwayTeam = findViewById(R.id.imageAwayTeam);
        matchResult = findViewById(R.id.matchResult);
        matchdayHeadline = findViewById(R.id.matchdayHeadline);

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
                        Event e = er.getEvents().get(0);

                        // Load the two images
                        loadClubBadge(e.getIdHomeTeam(), imageHomeTeam);
                        loadClubBadge(e.getIdAwayTeam(), imageAwayTeam);

                        // Show the result of the current match
                        matchResult.setText(String.format("%d : %d", e.getIntHomeScore(), e.getIntAwayScore()));
                        matchdayHeadline.setText(e.getStrEvent());

                        Log.d("MatchdayActivity", "onResponse successful: " + e.getStrEvent());
                    } else {
                        Log.d("MatchdayActivity", "onResponse not successful: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EventsResponse> call, @NotNull Throwable t) {
                    Log.d("MatchdayActivity", "onFailure");
                }

            }, eventId);
        }
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
}