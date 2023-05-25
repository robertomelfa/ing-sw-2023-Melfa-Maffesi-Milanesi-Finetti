package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ClientMain extends Application implements Serializable {

    public static void main(String[] args) throws Exception {

        System.out.printf(" ██████   ██████             █████████  █████               ████     ██████   ███          \n" +
                          "░░██████ ██████             ███░░░░░███░░███               ░░███    ███░░███ ░░░           \n" +
                          " ░███░█████░███  █████ ████░███    ░░░  ░███████    ██████  ░███   ░███ ░░░  ████   ██████ \n" +
                          " ░███░░███ ░███ ░░███ ░███ ░░█████████  ░███░░███  ███░░███ ░███  ███████   ░░███  ███░░███\n" +
                          " ░███ ░░░  ░███  ░███ ░███  ░░░░░░░░███ ░███ ░███ ░███████  ░███ ░░░███░     ░███ ░███████ \n" +
                          " ░███      ░███  ░███ ░███  ███    ░███ ░███ ░███ ░███░░░   ░███   ░███      ░███ ░███░░░  \n" +
                          " █████     █████ ░░███████ ░░█████████  ████ █████░░██████  █████  █████     █████░░██████ \n" +
                          "░░░░░     ░░░░░   ░░░░░███  ░░░░░░░░░  ░░░░ ░░░░░  ░░░░░░  ░░░░░  ░░░░░     ░░░░░  ░░░░░░  \n" +
                          "                  ███ ░███                                                                 \n" +
                          "                 ░░██████                                                                  \n" +
                          "                  ░░░░░░                                                                   \n");


        Boolean selection = false;
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        System.out.println("[CLIENT] is running");
        Scanner scanner = new Scanner(System.in);
        String input;
        //input = scanner.next();
        if (server.isLocked()) {
            System.out.println("Another client is connecting");
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Input not received within 30 seconds. Disconnecting from server ..-");
                try {
                    server.release();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 30000);
        while (!selection){
            System.out.println("Do you want to use UI?");
            input = scanner.next();
            switch (input) {
                case "yes":
                    timer.cancel();
                    selection = true;
                    launch(args);
                    break;
                case "no":
                    selection = true;
                    server.setFirstPlayer();
                    do{
                        System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");

                        if (scanner.hasNext()) {
                            input = scanner.next();
                            switch (input.toUpperCase()) {
                                case "A":
                                    server.block();
                                    timer.cancel();
                                    System.out.println("Starting Socket");
                                    Client_Socket clientS = new Client_Socket();
                                    clientS.start(server);
                                    break;
                                case "B":
                                    server.block();
                                    try {
                                        timer.cancel();

                                        GameClientInterface clientR = new Client();
                                        clientR.connection(server, clientR, server.getController());
                                        server.release();
                                    } catch (Exception e) {
                                    }
                                    break;

                            }
                        }
                    }
                    while (!input.toUpperCase().equals("A") && !input.toUpperCase().equals("B"));

            }
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.setScene(scene);
        stage.setTitle("MyShelfie");
        stage.getIcons().add(icon);
        stage.show();
    }
}