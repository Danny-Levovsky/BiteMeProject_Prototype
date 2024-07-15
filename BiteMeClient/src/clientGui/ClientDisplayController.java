package clientGui;

import java.io.IOException;
import java.util.ArrayList;

import client.BiteMeClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClientDisplayController {

    @FXML
    private Button btnBack;
    
    @FXML
    private Button Ordersbtn;
    
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> restaurantColumn;
    @FXML
    private TableColumn<Order, Integer> orderNumberColumn;
    @FXML
    private TableColumn<Order, Integer> totalPriceColumn;
    @FXML
    private TableColumn<Order, Integer> orderListNumberColumn;
    @FXML
    private TableColumn<Order, String> orderAddressColumn;
    
    private ObservableList<Order> ordersData = FXCollections.observableArrayList();
    private BiteMeClient client;
    
    @FXML
    public void initialize() {
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderListNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderListNumber"));
        orderAddressColumn.setCellValueFactory(new PropertyValueFactory<>("orderAddress"));
        
        ordersTable.setItems(ordersData);
    }
    
    public void setOrdersData(ArrayList<Order> orders) {
        ordersData.setAll(orders);
    }
        
    @FXML
    void getBackBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clientGui/ClientMainMenu.fxml")); // Update the path to your FXML
            Parent previousScreen = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(previousScreen);
            stage.setScene(scene);
            stage.setTitle("Client Main Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
