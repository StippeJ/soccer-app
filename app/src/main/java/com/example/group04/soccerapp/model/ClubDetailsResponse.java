package com.example.group04.soccerapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jan Stippe
 */
public class ClubDetailsResponse {

	@SerializedName("teams")
	private List<ClubDetails> teams;

	public List<ClubDetails> getTeams(){
		return teams;
	}

}