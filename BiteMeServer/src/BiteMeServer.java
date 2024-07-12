
import java.io.*;
import ocsf.server.*;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BiteMeServer extends AbstractServer 
{
  private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/order?serverTimezone=IST";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "Aa123456";
  private Connection connection;
  final public static int DEFAULT_PORT = 5555;
  
  public BiteMeServer(int port) 
  {
    super(port);
  
  try {
      connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
	          updateOrder(details);
	        } else if (details.size() >= 5) {
	          insertOrderDetails(details);
	        } else {
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

  private void insertOrderDetails(ArrayList<String> details) {
    String sql = "INSERT INTO orders (Restaurant, Order_number, Total_price, Order_list_number, Order_address) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, details.get(0));
      statement.setInt(2, Integer.parseInt(details.get(1)));
      statement.setInt(3, Integer.parseInt(details.get(2)));
      statement.setInt(4, Integer.parseInt(details.get(3)));
      statement.setString(5, details.get(4));

      int rowsInserted = statement.executeUpdate();
      if (rowsInserted > 0) {
        System.out.println("A new order was inserted successfully!");
      }
    } catch (SQLException e) {
      System.out.println("Error inserting order: " + e.getMessage());
    }
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
  
  private void updateOrder(ArrayList<String> details) {
	    String orderNumber = details.get(0);
	    String column = details.get(1);
	    String newValue = details.get(2);

	    if (!column.equals("Total_price") && !column.equals("Order_address")) {
	      System.out.println("Invalid column name provided: " + column);
	      return;
	    }

	    String sql = "UPDATE orders SET " + column + " = ? WHERE Order_number = ?";

	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	      if (column.equals("Total_price")) {
	        statement.setInt(1, Integer.parseInt(newValue));
	      } else {
	        statement.setString(1, newValue);
	      }
	      statement.setInt(2, Integer.parseInt(orderNumber));

	      int rowsUpdated = statement.executeUpdate();
	      if (rowsUpdated > 0) {
	        System.out.println("Order updated successfully!");
	      }
	    } catch (SQLException e) {
	      System.out.println("Error updating order: " + e.getMessage());
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
  }
}
//End of EchoServer class
