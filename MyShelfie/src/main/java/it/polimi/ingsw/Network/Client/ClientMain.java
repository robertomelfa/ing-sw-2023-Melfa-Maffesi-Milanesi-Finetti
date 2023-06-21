package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import it.polimi.ingsw.View.CLIView;
import it.polimi.ingsw.View.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ClientMain extends Application implements Serializable {


    /**
     * main that start the game for clients
     * @param args args main
     * @throws Exception Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.print("""
                 ██████   ██████             █████████  █████               ████     ██████   ███         \s
                ░░██████ ██████             ███░░░░░███░░███               ░░███    ███░░███ ░░░          \s
                 ░███░█████░███  █████ ████░███    ░░░  ░███████    ██████  ░███   ░███ ░░░  ████   ██████\s
                 ░███░░███ ░███ ░░███ ░███ ░░█████████  ░███░░███  ███░░███ ░███  ███████   ░░███  ███░░███\s
                 ░███ ░░░  ░███  ░███ ░███  ░░░░░░░░███ ░███ ░███ ░███████  ░███ ░░░███░     ░███ ░███████\s
                 ░███      ░███  ░███ ░███  ███    ░███ ░███ ░███ ░███░░░   ░███   ░███      ░███ ░███░░░ \s
                 █████     █████ ░░███████ ░░█████████  ████ █████░░██████  █████  █████     █████░░██████\s
                ░░░░░     ░░░░░   ░░░░░███  ░░░░░░░░░  ░░░░ ░░░░░  ░░░░░░  ░░░░░  ░░░░░     ░░░░░  ░░░░░░ \s
                                  ███ ░███                                                                \s
                                 ░░██████                                                                 \s
                                  ░░░░░░                                                                  \s
                """);


        boolean selection = false;
        System.out.println("[CLIENT] is running");
        CLIView view = new CLIView();
        String port = view.requestIP();
        Registry registry = LocateRegistry.getRegistry(port, 1099);
        GameInterface server = (GameInterface) registry.lookup("GameInterface");
        Scanner scanner = new Scanner(System.in);
        String input;
        //input = scanner.next();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Input not received within 30 seconds. Disconnecting from server ..-");
                try {
                    server.release();
                } catch (RemoteException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 30000);
        while (!selection){
            System.out.println("Do you want to use UI? [yes/no]");
            input = scanner.next();
            switch (input) {
                case "yes" -> {
                    timer.cancel();
                    selection = true;
                    launch("--ipport=" + port);
                }
                case "no" -> {
                    selection = true;
                    server.setFirstPlayer();
                    do {
                        System.out.println("Choose A to start a Socket client\nChoose B to start a RMI client");

                        if (scanner.hasNext()) {
                            input = scanner.next();
                            switch (input.toUpperCase()) {
                                case "A" -> {
                                    timer.cancel();
                                    if (server.isLocked()) {
                                        System.out.println("Another client is connecting");
                                    }
                                    server.block();
                                    System.out.println("Starting Socket");
                                    Client_Socket clientS = new Client_Socket();
                                    clientS.start(server, port);
                                }
                                case "B" -> {
                                    timer.cancel();
                                    if (server.isLocked()) {
                                        System.out.println("Another client is connecting");
                                    }
                                    server.block();
                                    try {
                                        GameClientInterface clientR = new Client();
                                        clientR.connection(server, clientR, server.getController());
                                        server.release();
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        }
                    }
                    while (!input.equalsIgnoreCase("A") && !input.equalsIgnoreCase("B"));
                }
            }
        }
    }

    /**
     * method that start the gui
     * @param stage stage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInScreen.fxml"));
        Parent root = fxmlLoader.load();
        Parameters parameters = getParameters();
        String ip = parameters.getNamed().get("ipport");
        LogInController loginController = fxmlLoader.getController();
        loginController.setIP(ip);
        loginController.initialize();
        fxmlLoader.setController(loginController);
        Scene scene = new Scene(root, 1080, 720);
        Image icon = new Image("assets/Publisher material/icon 50x50px.png");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("MyShelfie");
        stage.getIcons().add(icon);
        stage.show();
    }
}