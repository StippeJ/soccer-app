package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class to save data about an event
 * @author Jan Stippe
 */
public class Event {

    @SerializedName("idEvent")
    private int idEvent;

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

    @SerializedName("idAwayTeam")
    private int idAwayTeam;

    @SerializedName("strVenue")
    private String strVenue;

    @SerializedName("strEvent")
    private String strEvent;

    @SerializedName("strHomeTeam")
    private String strHomeTeam;

    @SerializedName("strLeague")
    private String strLeague;

    @SerializedName("intAwayScore")
    private Integer intAwayScore;

    /**
     * Get the local date and time of the event in a suitable format
     * @return Date as string (dd.MM.yyyy, HH:mm)
     * @author Jan Stippe
     */
    public String getFormattedDateAndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault());
        return dateFormat.format(getDateTimestamp());
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

    public Date getDateTimestamp() {
        return dateTimestamp;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public int getIdAwayTeam() {
        return idAwayTeam;
    }

    public String getStrVenue() {
        return strVenue;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public Integer getIntAwayScore() {
        return intAwayScore;
    }

}