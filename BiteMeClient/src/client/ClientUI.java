package client;

import clientGui.ClientConnectController;
import clientGui.ClientMainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

    private static final String IP = "localhost";
    private static final int PORT = 5555;
    ClientConnectController clientConnectController;
    
    private BiteMeClient biteMeClient;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	clientConnectController = new ClientConnectController();
    	
    	clientConnectController.getIP();
    	clientConnectController.start(primaryStage);
    	
        //biteMeClient = new BiteMeClient(IP, PORT);

    }
}
