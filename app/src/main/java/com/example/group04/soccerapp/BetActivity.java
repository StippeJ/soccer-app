package com.example.group04.soccerapp;

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

import androidx.constraintlayout.widget.Group;

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
    Group viewGroup;
    ImageView imageHomeTeam;
    ImageView imageAwayTeam;
    EditText scoreHome;
    EditText scoreAway;
    TextView headline;
    TextView date;
    TextView location;
    TextView league;
    TextView round;
    TextView errorMessage;
    Button saveBet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        // Create new ApiHelper-object
        apiHelper = new ApiHelper();

        // Reference Views of the layout
        progressBar = findViewById(R.id.betProgressSpinner);
        viewGroup = findViewById(R.id.betGroup);
        imageHomeTeam = findViewById(R.id.betImageHomeTeam);
        imageAwayTeam = findViewById(R.id.betImageAwayTeam);
        scoreHome = findViewById(R.id.betScoreHome);
        scoreAway = findViewById(R.id.betScoreAway);
        headline = findViewById(R.id.betHeadline);
        date = findViewById(R.id.betDate);
        location = findViewById(R.id.betLocation);
        league = findViewById(R.id.betLeague);
        round = findViewById(R.id.betRound);
        errorMessage = findViewById(R.id.betErrorMessage);
        saveBet = findViewById(R.id.betSaveButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int eventId = bundle.getInt("eventId");

            // show a progressbar
            progressBar.setVisibility(View.VISIBLE);
            viewGroup.setVisibility(View.INVISIBLE);

            // request data from api
            apiHelper.getSoccerRepo().getEventById(new Callback<EventsResponse>() {
                @Override
                public void onResponse(@NotNull Call<EventsResponse> call, @NotNull Response<EventsResponse> response) {
                    if (response.isSuccessful()) {
                        // Get the event
                        EventsResponse er = response.body();
                        currentEvent = er.getEvents().get(0);

                        fillViewsWithData();

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

        saveBet.setOnClickListener(v -> saveBet());
    }

    /**
     * Get bets from SharedPreferences
     * @return Saved bets in a BetList-object
     * @author Jan Stippe
     */
    private BetList getBets() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String betListString = sharedPreferences.getString(BET_LIST, "{'betList':[]}");

        Gson gson = new Gson();

        return gson.fromJson(betListString, BetList.class);
    }

    /**
     * Save a bet to the SharedPreferences
     * @author Jan Stippe
     */
    private void saveBet() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Read the content of the textboxes
        String stringScoreHome = scoreHome.getText().toString();
        String stringScoreAway = scoreAway.getText().toString();

        // Check if boxes were filled by user
        if (stringScoreHome.length() == 0 || stringScoreAway.length() == 0) {
            Toast.makeText(this, R.string.emptyEditText, Toast.LENGTH_SHORT).show();
        } else {
            // Cast scores to int
            int scoreHome = Integer.parseInt(stringScoreHome);
            int scoreAway = Integer.parseInt(stringScoreAway);

            // Create a new bet
            Bet bet = new Bet(currentEvent.getIdEvent(), scoreHome, scoreAway);
            Gson gson = new Gson();

            // Get currently saved bets and add the new one to the list
            BetList betList = getBets();
            betList.addBet(bet);

            // Serialize bets to json
            String jsonBets = gson.toJson(betList);

            // Save json to SharedPreferences
            editor.putString(BET_LIST, jsonBets);
            editor.apply();

            // Show success-message
            Toast.makeText(this, R.string.betSaved, Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    /**
     * Hide all views besides the error-message
     * Set a given text for the error-message-view
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
     * Load the currentEvent's data into the Views
     * @author Jan Stippe
     */
    private void fillViewsWithData() {
        // Load the two images
        apiHelper.loadTeamBadge(currentEvent.getIdHomeTeam(), imageHomeTeam);
        apiHelper.loadTeamBadge(currentEvent.getIdAwayTeam(), imageAwayTeam);

        headline.setText(currentEvent.getStrEvent());
        date.setText(currentEvent.getFormattedDateAndTime());
        location.setText(currentEvent.getStrVenue());
        league.setText(currentEvent.getStrLeague());
        round.setText(String.format(Locale.getDefault(),"%d", currentEvent.getIntRound()));
    }
}