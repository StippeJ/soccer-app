package com.example.group04.soccerapp.api;

import com.example.group04.soccerapp.model.ClubDetailsResponse;
import com.example.group04.soccerapp.model.EventsResponse;
import com.example.group04.soccerapp.model.TableResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface to implemtent API-requests
 * @author Jan Stippe
 */
public interface SoccerApi {

    @GET("lookuptable.php")
    Call<TableResponse> getTable(@Query("l") int leagueId, @Query("s") String season);

    @GET("lookupteam.php")
    Call<ClubDetailsResponse> getTeamDetails(@Query("id") int clubId);

    @GET("eventsnextleague.php")
    Call<EventsResponse> getNextEventsOfLeague(@Query("id") int leagueId);

    @GET("eventspastleague.php")
    Call<EventsResponse> getLastEventsOfLeague(@Query("id") int leagueId);

    @GET("lookupevent.php")
    Call<EventsResponse> getEventById(@Query("id") int eventId);

}
