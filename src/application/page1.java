package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class page1 implements Initializable{

    @FXML
    private ListView<String> expList;

    @FXML
    private ListView<String> stockList;
    

		@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			ObservableList<Product> pdlistly = getAllRecords();
			ObservableList<Product> lowStock = FXCollections.observableArrayList();;
			for (int j =0 ; j<pdlistly.size();j++) {
				if(pdlistly.get(j).checkStock()) {
					lowStock.add(pdlistly.get(j));
				
				}
			}
			ObservableList<String> pNAMES = FXCollections.observableArrayList();;
			for (int i =0 ; i<lowStock.size();i++) {
				pNAMES.add(lowStock.get(i).getName().toString()); //obList with just names
			}
			stockList.setItems(pNAMES); //SET list values 
			
			
			
			ObservableList<Perishable> prlistly = getAllRecords2();
			ObservableList<Perishable> expsoon = FXCollections.observableArrayList();;
			for (int k=0 ; k<prlistly.size();k++) {
				
				
				try {
					int days;
					days = prlistly.get(k).checkExperation();
					if (days < 30) {
						expsoon.add(prlistly.get(k));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			ObservableList<String> prNames = FXCollections.observableArrayList();;
			for (int i =0 ; i<expsoon.size();i++) {
				prNames.add(expsoon.get(i).getName().toString()); //obList with just names
			}
			expList.setItems(prNames);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    
	private ObservableList<Product> getAllRecords() throws ClassNotFoundException,SQLException {
		
		String sql="select * from products";
	    try{
	    	Class.forName("com.mysql.cj.jdbc.Driver");
		       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
		       System.out.println("Connection successful");
	        
	       
	        PreparedStatement stmt = conn.prepareStatement("select * from products");
	        
	        ResultSet reSet = stmt.executeQuery(sql);
	        ObservableList<Product> pdList = getProductObjects(reSet);
	        
	        conn.close();
		    System.out.println("connection closed");
	        return pdList;
	        
	        
	        
	    }catch(SQLException e){
	        System.out.println("Error occured while fetching the reocrds from DB"+e);
	        e.printStackTrace();
	        throw e;
	    }
		
	}

	private ObservableList<Product> getProductObjects(ResultSet reSet) throws ClassNotFoundException,SQLException  {

		try{
		   ObservableList<Product>productlist=FXCollections.observableArrayList();
		   	while(reSet.next()){
		   		Product pd = new Product();
		   		pd.setId(reSet.getString("pdID"));
		   		pd.setName(reSet.getString("name"));
		   		pd.setDescription(reSet.getString("description"));
		   		pd.setType(reSet.getString("type"));
		   		pd.setBcode(reSet.getString("bcode"));
		   		pd.setCost(reSet.getInt("cost"));
		   		pd.setSale(reSet.getInt("sale"));
		   		pd.setQty(reSet.getInt("qty"));
		   		pd.setReord(reSet.getInt("rord"));
		   		productlist.add(pd);
		   	}
		return productlist;
		
		}catch(SQLException e){
		   System.out.println("Error ocuureed whlile fetching the data from DB"+e);
		    e.printStackTrace();
		    throw e;
		}
		
	}


	private ObservableList<Perishable> getAllRecords2() throws ClassNotFoundException,SQLException {
		
		String sql="select * from perishables";
	    try{
	    	Class.forName("com.mysql.cj.jdbc.Driver");
		       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
		       System.out.println("Connection successful");
	        
	       
	        PreparedStatement stmt = conn.prepareStatement("select * from perishables");
	        
	        ResultSet reSet = stmt.executeQuery(sql);
	        ObservableList<Perishable> pdList = getPerishableObjects(reSet);
	        
	        conn.close();
		    System.out.println("connection closed");
	        return pdList;
	        
	        
	        
	    }catch(SQLException e){
	        System.out.println("Error occured while fetching the reocrds from DB"+e);
	        e.printStackTrace();
	        throw e;
	    }
		
	}

	private ObservableList<Perishable> getPerishableObjects(ResultSet reSet) throws ClassNotFoundException,SQLException  {

		try{
		   ObservableList<Perishable>perishlist=FXCollections.observableArrayList();
		   	while(reSet.next()){
		   		
		   		Perishable pd = new Perishable(reSet.getString("id"),reSet.getString("name"),reSet.getString("description"),
		   				reSet.getString("type"),reSet.getString("bcode"),reSet.getInt("cost"),
		   				reSet.getInt("sale"),reSet.getInt("qty"),reSet.getInt("rord"),reSet.getDate("expdate"));
		   		
		   		perishlist.add(pd);
		   		
		   	}
		return perishlist;
		
		}catch(SQLException e){
		   System.out.println("Error ocuureed whlile fetching the data from DB"+e);
		    e.printStackTrace();
		    throw e;
		}
		
	}


}
