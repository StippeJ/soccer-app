package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Jan Stippe
 */
public class Event {

    @SerializedName("idEvent")
    private int idEvent;

    @SerializedName("dateEvent")
    private String dateEvent;

    @SerializedName("strOfficial")
    private String strOfficial;

    @SerializedName("intHomeShots")
    private int intHomeShots;

    @SerializedName("idLeague")
    private int idLeague;

    @SerializedName("intRound")
    private int intRound;

    @SerializedName("idHomeTeam")
    private int idHomeTeam;

    @SerializedName("intHomeScore")
    private int intHomeScore;

    @SerializedName("strTimestamp")
    private String strTimestamp;

    @SerializedName("strAwayTeam")
    private String strAwayTeam;

    @SerializedName("strDate")
    private String strDate;

    @SerializedName("idAwayTeam")
    private int idAwayTeam;

    @SerializedName("dateEventLocal")
    private String dateEventLocal;

    @SerializedName("strTime")
    private String strTime;

    @SerializedName("strVenue")
    private String strVenue;

    @SerializedName("strTimeLocal")
    private String strTimeLocal;

    @SerializedName("strSeason")
    private String strSeason;

    @SerializedName("strEventAlternate")
    private String strEventAlternate;

    @SerializedName("strEvent")
    private String strEvent;

    @SerializedName("strHomeTeam")
    private String strHomeTeam;

    @SerializedName("strThumb")
    private String strThumb;

    @SerializedName("strLeague")
    private String strLeague;

    @SerializedName("intAwayScore")
    private int intAwayScore;

    @SerializedName("strStatus")
    private String strStatus;

    public String getStrOfficial() {
        return strOfficial;
    }

    public int getIntHomeShots() {
        return intHomeShots;
    }

    public int getIdLeague() {
        return idLeague;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public int getIntRound() {
        return intRound;
    }

    public int getIdHomeTeam() {
        return idHomeTeam;
    }

    public int getIntHomeScore() {
        return intHomeScore;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getStrTimestamp() {
        return strTimestamp;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public String getStrDate() {
        return strDate;
    }

    public int getIdAwayTeam() {
        return idAwayTeam;
    }

    public String getDateEventLocal() {
        return dateEventLocal;
    }

    public String getStrTime() {
        return strTime;
    }

    public String getStrVenue() {
        return strVenue;
    }

    public String getStrTimeLocal() {
        return strTimeLocal;
    }

    public String getStrSeason() {
        return strSeason;
    }

    public String getStrEventAlternate() {
        return strEventAlternate;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public String getStrThumb() {
        return strThumb;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public int getIntAwayScore() {
        return intAwayScore;
    }

    public String getStrStatus() {
        return strStatus;
    }

}