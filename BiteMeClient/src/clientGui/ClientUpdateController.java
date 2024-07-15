package clientGui;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;

import client.BiteMeClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ClientUpdateController {

    @FXML
    private Label lblOrderNumber;

    @FXML
    private Label lblTotalPrice;
    
    @FXML
    private Label lblOrderAddress;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtOrderNumber;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtOrderAddress;
    
    private BiteMeClient client;

    @FXML
    void getBackBtn(ActionEvent event) {
    	try {
  	      // Load the FXML for the previous screen
  	      FXMLLoader loader = new FXMLLoader();
  	      loader.setLocation(getClass().getResource("/clientGui/ClientMainMenu.fxml")); // Update the path to your FXML
  	      Parent previousScreen = loader.load();
  	      // Get the current stage from the event source
  	      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
  	
  	      // Set the scene for the stage with the root of the previous screen
  	      Scene scene = new Scene(previousScreen);
  	      stage.setScene(scene);
  	
  	      // Optionally, set the title for the stage
  	      stage.setTitle("Client Main Menu");
  	
  	      // Show the updated stage
  	      stage.show();
  	  } catch (Exception e) {
  	      e.printStackTrace(); // Print the stack trace in case of an exception
  	  }
    }
    @FXML
    void updateOrderBtn(ActionEvent event) throws IOException {
        String orderNumber = txtOrderNumber.getText();
        String totalPrice = txtTotalPrice.getText();
        String orderAddress = txtOrderAddress.getText();

        if (!orderNumber.isEmpty() && !totalPrice.isEmpty() && !orderAddress.isEmpty()) {
            ArrayList<String> updateDetails = new ArrayList<>();
            updateDetails.add(orderNumber);
            updateDetails.add(totalPrice);
            updateDetails.add(orderAddress);

            // Assuming localhost and port 5555 for connection
            client = new BiteMeClient("localhost", 5555);
            client.requestUpdateOrder(updateDetails);
        } else {
            System.out.println("Please fill in all fields.");
            // You can display an error message to the user in the GUI if needed
        }
    }

}