package model;

public class Boat {
	private BoatType type;
	private double size;
	
	// Added so ObjectMapper has default creator for creating Members from JSON string
	public Boat() {
		
	}
	public Boat(BoatType type, double size) {
		this.type = type;
		this.size = size;
	}
	
	public BoatType getType() {
		return type;
	}

	public double getSize() {
		return size;
	}
	
	public void setType(BoatType type) {
		this.type = type;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return type.toString();
	}
}
