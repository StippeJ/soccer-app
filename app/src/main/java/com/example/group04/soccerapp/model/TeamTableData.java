package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class to save data about a team in the table
 * @author Jan Stippe
 */
public class TeamTableData {

	@SerializedName("intLoss")
	private int loss;

	@SerializedName("idTeam")
	private int teamId;

	@SerializedName("intRank")
	private int rank;

	@SerializedName("strName")
	private String name;

	@SerializedName("intDraw")
	private int draw;

	@SerializedName("intWin")
	private int win;

	public int getLoss(){
		return loss;
	}

	public int getTeamId(){
		return teamId;
	}

	public int getRank() {
		return rank;
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