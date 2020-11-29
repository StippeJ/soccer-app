package com.example.group04.soccerapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jan Stippe
 */
public class EventsResponse {

	@SerializedName(value = "events", alternate = "results")
	private List<Event> events;

	public List<Event> getEvents(){
		return events;
	}

}