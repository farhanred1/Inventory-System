package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class SampleController implements Initializable {

    @FXML
    private StackPane contentArea;

    @FXML
    void page1(ActionEvent event)throws IOException {
    	Parent fxml = FXMLLoader.load(getClass().getResource("page1.fxml"));
    	contentArea.getChildren().removeAll();
    	contentArea.getChildren().setAll(fxml);
    }

    @FXML
    void page2(ActionEvent event)throws IOException {
    	Parent fxml = FXMLLoader.load(getClass().getResource("page2.fxml"));
    	contentArea.getChildren().removeAll();
    	contentArea.getChildren().setAll(fxml);
    }

    @FXML
    void page3(ActionEvent event)throws IOException {
    	Parent fxml = FXMLLoader.load(getClass().getResource("Page3.fxml"));
    	contentArea.getChildren().removeAll();
    	contentArea.getChildren().setAll(fxml);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Parent fxml;
		try {
			fxml = FXMLLoader.load(getClass().getResource("page1.fxml"));
			contentArea.getChildren().removeAll();
	    	contentArea.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		
	}

}
