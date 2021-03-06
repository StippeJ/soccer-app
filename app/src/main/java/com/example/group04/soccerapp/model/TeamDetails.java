package com.example.group04.soccerapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class to represent data about a team given by the API
 * @author Jan Stippe
 */
public class TeamDetails {

	@SerializedName("strTeam")
	private String strTeam;

	@SerializedName("strTeamBadge")
	private String strTeamBadge;

	@SerializedName("intFormedYear")
	private int intFormedYear;

	@SerializedName("strStadium")
	private String strStadium;

	@SerializedName("strStadiumThumb")
	private String strStadiumThumb;

	@SerializedName("intStadiumCapacity")
	private int intStadiumCapacity;

	@SerializedName("strDescriptionEN")
	private String strDescriptionEN;

	@SerializedName("strDescriptionDE")
	private String strDescriptionDE;

	@SerializedName("strFacebook")
	private String strFacebook;

	@SerializedName("strInstagram")
	private String strInstagram;

	@SerializedName("strTwitter")
	private String strTwitter;

	public String getStrTeam(){
		return strTeam;
	}

	public String getStrTeamBadge(){
		return strTeamBadge;
	}

	public int getIntFormedYear(){
		return intFormedYear;
	}

	public String getStrStadium(){
		return strStadium;
	}

	public String getStrStadiumThumb(){
		return strStadiumThumb;
	}

	public int getIntStadiumCapacity(){
		return intStadiumCapacity;
	}

	public String getStrDescriptionEN(){
		return strDescriptionEN;
	}

	public String getStrDescriptionDE(){
		return strDescriptionDE;
	}

	public String getStrFacebook(){
		return strFacebook;
	}

	public String getStrInstagram(){
		return strInstagram;
	}

	public String getStrTwitter(){
		return strTwitter;
	}

}