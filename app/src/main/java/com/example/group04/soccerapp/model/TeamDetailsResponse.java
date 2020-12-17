package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class to save API-response when teams are requested
 * @author Jan Stippe
 */
public class TeamDetailsResponse {

	@SerializedName("teams")
	private List<TeamDetails> teams;

	public List<TeamDetails> getTeams(){
		return teams;
	}

}