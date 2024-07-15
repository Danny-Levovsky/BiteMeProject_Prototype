package clientGui;

import client.BiteMeClient;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ClientMainMenuController {

    @FXML
    private Button btnDisplay = null;

    @FXML
    private Button btnExit = null;

    @FXML
    private Button btnUpdate = null;

    @FXML
    private TextField iptxt;
    
    
    public void update(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGui/ClientUpdate.fxml").openStream());
	
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Update Tool");
		primaryStage.setScene(scene);		
		primaryStage.show();
    }
    
    public void display(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGui/ClientDisplay.fxml").openStream());
		
		// Get the controller for ClientDisplay.fxml
        ClientDisplayController displayController = loader.getController();

        // Create the client and set the display controller
        BiteMeClient client = new BiteMeClient("localhost", 5555);
        client.setDisplayController(displayController);
        client.requestOrderDisplay();
	
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Display Tool");
		primaryStage.setScene(scene);		
		primaryStage.show();
    }
    
    
    
    public void getExitBtn(ActionEvent event) throws Exception {
    	System.exit(0);
    }
    
    

}
