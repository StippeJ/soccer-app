package com.example.group04.soccerapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jan Stippe
 */
public class TableResponse {

	@SerializedName("table")
	private List<ClubTableData> table;

	public List<ClubTableData> getTable(){
		return table;
	}

}