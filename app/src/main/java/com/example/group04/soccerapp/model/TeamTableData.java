package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class to save data about a team in the table
 * @author Jan Stippe
 */
public class TeamTableData {

	@SerializedName("loss")
	private int loss;

	@SerializedName("teamid")
	private int teamId;

	@SerializedName("name")
	private String name;

	@SerializedName("draw")
	private int draw;

	@SerializedName("win")
	private int win;

	public int getLoss(){
		return loss;
	}

	public int getTeamId(){
		return teamId;
	}

	public String getName(){
		return name;
	}

	public int getDraw(){
		return draw;
	}

	public int getWin(){
		return win;
	}

}