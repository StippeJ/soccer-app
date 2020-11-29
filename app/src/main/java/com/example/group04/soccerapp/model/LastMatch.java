package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author André Bautz
 */
public class LastMatch {

    @SerializedName("strTimestamp")
    private Date dateOfMatch;

    @SerializedName("strHomeTeam")
    private String homeClub;

    @SerializedName("intHomeScore")
    private int homeScore;

    @SerializedName("intAwayScore")
    private int awayScore;

    @SerializedName("strAwayTeam")
    private String awayClub;

    public Date getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(Date dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public String getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(String homeClub) {
        this.homeClub = homeClub;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(String awayClub) {
        this.awayClub = awayClub;
    }
}
