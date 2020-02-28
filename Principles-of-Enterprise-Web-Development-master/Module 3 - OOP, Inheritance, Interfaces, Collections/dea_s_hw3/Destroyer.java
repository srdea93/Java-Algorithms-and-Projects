package dea_s_hw3;

public class Destroyer extends Ship {
	private int numberMissiles;
	
	// Constructor
	public Destroyer() {
		this.numberMissiles = 0;
	}
	
	public Destroyer(int length, String speed, String name, String type, int numberMissiles) {
		this.setLength(length);
		this.setSpeed(speed);
		this.setName(name);
		this.setType(type);
		this.numberMissiles = numberMissiles;
	}

	public int getNumberMissiles() {
		return numberMissiles;
	}

	public void setNumberMissiles(int numberMissiles) {
		this.numberMissiles = numberMissiles;
	}
	// Error handling for any parsing that is not a usable integer
	public void setNumberMissiles(String numberMissiles) {
		try {
			this.numberMissiles = Integer.parseInt(numberMissiles);
		}
		catch (NumberFormatException e) {
			this.numberMissiles = 2;
		}
	}
	
	@Override
	public String toString() {
		return "Name: " + this.getName() + "\n" + 
				"Type: " + this.getType() + "\n" + 
				"Length: " + this.getLength() + "\n" + 
				"Speed: " + this.getSpeed() + "\n" + 
				"Number of Missiles: " + this.numberMissiles;
	}
}
