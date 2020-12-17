package com.example.group04.soccerapp.api;

import com.example.group04.soccerapp.model.EventsResponse;
import com.example.group04.soccerapp.model.TableResponse;
import com.example.group04.soccerapp.model.TeamDetailsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implements the API-requests of the SoccerApi-interface
 * Api-requests are based on the "thesportsdb.com"-API
 * @author Jan Stippe
 */
public class SoccerRepo {

    private final SoccerApi soccerApi;
    // Id of the Bundesliga can be changed here for all API-requests
    private final int bundesligaId = 4331;

    public SoccerRepo() {
        // Allow dates to be create from strings of a given format
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .create();

        // Add Logging to HTTP-requests
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        soccerApi = retrofit.create(SoccerApi.class);
    }

    // Get the current table of the 1. Bundesliga for a given season
    // Season has to be of a format like "2019-2020"
    public void getTable(Callback<TableResponse> callback, String season) {
        Call<TableResponse> call = soccerApi.getTable(bundesligaId, season);
        call.enqueue(callback);
    }

    // Get details about a team with a given id
    public void getTeamDetails(Callback<TeamDetailsResponse> callback, int teamId) {
        Call<TeamDetailsResponse> call = soccerApi.getTeamDetails(teamId);
        call.enqueue(callback);
    }

    // Get the next 15 events of the 1. Bundesliga
    public void getNextEventsOfLeague(Callback<EventsResponse> callback) {
        Call<EventsResponse> call = soccerApi.getNextEventsOfLeague(bundesligaId);
        call.enqueue(callback);
    }

    // Get the last 15 events of the 1. Bundesliga
    public void getLastEventsOfLeague(Callback<EventsResponse> callback) {
        Call<EventsResponse> call = soccerApi.getLastEventsOfLeague(bundesligaId);
        call.enqueue(callback);
    }

    // Get details about an event with a given id
    public void getEventById(Callback<EventsResponse> callback, int eventId) {
        Call<EventsResponse> call = soccerApi.getEventById(eventId);
        call.enqueue(callback);
    }

}
