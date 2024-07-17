package clientGui;

import client.BiteMeClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientConnectController {

	private String server_connection_data;
	private BiteMeClient biteMeClient;
    @FXML
    private Button btnConnect;

    @FXML
    private Label conStatus;
    
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
    	server_connection_data = txtId.getText();
    	try{
    		biteMeClient = new BiteMeClient(server_connection_data, 5555);
    		FXMLLoader loader = new FXMLLoader();
        	((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		Stage primaryStage = new Stage();
    		Pane root = loader.load(getClass().getResource("/clientGui/ClientMainMenu.fxml").openStream());
    	
    		Scene scene = new Scene(root);			
    		primaryStage.setTitle("Client Main Menu");
    		primaryStage.setScene(scene);		
    		primaryStage.show();
    	}catch(Exception e){
    		conStatus.setText("Couldn't Connect to Server");
    	}
    }
    
    @FXML
    public String getIP() throws Exception{
    	{
    		return server_connection_data;
    	}
    }
    

}