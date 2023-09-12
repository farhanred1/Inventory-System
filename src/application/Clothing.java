package application;

public class Clothing extends Product {
	String colour,id;


	public Clothing(String colour, String id) {
		super();
		this.colour = colour;
		this.id = id;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
