package com.example.group04.soccerapp.model;

import java.util.List;

/**
 * Class to save a list of bets
 * @author Jan Stippe
 */
public class BetList {

    private final List<Bet> betList;

    // Constructor
    public BetList(List<Bet> betList) {
        this.betList = betList;
    }

    // Getter-method
    public List<Bet> getBetList() {
        return betList;
    }

    // Add a bet to the list
    public void addBet(Bet bet) {
        betList.add(bet);
    }

}
