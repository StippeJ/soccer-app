package com.example.group04.soccerapp;

import com.example.group04.soccerapp.model.ClubDetailsResponse;
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

    SoccerApi soccerApi;

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

    public void getClubDetails(Callback<ClubDetailsResponse> callback, int teamId) {
        Call<ClubDetailsResponse> call = soccerApi.getClubDetails(teamId);
        call.enqueue(callback);
    }

}
