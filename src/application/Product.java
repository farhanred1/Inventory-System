package application;

public class Product {
	String name, description, type, bcode,id;
	int cost, sale, qty, reord;
	
	public Product() {
		super();
	}

	public Product(String id, String name, String description, String type, String bcode, int cost, int sale, int qty, int reord) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.bcode = bcode;
		this.cost = cost;
		this.sale = sale;
		this.qty = qty;
		this.reord = reord;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBcode() {
		return bcode;
	}

	public void setBcode(String bcode) {
		this.bcode = bcode;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getReord() {
		return reord;
	}

	public void setReord(int reord) {
		this.reord = reord;
	}
	
	public boolean checkStock() {
		boolean lowstock = false;
		if (qty<=reord)
			lowstock = true;
		return lowstock;
	}
	
	

}
