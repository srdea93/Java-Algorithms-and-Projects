package dea_s_hw13;

public class Hike {
	private int month;
	private int day;
	private int year;
	private int hikeID;
	private int length;
	private int hikers;
	private int price;
	private String errorMessage = "";
	
	public Hike() {
		month = 0;
		day = 0;
		year = 0;
		hikeID = 0;
		length = 0;
		hikers = 0;
		price = 0;
		errorMessage = "";
	}
	
	public Hike(int month, int day, int year, int hikeID, int length, int hikers, int price) {
		this.month = month;
		this.day = day;
		this.year = year;
		this.hikeID = hikeID;
		this.length = length;
		this.hikers = hikers;  
		this.price = price;
	}
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getHikeID() {
		return hikeID;
	}
	public void setHikeID(int hikeID) {
		this.hikeID = hikeID;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHikers() {
		return hikers;
	}
	public void setHikers(int hikers) {
		this.hikers = hikers;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// Error handling methods to make sure Hike bean values are valid. If they are not, return false
	public boolean checkDate() {
		if(this.day > 31 || this.day < 1 || this.month > 12 || this.month < 1 || this.year > 2025 || this.year < 2020) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkHikeID() {
		if(this.hikeID < 0 || this.hikeID > 2) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkLength() {
		if(this.length > 7 || this.length < 2) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkHikers() {
		if(this.hikers > 10 || this.hikers < 1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// Check if the combination of day/month is valid. If the day is a bad combo, return false
	public boolean checkDayMonthCombo() {
		BookingDay bookDay = new BookingDay(this.year, this.month, this.day);
		if(bookDay.badDay()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkHikeCombo() {
		boolean isGood = true;
		if(this.hikeID == 0) {
			if(this.length == 3 || this.length == 5) {
				isGood = true;
			}
			else {
				isGood = false;
			}
		}
		if(this.hikeID == 1) {
			if(this.length == 2 || this.length == 3 || this.length == 4) {
				isGood = true;
			}
			else {
				isGood = false;
			}
		}
		if(this.hikeID == 2) {
			if(this.length == 5 || this.length == 7) {
				isGood = true;
			}
			else {
				isGood = false;
			}
		}
		
		return isGood;
	}
	
	public boolean checkSeason() {
		boolean isGood = true;
		if(this.month < 5 || this.month > 9) {
			isGood = false;
		}
		return isGood;
	}
	
	public void addErrorMessage(String message) {
		this.errorMessage = this.errorMessage + " " + message;
	}
	
	public int calcPrice() {
		// All error handling dealt with during the parsing method
		int cost = 0;
		int hikePrice = 0;
		if (this.hikeID == 0) {
			hikePrice = 40;
		}
		if (this.hikeID == 1) {
			hikePrice = 35;
		}
		if (this.hikeID == 2) {
			hikePrice = 45;
		}
		
		cost = hikePrice * this.length * this.hikers;
		return cost;
	}
	
	
}
