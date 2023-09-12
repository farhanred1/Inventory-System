package application;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.Initializable;

public class page2 implements Initializable{
	
	String idX;

    @FXML
    private TextField nameedit, bcodeedit, costedit, descedit, qtyedit, rordedit, saleedit, typeedit, searchBox;

    @FXML
    private ListView<String> pdList;
 
    @FXML
    private Label idedit;

    @FXML
    private ImageView imgedit;

    @FXML
    private Button btnAdd;
    
    

    
    @FXML
    void onListClicked(MouseEvent event) throws ClassNotFoundException,SQLException{
    	System.out.println("mouse clicked");
	    try{
	    	Class.forName("com.mysql.cj.jdbc.Driver");
		       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
		       System.out.println("Connection successful for list item");
	        
	       
	        PreparedStatement stmt = conn.prepareStatement("select * from products where name = ?");
	        stmt.setString(1, (String)pdList.getSelectionModel().getSelectedItem());
	        
	        ResultSet reSet = stmt.executeQuery();
	        
	    	while(reSet.next()){
		   		
		   		nameedit.setText(reSet.getString("name"));
		   		nameedit.setEditable(false);
		   		descedit.setText(reSet.getString("description"));
		   		descedit.setEditable(false);
		   		typeedit.setText(reSet.getString("type"));
		   		typeedit.setEditable(false);
		   		bcodeedit.setText(reSet.getString("bcode"));
		   		bcodeedit.setEditable(false);
		   		costedit.setText(reSet.getString("cost"));
		   		costedit.setEditable(false);
		   		saleedit.setText(reSet.getString("sale"));
		   		saleedit.setEditable(false);
		   		qtyedit.setText(reSet.getString("qty"));
		   		qtyedit.setEditable(false);
		   		rordedit.setText(reSet.getString("rord"));
		   		rordedit.setEditable(false);
		   		idedit.setText("# "+reSet.getString("pdID"));
		   		idX = reSet.getString("pdID");
		   				   		
	    	}
	    	String SQL = "SELECT image from images WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, idX);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InputStream imageFile = rs.getBinaryStream(1);
                Image image = new Image(imageFile);
                imgedit.setImage(image);
                //ImageView iv = new ImageView(image);
            }
	        conn.close();
		    System.out.println("connection closed");     
	        
	    }catch(SQLException e){
	        System.out.println("Error occured while fetching the reocrds from DB"+e);
	        e.printStackTrace();
	        throw e;
	    }
    }
    
    

    @FXML
    void onAddClicked(ActionEvent event) {
    	startAddWindow();
    }
    
    public void startAddWindow() {
    	Stage Window2 = new Stage();
    	try {
    	AnchorPane root =
    	(AnchorPane)FXMLLoader.load(getClass().getResource("AddPage.fxml"));
    	Scene scene2 = new Scene(root,600,500);
    	scene2.getStylesheets().add(getClass().getResource("application.css"
    	).toExternalForm());
    	
    	Window2.initModality(Modality.APPLICATION_MODAL);
    	Window2.setScene(scene2);
    	Window2.show();
    	} catch(Exception e) {
    	e.printStackTrace();
    	}
    	
    	
    	
    	Window2.setOnHidden(new EventHandler<WindowEvent>() {
    	    public void handle(WindowEvent we) {
    	    	try {
    				ObservableList<Product> pdlistly = getAllRecords();
    				ObservableList<String> pNAMES = FXCollections.observableArrayList();;
    				for (int i =0 ; i<pdlistly.size();i++) {
    					pNAMES.add(pdlistly.get(i).getName().toString()); //obList with just names
    				}
    				
    				pdList.setItems(pNAMES); //SET list values 
    				
    				
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	    }
    	});
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		

		try {
			ObservableList<Product> pdlistly = getAllRecords();
			ObservableList<String> pNAMES = FXCollections.observableArrayList();;
			for (int i =0 ; i<pdlistly.size();i++) {
				pNAMES.add(pdlistly.get(i).getName().toString()); //obList with just names
			}
			
			pdList.setItems(pNAMES); //SET list values 
			
			
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
		   		pd.setBcode(reSet.getString("cost"));
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
	
    @FXML
    void onSearch(KeyEvent event) {
		 String s = searchBox.getText().trim();
		 	
		 	try {
				ObservableList<Product> pdlistly = getSearchRecords(s);
				ObservableList<String> pNAMES = FXCollections.observableArrayList();;
				for (int i =0 ; i<pdlistly.size();i++) {
					pNAMES.add(pdlistly.get(i).getName().toString()); //obList with just names
				}
				
				pdList.setItems(pNAMES); //SET list values 
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
	    }
	 
	 private ObservableList<Product> getSearchRecords(String s) throws ClassNotFoundException,SQLException {
			
			String sql="SELECT * FROM products WHERE name LIKE '%?%' AND description LIKE '%?%'";
		    try{
		    	Class.forName("com.mysql.cj.jdbc.Driver");
			       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
			       System.out.println("Connection successful");
		        
		       
		        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE name LIKE ? OR description LIKE ?");
		        stmt.setString(1,"%"+s+"%");
		        stmt.setString(2,"%"+s+"%");
		        
		        ResultSet reSet = stmt.executeQuery();
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


}
