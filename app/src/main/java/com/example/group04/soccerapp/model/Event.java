package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private Integer intHomeScore;

    @SerializedName("strTimestamp")
    private Date dateTimestamp;

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
    private Integer intAwayScore;

    @SerializedName("strStatus")
    private String strStatus;

    /**
     * Get the local date and time of the event in a suitable format
     * @return Date as string (dd.MM.yyyy, HH:mm)
     * @author Jan Stippe
     */
    public String getFormattedDateAndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault());
        return dateFormat.format(getDateTimestamp());
    }

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

    public Integer getIntHomeScore() {
        return intHomeScore;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public Date getDateTimestamp() {
        return dateTimestamp;
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

    public Integer getIntAwayScore() {
        return intAwayScore;
    }

    public String getStrStatus() {
        return strStatus;
    }

}