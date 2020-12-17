package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Jan Stippe
 */
public class TableResponse {

	@SerializedName("table")
	private List<TeamTableData> table;

	public List<TeamTableData> getTable(){
		return table;
	}

}