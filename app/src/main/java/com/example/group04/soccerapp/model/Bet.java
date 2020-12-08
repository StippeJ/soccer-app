package com.example.group04.soccerapp.model;

/**
 * Class to save a bet for an event
 * @author Jan Stippe
 */
public class Bet {

    private int eventId;
    private int scoreHome;
    private int scoreAway;

    // Constructor
    public Bet(int eventId, int scoreHome, int scoreAway) {
        this.eventId = eventId;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
    }

    // Getter-methods to acces data
    public int getEventId() {
        return eventId;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

}
