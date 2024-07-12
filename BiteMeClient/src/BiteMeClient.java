import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import ocsf.client.AbstractClient;

public class BiteMeClient extends AbstractClient {

  public BiteMeClient(String host, int port) throws IOException {
    super(host, port);
    openConnection();
  }

  @Override
  protected void handleMessageFromServer(Object msg) {
    if (msg instanceof ArrayList) {
      ArrayList<String> orders = (ArrayList<String>) msg;
      int orderCount = 1;
      for (String order : orders) {
        System.out.println("Order " + orderCount + ": " + order);
        orderCount++;
      }
    } else {
      System.out.println("Message from server: " + msg);
    }
  }

  public void sendArrayListToServer(ArrayList<String> list) {
    try {
      sendToServer(list);
    } catch (IOException e) {
      System.out.println("Error sending message to server: " + e.getMessage());
    }
  }

  public void insertData() {
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> details = new ArrayList<>();

    System.out.println("Enter order details:");

    System.out.print("Restaurant Name: ");
    details.add(scanner.nextLine());

    System.out.print("Order Number: ");
    details.add(scanner.nextLine());

    System.out.print("total Price: ");
    details.add(scanner.nextLine());

    System.out.print("Order_list_number: ");
    details.add(scanner.nextLine());

    System.out.print("Order_address: ");
    details.add(scanner.nextLine());
    
    sendArrayListToServer(details);
    System.out.println("Details sent to server.");

  }
  
  public void requestOrderDisplay() {
	    try {
	      sendToServer("fetchOrders");
	    } catch (IOException e) {
	      System.out.println("Error sending fetch request to server: " + e.getMessage());
	    }
	  }
  
  public void requestConnectivityDetails() {
	    try {
	      sendToServer("showConnectivityDetails");
	    } catch (IOException e) {
	      System.out.println("Error sending connectivity request to server: " + e.getMessage());
	    }
	  }
  
  public void requestUpdateOrder() {
	    Scanner scanner = new Scanner(System.in);
	    ArrayList<String> updateDetails = new ArrayList<>();
	    System.out.print("What order you wish to edit: ");

	    System.out.print("Enter Order Number: ");
	    updateDetails.add(scanner.nextLine());

	    System.out.print("Enter Column to Update (Total_price or Order_address): ");
	    updateDetails.add(scanner.nextLine());

	    System.out.print("Enter New Value: ");
	    updateDetails.add(scanner.nextLine());

	    try {
	      sendToServer(updateDetails);
	    } catch (IOException e) {
	      System.out.println("Error sending update request to server: " + e.getMessage());
	    }
	    
  	}

  public static void main(String[] args) {
    String host = "localhost";
    int port = 5555;

    try {
        BiteMeClient client = new BiteMeClient(host, port);
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
          System.out.println("Menu:");
          System.out.println("1. Insert Order");
          System.out.println("2. Display Orders");
          System.out.println("3. Show Connectivity Details");
          System.out.println("4. Update Order");
          System.out.println("5. Exit");
          System.out.print("Enter your choice: ");
          int choice = scanner.nextInt();
          scanner.nextLine();  // Consume newline

          switch (choice) {
            case 1:
              client.insertData();
              break;
            case 2:
              client.requestOrderDisplay();
              break;
            case 3:
              client.requestConnectivityDetails();
              break;
            case 4:
              client.requestUpdateOrder();
              break;
            case 5:
              exit = true;
              break;
            default:
              System.out.println("Invalid choice. Please try again.");
          }
        }

        scanner.close();
      } catch (IOException e) {
        System.out.println("Client error: " + e.getMessage());
      }
    }
  }
