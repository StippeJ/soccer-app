package com.example.group04.soccerapp;

import com.example.group04.soccerapp.model.ClubDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Jan Stippe
 */
public interface SoccerApi {

    @GET("lookupteam.php")
    Call<ClubDetailsResponse> getClubDetails(@Query("id") int teamId);

}
