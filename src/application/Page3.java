package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class Page3 implements Initializable{
	int ID;
	
	
    @FXML
    private Button invAdd;

    @FXML
    private TextField invClient;

    @FXML
    private TextField invPdId;

    @FXML
    private TextField invQty;
    
    @FXML
    void onInvAdd(ActionEvent event) {
    	try {
    		ObservableList<Invoice>invoice = addInvoice();
    		for (int i =0 ; i<invoice.size();i++) {
				System.out.println(invoice.get(i).getPname().toString()); //obList with just names
			}
    		invTable.setItems(invoice);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

    }

    @FXML
    private TableView<Invoice> invTable;

    @FXML
    private TableColumn<Invoice, String> nameCol;

    @FXML
    private TableColumn<Invoice, Double> priceCol;

    @FXML
    private TableColumn<Invoice, Integer> qtyCol;

    @FXML
    private TableColumn<Invoice, Double> ttlCol;
    
    @FXML
    private TableColumn<Invoice, String> idCol;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//nameCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("pname"));
		//idCol.setCellValueFactory(new PropertyValueFactory<Invoice, String>("pid"));
		//priceCol.setCellValueFactory(new PropertyValueFactory<Invoice, Double>("price"));
		//qtyCol.setCellValueFactory(new PropertyValueFactory<Invoice, Integer>("quantity"));
		//ttlCol.setCellValueFactory(new PropertyValueFactory<Invoice, Double>("total"));
		
		/*try {
			ObservableList<Invoice>invoice = addInvoice();
			System.out.println(invoice);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		invTable.setItems(invoice);*/
		
		
	}
	
	public ObservableList<Invoice> addInvoice() throws ClassNotFoundException, SQLException {
				String cName = invClient.getText().trim();
				String pdId = invPdId.getText().trim();
				String qty = invQty.getText().trim();
				
				
		
		 try{
		    	Class.forName("com.mysql.cj.jdbc.Driver");
			       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
			       System.out.println("Connection successful for invoice table");
			       
					
				String sql = "SELECT * from products WHERE pdID = ?";
			       PreparedStatement stmt = conn.prepareStatement(sql);
			       stmt.setString(1, pdId);
			       ResultSet rsSt = stmt.executeQuery();
			       ObservableList<Invoice>ivlist=FXCollections.observableArrayList();
			       while (rsSt.next()) {
			    	   Invoice iv = new Invoice();
			    	   iv.setPid(rsSt.getString("pdID"));
			    	   iv.setPname(rsSt.getString("name"));
			    	   System.out.println(rsSt.getString("name"));
			    	   iv.setPrice(rsSt.getDouble("sale"));
			    	   System.out.println(rsSt.getDouble("sale"));
			    	   iv.setQuantity(Integer.parseInt(invQty.getText().trim()));
			    	   System.out.println(Integer.parseInt(invQty.getText().trim()));
			    	   iv.setTotal((rsSt.getInt("sale")*Integer.parseInt(invQty.getText().trim()))*0.0);
			    	   System.out.println((rsSt.getInt("sale")*Integer.parseInt(invQty.getText().trim()))*1.0);
			    	   ivlist.add(iv);
			    	   //invoice.add(iv);
			    	   //invTable.getItems().add(iv);
			    	   
			    	   
			    	   GeneratePdf gp = new GeneratePdf();
					   gp.generate(cName, iv);
			    	   System.out.println("new invoice added");
			       }
			       
	            
		        conn.close();
			    System.out.println("connection closed"); 
			    
		        return ivlist;
		    }catch(SQLException e){
		        System.out.println("Error occured while fetching the reocrds from DB"+e);
		        e.printStackTrace();
		        throw e;
		    }
		
		 
	}

}

/*//should be in on click create button
			       PreparedStatement stmt0 = conn.prepareStatement("select max(id) from allproducts");
					 ResultSet rs = stmt0.executeQuery();
					 
					 if (rs.next()) {
						 ID = rs.getInt("max(id)")+1;
						 System.out.println(ID); 
					 }
					rs.close();	*/
