package dea_s_hw3;

public abstract class Aircraft implements Contact{
	// Private member variables
	private int length;
	private int speed;
	private String name;
	private String type;
	
	private int altitude;
	
	public Aircraft() {
		this.length = 0;
		this.speed = 0;
		this.name = "";
		this.type = "";
		this.altitude = 0;
	}
	
	public Aircraft(int length, int speed, String name, String type, int altitude) {
		this.length = length;
		this.speed = speed;
		this.name = name;
		this.type = type;
		this.altitude = altitude;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		// Validate input integer is > 0
		if (length < 0) {
			System.out.println("Value must be a positive integer.");
		}			
		else {
			this.length = length;
		}
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		// Error handling if the input is not a correct integer
		try {
			this.speed = Integer.parseInt(speed);
			if (this.speed < 0) {
				this.speed = 0;
				System.out.println("Value must be a positive integer.");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Value must be a positive integer.");
			}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	// Additional Altitude Methods
	public int getAltitude() {
		return altitude;
	}
	
	public void setAltitude(int altitude) {
		if (altitude < 0) {
			System.out.println("Value must be a positive integer");
		}
		else {
			this.altitude = altitude;
		}
	}
	
	
}
