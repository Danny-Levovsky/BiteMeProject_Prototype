package client;
import javafx.application.Application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import clientGui.ClientDisplayController;
import clientGui.ClientMainMenuController;
import clientGui.ClientUpdateController;
import clientGui.Order;
import ocsf.client.AbstractClient;

//biteMeclient is responsible for communication with the server
public class BiteMeClient extends AbstractClient {
	private ClientDisplayController displayController;
	private ClientUpdateController updateController;

  public BiteMeClient(String host, int port) throws IOException {
    super(host, port);
    openConnection();
  }
  
  // get an array list from the server, array list contains list of orders
  // method is used to get the array list, parse it, arrange it to new array list and send to display controller
  @Override
  protected void handleMessageFromServer(Object msg) {
      if (msg instanceof ArrayList) {
    	  //get array list from server
          ArrayList<String> orders = (ArrayList<String>) msg;
          //set new array list
          ArrayList<Order> orderList = new ArrayList<>();
          //parse the data
          for (String orderString : orders) {
              String[] orderDetails = orderString.split(", ");
              String restaurant = orderDetails[0].split(": ")[1];
              int orderNumber = Integer.parseInt(orderDetails[1].split(": ")[1]);
              int totalPrice = Integer.parseInt(orderDetails[2].split(": ")[1]);
              int orderListNumber = Integer.parseInt(orderDetails[3].split(": ")[1]);
              String orderAddress = orderDetails[4].split(": ")[1];
          //add to array list: order list
              Order order = new Order(restaurant, orderNumber, totalPrice, orderListNumber, orderAddress);
              orderList.add(order);
          }

          // Get the controller and update it with the order list
          if (displayController != null) {
        	  displayController.setOrdersData(orderList);
          } else {
              System.out.println("Controller not available to send orders.");
          }
      } else {
          System.out.println("Message from server: " + msg);
          updateController.getmessage(msg);
      }
  }
  
  //get an instance of display controller - used to deliver data
  public void setDisplayController(ClientDisplayController displayController) {
      this.displayController = displayController;
  }

  //used in client display controller - get data from server
  public void requestOrderDisplay() {
	    try {
	      sendToServer("fetchOrders");
	    } catch (IOException e) {
	      System.out.println("Error sending fetch request to server: " + e.getMessage());
	    }
	  }
  
  // to be changed later
  public void requestConnectivityDetails() {
	    try {
	      sendToServer("showConnectivityDetails");
	    } catch (IOException e) {
	      System.out.println("Error sending connectivity request to server: " + e.getMessage());
	    }
	  }
  
  //used in client update controller - send server new data to update in database
  public void requestUpdateOrder(ArrayList<String> updateDetails) {
	    try {
	      sendToServer(updateDetails);
	    } catch (IOException e) {
	      System.out.println("Error sending update request to server: " + e.getMessage());
	    }
  }
}
	    
  	
