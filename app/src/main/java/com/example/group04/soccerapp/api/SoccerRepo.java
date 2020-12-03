package com.example.group04.soccerapp.api;

import com.example.group04.soccerapp.api.SoccerApi;
import com.example.group04.soccerapp.model.ClubDetailsResponse;
import com.example.group04.soccerapp.model.EventsResponse;
import com.example.group04.soccerapp.model.TableResponse;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jan Stippe
 */
public class SoccerRepo {

    private final SoccerApi soccerApi;
    private final int bundesligaId = 4331;

    public SoccerRepo() {
        Gson gson = new Gson();

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

    public void getTable(Callback<TableResponse> callback, String season) {
        Call<TableResponse> call = soccerApi.getTable(bundesligaId, season);
        call.enqueue(callback);
    }

    public void getClubDetails(Callback<ClubDetailsResponse> callback, int clubId) {
        Call<ClubDetailsResponse> call = soccerApi.getClubDetails(clubId);
        call.enqueue(callback);
    }

    public void getNextEventsOfLeague(Callback<EventsResponse> callback) {
        Call<EventsResponse> call = soccerApi.getNextEventsOfLeague(bundesligaId);
        call.enqueue(callback);
    }

    public void getLastEventsOfLeague(Callback<EventsResponse> callback) {
        Call<EventsResponse> call = soccerApi.getLastEventsOfLeague(bundesligaId);
        call.enqueue(callback);
    }

    public void getNextEventsOfClub(Callback<EventsResponse> callback, int clubId) {
        Call<EventsResponse> call = soccerApi.getNextEventsOfClub(clubId);
        call.enqueue(callback);
    }

    public void getLastEventsOfClub(Callback<EventsResponse> callback, int clubId) {
        Call<EventsResponse> call = soccerApi.getLastEventsOfClub(clubId);
        call.enqueue(callback);
    }

    public void getEventById(Callback<EventsResponse> callback, int eventId) {
        Call<EventsResponse> call = soccerApi.getEventById(eventId);
        call.enqueue(callback);
    }

}
