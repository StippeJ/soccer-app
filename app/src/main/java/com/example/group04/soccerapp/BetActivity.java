package com.example.group04.soccerapp;

import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group04.soccerapp.api.ApiHelper;
import com.example.group04.soccerapp.model.Bet;
import com.example.group04.soccerapp.model.BetList;
import com.example.group04.soccerapp.model.Event;
import com.example.group04.soccerapp.model.EventsResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity to bet on a match
 * @author Jan Stippe
 */
public class BetActivity extends BaseActivity {

    // Object to access API
    ApiHelper apiHelper;

    // Event that is currently visible
    Event currentEvent;

    // Views to show data from API
    ProgressBar progressBar;
    Group eventGroup;
    ImageView imageHomeTeam;
    ImageView imageAwayTeam;
    EditText editTextScoreHome;
    EditText editTextScoreAway;
    TextView eventHeadline;
    TextView eventDate;
    TextView eventLocation;
    TextView eventLeague;
    TextView eventRound;
    TextView errorMessage;
    Button saveBet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        apiHelper = new ApiHelper();

        progressBar = findViewById(R.id.betProgressSpinner);
        eventGroup = findViewById(R.id.eventGroup);
        imageHomeTeam = findViewById(R.id.imageHomeTeam);
        imageAwayTeam = findViewById(R.id.imageAwayTeam);
        editTextScoreHome = findViewById(R.id.eventScoreHome);
        editTextScoreAway = findViewById(R.id.eventScoreAway);
        eventHeadline = findViewById(R.id.eventHeadline);
        eventDate = findViewById(R.id.eventDate);
        eventLocation = findViewById(R.id.eventLocation);
        eventLeague = findViewById(R.id.eventLeague);
        eventRound = findViewById(R.id.eventRound);
        errorMessage = findViewById(R.id.errorMessage);
        saveBet = findViewById(R.id.saveBetButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int eventId = bundle.getInt("eventId");

            // show a progressbar
            progressBar.setVisibility(View.VISIBLE);
            eventGroup.setVisibility(View.INVISIBLE);

            // request data from api
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

        saveBet.setOnClickListener(v -> saveBet());
    }

    /**
     * Get bets from SharedPreferences
     * @return Saved bets in a BetList-object
     */
    private BetList getBets() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String betListString = sharedPreferences.getString(BET_LIST, "{'betList':[]}");

        Gson gson = new Gson();

        return gson.fromJson(betListString, BetList.class);
    }

    /**
     * Save a bet to the SharedPreferences
     */
    private void saveBet() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String stringScoreHome = editTextScoreHome.getText().toString();
        String stringScoreAway = editTextScoreAway.getText().toString();

        if (stringScoreHome.length() == 0 || stringScoreAway.length() == 0) {
            Toast.makeText(this, R.string.emptyEditText, Toast.LENGTH_SHORT).show();
        } else {
            int scoreHome = Integer.parseInt(stringScoreHome);
            int scoreAway = Integer.parseInt(stringScoreAway);

            Bet bet = new Bet(currentEvent.getIdEvent(), scoreHome, scoreAway);
            Gson gson = new Gson();

            BetList betList = getBets();
            betList.addBet(bet);

            String jsonBets = gson.toJson(betList);

            editor.putString(BET_LIST, jsonBets);
            editor.apply();
            finish();
        }
    }
}