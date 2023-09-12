package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IDgenerator {
	
	private  int ID ;
	
	public String generateID() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
			System.out.println("Connection successful");
			 PreparedStatement stmt = conn.prepareStatement("select max(id) from products");
			 ResultSet rs = stmt.executeQuery();
			 
			 if (rs.next()) {
				 ID = rs.getInt("max(id)")+1;
				 System.out.println(ID);
				 
				 
			 }
			rs.close();		 
					 
		}catch(Exception e) {
		       System.err.println(e);
			
		}
		
     
    
    
		
		return String.valueOf(ID);
	}
}
