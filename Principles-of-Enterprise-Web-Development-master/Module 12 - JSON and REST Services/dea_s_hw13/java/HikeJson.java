package dea_s_hw13;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HikeJson {
	private String month;
	private String day;
	private String year;
	private String hikeID;
	private String length;
	private String hikers;
	
	public HikeJson() {
		super();
	}
	
	@JsonProperty("MONTH")
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@JsonProperty("DAY")
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	@JsonProperty("YEAR")
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@JsonProperty("HIKEID")
	public String getHikeID() {
		return hikeID;
	}
	public void setHikeID(String hikeID) {
		this.hikeID = hikeID;
	}
	@JsonProperty("LENGTH")
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@JsonProperty("HIKERS")
	public String getHikers() {
		return hikers;
	}
	public void setHikers(String hikers) {
		this.hikers = hikers;
	}
	@Override
	public String toString() {
		return "HikeJson [month=" + month + ", day=" + day + ", year=" + year + ", hikeID=" + hikeID + ", length="
				+ length + ", hikers=" + hikers + "]";
	}
	
	
}
