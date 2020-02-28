package dea_s_hw3;

public class Submarine extends Ship{
	private int numberTorpedoes;

	// Constructor
	public Submarine() {
		this.numberTorpedoes = 0;
	}
	
	public Submarine(int length, String speed, String name, String type, int numberTorpedoes) {
		this.setLength(length);
		this.setSpeed(speed);
		this.setName(name);
		this.setType(type);
		this.numberTorpedoes = numberTorpedoes;
	}
	
	public int getNumberTorpedoes() {
		return numberTorpedoes;
	}

	public void setNumberTorpedoes(int numberTorpedoes) {
		this.numberTorpedoes = numberTorpedoes;
	}
	
	public void setNumberTorpedoes(String numberTorpedoes) {
		// Error handling for any parsing that is not a usable integer
		try {
			this.numberTorpedoes = Integer.parseInt(numberTorpedoes);
		}
		catch (NumberFormatException e) {
			this.numberTorpedoes = 2;
		}
	}
	
	@Override
	public String toString() {
		return "Name: " + this.getName() + "\n" + 
				"Type: " + this.getType() + "\n" + 
				"Length: " + this.getLength() + "\n" + 
				"Speed: " + this.getSpeed() + "\n" + 
				"Number of Torpedoes: " + this.numberTorpedoes;
	}
	
}
