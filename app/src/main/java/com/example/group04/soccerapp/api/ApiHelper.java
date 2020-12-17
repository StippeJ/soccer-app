package com.example.group04.soccerapp.api;

import android.util.Log;
import android.widget.ImageView;

import com.example.group04.soccerapp.R;
import com.example.group04.soccerapp.model.TeamDetails;
import com.example.group04.soccerapp.model.TeamDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Helper-Class for API-requests
 * Provides a method to request club-details and load an image into a given ImageView
 * @author Jan Stippe
 */
public class ApiHelper {

    private final SoccerRepo soccerRepo;

    /**
     * Constructor
     * @author Jan Stippe
     */
    public ApiHelper() {
        soccerRepo = new SoccerRepo();
    }

    // Getter for SoccerRepo
    public SoccerRepo getSoccerRepo() {
        return soccerRepo;
    }

    /**
     * Load the image of a clubs badge into a specified ImageView.
     * @param clubId Id of the club, for which the image should be requested from the API
     * @param imageView ImageView into which the image will be loaded
     * @author Jan Stippe
     */
    public void loadTeamBadge(int clubId, ImageView imageView) {
        soccerRepo.getTeamDetails(new Callback<TeamDetailsResponse>() {

            @Override
            public void onResponse(@NotNull Call<TeamDetailsResponse> call, @NotNull Response<TeamDetailsResponse> response) {
                if (response.isSuccessful()) {
                    TeamDetailsResponse cdr = response.body();
                    TeamDetails cd = cdr.getTeams().get(0);
                    Picasso.get().load(cd.getStrTeamBadge()).placeholder(R.drawable.placeholder_team_badge).into(imageView);
                } else {
                    Log.d("ApiHelper", "onResponse not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TeamDetailsResponse> call, @NotNull Throwable t) {
                Log.d("ApiHelper", "onFailure");
            }

        }, clubId);
    }

}
