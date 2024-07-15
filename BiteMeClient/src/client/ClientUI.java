package client;

import clientGui.ClientMainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    private BiteMeClient biteMeClient;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        biteMeClient = new BiteMeClient(HOST, PORT);

        ClientMainMenuController cFrame = new ClientMainMenuController();
        cFrame.start(primaryStage);
    }
}
