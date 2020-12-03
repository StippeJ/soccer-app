package com.example.group04.soccerapp;

import android.util.Log;
import android.widget.ImageView;

import com.example.group04.soccerapp.model.ClubDetails;
import com.example.group04.soccerapp.model.ClubDetailsResponse;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void loadClubBadge(int clubId, ImageView imageView) {
        soccerRepo.getClubDetails(new Callback<ClubDetailsResponse>() {

            @Override
            public void onResponse(@NotNull Call<ClubDetailsResponse> call, @NotNull Response<ClubDetailsResponse> response) {
                if (response.isSuccessful()) {
                    ClubDetailsResponse cdr = response.body();
                    ClubDetails cd = cdr.getTeams().get(0);
                    Picasso.get().load(cd.getStrTeamBadge()).into(imageView);
                } else {
                    Log.d("ApiHelper", "onResponse not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClubDetailsResponse> call, @NotNull Throwable t) {
                Log.d("ApiHelper", "onFailure");
            }

        }, clubId);
    }

}
