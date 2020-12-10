package com.example.group04.soccerapp.model;

import java.util.Collections;
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

	/**
	 * Get list of events sorted by date
	 * List is sorted ascending (e. g. 1.1.2020, 2.1.2020 -> 3.2.2020)
	 * @return List of events
	 * @author Jan Stippe
	 */
	public List<Event> getAscendingEvents() {
		Collections.sort(events, (e1, e2) -> e1.getDateTimestamp().compareTo(e2.getDateTimestamp()));
		return events;
	}

	/**
	 * Get list of events sorted by date
	 * List is sorted descending (e. g. 3.1.2020, 2.1.2020 -> 1.2.2020)
	 * @return List of events
	 * @author Jan Stippe
	 */
	public List<Event> getDescendingEvents() {
		Collections.sort(events, (e1, e2) -> e2.getDateTimestamp().compareTo(e1.getDateTimestamp()));
		return events;
	}

}