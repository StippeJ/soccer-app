package com.example.group04.soccerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.group04.soccerapp.model.ClubDetails;
import com.example.group04.soccerapp.model.ClubDetailsResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubActivity extends AppCompatActivity {

    SoccerRepo soccerRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        soccerRepo = new SoccerRepo();
    }

    /**
     * Request details about a club from the API
     * @param clubId Id of the club for which the details should be loaded
     * @author Jan Stippe
     */
    public void loadClubDetails(int clubId) {
        soccerRepo.getClubDetails(new Callback<ClubDetailsResponse>() {

            @Override
            public void onResponse(@NotNull Call<ClubDetailsResponse> call, @NotNull Response<ClubDetailsResponse> response) {
                if (response.isSuccessful()) {
                    ClubDetailsResponse cdr = response.body();
                    ClubDetails cd = cdr.getTeams().get(0);
                    Log.d("ClubActivity", "onResponse successful: " + cd.getStrAlternate());
                } else {
                    Log.d("ClubActivity", "onResponse not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClubDetailsResponse> call, @NotNull Throwable t) {
                Log.d("MainActivity", "onFailure");
            }

        }, clubId);
    }

}