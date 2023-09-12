package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.time.ZoneId;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddPageController implements Initializable {
	String imagePath;

    @FXML
    private TextField bcodeTxt, nameTxt, costTxt, qtyTxt, rordTxt, saleTxt, colourTxt;

    @FXML
    private AnchorPane clothingView, perishableView;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private TextArea descTxt;

    @FXML
    private Label errTxt;

    @FXML
    private ImageView pdImg;
    
    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<String> typeTxt;
    ObservableList<String>list=FXCollections.observableArrayList("Perishable","Clothing","Applience");

    @FXML
    void onSaveClick(ActionEvent event) {
    	checkData();
    	try {
			createProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Stage window2 = (Stage)saveBtn.getScene().getWindow();
    	window2.fireEvent(new WindowEvent(window2, WindowEvent.WINDOW_HIDDEN));
    	window2.close();
    }

    @FXML
    void onUploadClick(ActionEvent event) {

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg"),
    											new FileChooser.ExtensionFilter("Image Files","*.png"),
    											new FileChooser.ExtensionFilter("Image Files","*.jpeg"));
    	
    	File file = fileChooser.showOpenDialog(null);
    
    	if(file != null) {
    		pdImg.setImage(new Image(file.toURI().toString()));
    		imagePath = file.getAbsolutePath();
    		
    	}else {
    		imagePath = "nil";
    		System.out.println(imagePath);
    	}
    	
    }

    public void initialize(URL location,ResourceBundle resources){
        // TODO Auto-generated method stub
    	
    	clothingView.setVisible(false);
    	perishableView.setVisible(false);
        typeTxt.setItems(list);
        typeTxt.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        	   System.out.println(newValue);
        	   if(newValue.equals("Perishable")) {
        		   clothingView.setVisible(false);
        		   perishableView.setVisible(true);
        		   
        	   }else if(newValue.equals("Clothing")) {
        		   perishableView.setVisible(false);
        		   clothingView.setVisible(true);
        	   }
        	});
    }
    
    public void checkData() {
    	errTxt.setText("");
       	String name = nameTxt.getText().toString().trim();
    	String desc = descTxt.getText().toString().trim();
        String barNo = bcodeTxt.getText().toString().trim();
        String cost = costTxt.getText().toString().trim();
        String sale = saleTxt.getText().toString().trim();
        String qty = qtyTxt.getText().toString().trim();
        String rord = rordTxt.getText().toString().trim();
        String colour = colourTxt.getText().toString().trim();
        
        if(imagePath.equals(null)) {
        	errTxt.setText("Upload an image");
        }else if(name.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(desc.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(barNo.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(cost.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(sale.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(qty.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(rord.equals("")) {
        	errTxt.setText("Fill in all the fields");
        }else if(isNumeric(barNo)== false) {
        	errTxt.setText("Barcode No requires numeric value");
        	
        }else if(isNumeric(cost)== false) {
        	errTxt.setText("Cost requires numeric value");
        	
        }else if(isNumeric(sale)== false) {
        	errTxt.setText("Sale requires numeric value");
        	
        }else if(isNumeric(qty)== false) {
        	errTxt.setText("Quantity requires numeric value");
        	
        }else if(isNumeric(rord)== false) {
        	errTxt.setText("Reorder level requires numeric value");
        	
        }else if (typeTxt.getSelectionModel().isEmpty()){
        	errTxt.setText("Select the product type");
        }else if (typeTxt.getValue().equals("Perishable")) {
        	 if (expDatePicker.getValue() == null) {
        		 errTxt.setText("Date field is empty");
        	 }
        }else if(typeTxt.getValue().equals("Clothing")) {
        	if(colour.equals("")) {
        		errTxt.setText("Fill in all the fields");
        	}
        }else {
        	errTxt.setText("");
        }
        
    }
    
    public void createProduct() throws SQLException{
    	String name = nameTxt.getText().toString().trim();
        String desc = descTxt.getText().toString().trim();
        String barNo = bcodeTxt.getText().toString().trim();
        //String exp = expTxt.getText().toString().trim();
        String cost = costTxt.getText().toString().trim();
        String sale = saleTxt.getText().toString().trim();
        String qty = qtyTxt.getText().toString().trim();
        String rord = rordTxt.getText().toString().trim();
        String type = typeTxt.getValue();
        
        IDgenerator id = new IDgenerator();
        
        Product product = new Product(id.generateID(), name, desc, type, barNo,Integer.valueOf(sale),
        		Integer.valueOf(cost),Integer.valueOf(qty),Integer.valueOf(rord));
       
        
        
        try { 
	        Class.forName("com.mysql.cj.jdbc.Driver");
		       Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost/inventory","root","");
		       System.out.println("Connection successful");
	        
	       
	        PreparedStatement stmt = conn.prepareStatement(
	        		"insert into products (pdID, name, description, type, bcode, cost, sale, qty, rord)"
	        		+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
	        		Statement.RETURN_GENERATED_KEYS);
	        
	        
	        stmt.setString(1,product.getId());
	        stmt.setString(2,product.getName());
	        stmt.setString(3,product.getDescription());
	        stmt.setString(4,product.getType());
	        stmt.setString(5,product.getBcode());
	        stmt.setInt(6,product.getCost());
	        stmt.setInt(7,product.getSale());
	        stmt.setInt(8,product.getQty());
	        stmt.setInt(9,product.getReord());
	        //stmt.setBlob(10,is);
	        stmt.execute();
	        
	        PreparedStatement stmt1 = conn.prepareStatement(
	        		"insert into images (id, image)"
	        		+ " values (?, ?)",
	        		Statement.RETURN_GENERATED_KEYS);
	        InputStream is = new FileInputStream(new File(imagePath));
	        stmt1.setString(1,product.getId());
	        stmt1.setBlob(2,is);
	        stmt1.execute();
	        if(product.getType().equals("Perishable")) {
	        	java.util.Date date = 
	        		    java.util.Date.from(expDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
	        		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	        		
        	Perishable perishable =  new Perishable(product.getId(), name, desc, type, barNo,Integer.valueOf(sale),
            		Integer.valueOf(cost),Integer.valueOf(qty),Integer.valueOf(rord),sqlDate);
        	System.out.println("perishable obj created");
        	System.out.println(sqlDate);
        	PreparedStatement stmt2 = conn.prepareStatement(
	        		"insert into perishables (id, name, description, type, bcode, cost, sale, qty, rord, expdate)"
	        		+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
	        		Statement.RETURN_GENERATED_KEYS);
        	stmt2.setString(1,perishable.getId());
 	        stmt2.setString(2,perishable.getName());
 	        stmt2.setString(3,perishable.getDescription());
 	        stmt2.setString(4,perishable.getType());
 	        stmt2.setString(5,perishable.getBcode());
 	        stmt2.setInt(6,perishable.getCost());
 	        stmt2.setInt(7,perishable.getSale());
 	        stmt2.setInt(8,perishable.getQty());
 	        stmt2.setInt(9,perishable.getReord());
 	        stmt2.setDate(10,perishable.getExpdt());
 	        stmt2.execute();
	        }else if (product.getType().equals("Clothing")) {
	        	String colour = colourTxt.getText().toString().trim();
	        	Clothing clothing = new Clothing(colour,product.getId());
	        	PreparedStatement stmt3 = conn.prepareStatement(
		        		"insert into clothings (colour, id)"
		        		+ " values (?, ?)",
		        		Statement.RETURN_GENERATED_KEYS);
	        	 stmt3.setString(1,clothing.getColour());
	        	 stmt3.setString(2,clothing.getId());
	        	 stmt3.execute();
	        	
	        }
		    conn.close();
	       }
	       catch (Exception e) {
		       System.err.println(e);
	       }
	        	        
    	
    }
    
    public static boolean isNumeric(String string) {
        // Checks if the provided string
        // is a numeric by applying a regular
        // expression on it.
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, string);
    }

}
