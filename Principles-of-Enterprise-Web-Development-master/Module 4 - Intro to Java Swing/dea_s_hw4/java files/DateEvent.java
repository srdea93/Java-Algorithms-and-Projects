package dea_s_hw4;

import java.util.EventObject;

// Class to hold the events from the DatePanel to interface with HikeBookingGUI
public class DateEvent extends EventObject {
	
	private int day;
	private int month;
	private int year;
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public DateEvent(Object source) {
		super(source);
	}
	
	public DateEvent(Object source, int day, int month, int year) {
		super(source);
		this.day = day;
		this.month = month;
		this.year = year;
	}

}
