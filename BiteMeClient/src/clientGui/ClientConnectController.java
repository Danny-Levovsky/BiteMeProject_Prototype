package clientGui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientConnectController {

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnExit;

    @FXML
    private TextField txtId;

    
    public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/clientGui/ClientConnect.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client Connect");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
    
    
    @FXML
    void getExitBtn(ActionEvent event) throws Exception{
    	System.exit(0);
    }
    
    @FXML
    void getBtnConnect(ActionEvent event) throws Exception{
    	// TO DO: get data for server connection
    	
    	FXMLLoader loader = new FXMLLoader();
    	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/clientGui/ClientMainMenu.fxml").openStream());
	
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Client Main Menu");
		primaryStage.setScene(scene);		
		primaryStage.show();
    }

}