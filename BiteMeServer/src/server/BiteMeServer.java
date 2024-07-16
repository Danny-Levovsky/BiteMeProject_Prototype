package server;

import java.io.*;
import ocsf.server.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class BiteMeServer extends AbstractServer 
{
  //private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/order?serverTimezone=IST";
  //private static final String DB_USER = "root";
  //private static final String DB_PASSWORD = "Aa123456";
  private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";  // URL without database name

  private Connection connection;
  private BiConsumer<String, String> clientConnectedCallback;


  final public static int DEFAULT_PORT = 5555;
  
  public BiteMeServer(int port, String dbName, String dbUser, String dbPassword) {
      super(port);

      String fullDBUrl = DB_URL + dbName + "?serverTimezone=IST";

      try {
          connection = DriverManager.getConnection(fullDBUrl, dbUser, dbPassword);
          System.out.println("Connected to the database.");
      } catch (SQLException e) {
          System.out.println("Database connection failed: " + e.getMessage());
      }
  }

  @Override
  protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
	    if (msg instanceof ArrayList) {
	      ArrayList<String> details = (ArrayList<String>) msg;
	      if (details.size() == 3) {
	          updateOrder(details,client);
	        } 
	      else {
	          System.out.println("Insufficient details received from client.");
	      }
	    } else if (msg.equals("fetchOrders")) {
	      fetchAndSendOrdersToClient(client);
	    } else if (msg.equals("showConnectivityDetails")) {
	      sendConnectivityDetailsToClient(client);
	    } else {
	      System.out.println("Message received: " + msg);
	    }
	  }

  @Override
  protected synchronized void clientConnected(ConnectionToClient client) {
      try {
          String clientIpAddress = client.getInetAddress().getHostAddress();
          String clientHostName = client.getInetAddress().getHostName();
          if (clientConnectedCallback != null) {
              clientConnectedCallback.accept(clientHostName, clientIpAddress);
          }
      } catch (Exception e) {
          System.out.println("Error getting client details: " + e.getMessage());
      }
  }
  
  public void setClientConnectedCallback(BiConsumer<String, String> callback) {
      this.clientConnectedCallback = callback;
  }
  
  private void fetchAndSendOrdersToClient(ConnectionToClient client) {
	    String sql = "SELECT * FROM orders";
	    ArrayList<String> orders = new ArrayList<>();

	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {

	      while (resultSet.next()) {
	        String restaurant = resultSet.getString("Restaurant");
	        int orderNumber = resultSet.getInt("Order_number");
	        int totalPrice = resultSet.getInt("Total_price");
	        int orderListNumber = resultSet.getInt("Order_list_number");
	        String orderAddress = resultSet.getString("Order_address");

	        String order = String.format("Restaurant: %s, Order Number: %d, Total Price: %d, Order List Number: %d, Order Address: %s",
	                                     restaurant, orderNumber, totalPrice, orderListNumber, orderAddress);
	        orders.add(order);
	      }

	      client.sendToClient(orders);
	    } catch (SQLException | IOException e) {
	      System.out.println("Error fetching or sending orders: " + e.getMessage());
	    }
	  }
  
  private void sendConnectivityDetailsToClient(ConnectionToClient client) {
	    try {
	      String clientInfo = "IP Address: " + client.getInetAddress().getHostAddress() +
	                          ", Host Name: " + client.getInetAddress().getHostName();
	      client.sendToClient(clientInfo);
	    } catch (IOException e) {
	      System.out.println("Error sending connectivity details: " + e.getMessage());
	    }
	  }
  
  private void updateOrder(ArrayList<String> details,ConnectionToClient client) {
      String orderNumber = details.get(0);
      String totalPrice = details.get(1);
      String orderAddress = details.get(2);

      String sql = "UPDATE orders SET Total_price = ?, Order_address = ? WHERE Order_number = ?";

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setInt(1, Integer.parseInt(totalPrice));
          statement.setString(2, orderAddress);
          statement.setInt(3, Integer.parseInt(orderNumber));

          int rowsUpdated = statement.executeUpdate();
          if (rowsUpdated > 0) {
              System.out.println("Order updated successfully!");
              sendMessageToClient(client, "Order updated successfully!");
          } else {
              System.out.println("Order not found");
              sendMessageToClient(client, "Order not found");
          }
      } catch (SQLException e) {
          System.out.println("Error updating order: " + e.getMessage());
      }
  }
  
  private void sendMessageToClient(ConnectionToClient client, String message) {
	    try {
	      client.sendToClient(message);
	    } catch (IOException e) {
	      System.out.println("Error sending message to client: " + e.getMessage());
	    }
	  }
  
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
 
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  /*
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    BiteMeServer sv = new BiteMeServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }*/
}
//End of EchoServer class
