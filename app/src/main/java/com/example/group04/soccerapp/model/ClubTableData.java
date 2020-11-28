package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Jan Stippe
 */
public class ClubTableData {

	@SerializedName("loss")
	private int loss;

	@SerializedName("total")
	private int total;

	@SerializedName("goalsfor")
	private int goalsFor;

	@SerializedName("goalsagainst")
	private int goalsAgainst;

	@SerializedName("teamid")
	private String teamId;

	@SerializedName("goalsdifference")
	private int goalsDifference;

	@SerializedName("name")
	private String name;

	@SerializedName("draw")
	private int draw;

	@SerializedName("played")
	private int played;

	@SerializedName("win")
	private int win;

	public int getLoss(){
		return loss;
	}

	public int getTotal(){
		return total;
	}

	public int getGoalsFor(){
		return goalsFor;
	}

	public int getGoalsAgainst(){
		return goalsAgainst;
	}

	public String getTeamId(){
		return teamId;
	}

	public int getGoalsDifference(){
		return goalsDifference;
	}

	public String getName(){
		return name;
	}

	public int getDraw(){
		return draw;
	}

	public int getPlayed(){
		return played;
	}

	public int getWin(){
		return win;
	}

}