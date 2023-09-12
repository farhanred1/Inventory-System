package application;



import java.sql.Date;
import java.time.LocalDate;


public class Perishable extends Product {
	Date expdt;
	public Perishable(String id, String name, String description, String type, String bcode, int cost, int sale, int qty, int reord,Date expdt) {
		super(id, name, description, type, bcode, cost, sale, qty, reord);
		this.expdt = expdt;
	}

	
	public Date getExpdt() {
		return expdt;
	}



	public void setExpdt(Date expdt) {
		this.expdt = expdt;
	}



	public int checkExperation() throws Exception {
		LocalDate dateExp= Date.valueOf(expdt.toString()).toLocalDate();
		LocalDate dateCurrent = LocalDate.now();
	      //System.out.println(date.toEpochDay());
		long date1 = dateExp.toEpochDay();
		long date2 = dateCurrent.toEpochDay();
		int  days  = (int) Math.abs(date1 - date2);
		
		return days;
	}

}
