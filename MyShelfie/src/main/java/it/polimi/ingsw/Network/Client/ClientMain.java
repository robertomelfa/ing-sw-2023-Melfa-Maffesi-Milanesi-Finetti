package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ClientMain extends Application implements Serializable {

    public static void main(String[] args) throws Exception {

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        System.out.println("[CLIENT] is running");
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Do you want to use UI?");
        input = scanner.next();
        if (server.isLocked()) {
            System.out.println("Another client is connecting");
        }
        server.block();
        switch (input) {
            case "yes":
                launch(args);
                break;

            default:
                server.setFirstPlayer();
                System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Input not received within 15 seconds. Disconnecting from server ..-");
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
                timer.schedule(task, 15000);
                if (scanner.hasNext()) {
                    input = scanner.next();
                    switch (input.toUpperCase()) {
                        case "A":
                            timer.cancel();
                            System.out.println("Starting Socket");
                            Client_Socket clientS = new Client_Socket();
                            clientS.start(server);
                            break;
                        case "B":
                            try {
                                timer.cancel();

                                GameClientInterface clientR = new Client();
                                clientR.connection(server, clientR, server.getController());
                                server.release();

                            } catch (Exception e) {
                                System.out.println("[System] Server failed: " + e);
                            }
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setScene(scene);
        stage.setTitle("MyShelfie");
        stage.show();
        }
}