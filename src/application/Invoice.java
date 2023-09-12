package application;

public class Invoice {
	
	String pid, pname;
	double price,total;
	int quantity;
	

	public Invoice(String pdid, String pdname, double price, double total, int quantity) {
		super();
		this.pid = pdid;
		this.pname = pdname;
		this.price = price;
		this.total = total;
		this.quantity = quantity;
	}

	public Invoice() {
		super();
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}


	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	

}
