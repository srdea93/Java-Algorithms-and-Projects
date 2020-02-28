package dea_s_hw3;

public class P3 extends Aircraft{
	private int numberEngines;
	
	// Constructor
	public P3(int length, String speed, String name, String type, int altitude, int numberEngines) {
		this.setLength(length);
		this.setSpeed(speed);
		this.setName(name);
		this.setType(type);
		this.setAltitude(altitude);
		this.numberEngines = numberEngines;
	}

	public int getNumberEngines() {
		return numberEngines;
	}

	public void setNumberEngines(int numberEngines) {
		if (numberEngines < 0) {
			System.out.println("Value must be a positive integer");
		}
		else {
			this.numberEngines = numberEngines;
		}
	}
	
	@Override
	public String toString() {
		return "Name: " + this.getName() + "\n" + 
				"Type: " + this.getType() + "\n" + 
				"Length: " + this.getLength() + "\n" + 
				"Speed: " + this.getSpeed() + "\n" + 
				"Altitude: " + this.getAltitude() + "\n" +
				"Number of Engines: " + this.numberEngines;
	}
}
