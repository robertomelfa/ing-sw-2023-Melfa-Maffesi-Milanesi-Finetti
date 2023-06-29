package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Network.Client.RMI.ClientRMI_Interface;
import it.polimi.ingsw.Network.Client.RMI.Client_RMI;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Server.RMI.ServerRMI_Interface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class LogInController implements Serializable {

    private Registry registry;
    private ServerRMI_Interface server;
    private String ip;

    @FXML
    Button RMIButton;
    @FXML
    TextField username;
    @FXML
    Label labelNumPlayers;
    @FXML
    Button start;
    @FXML
    TextField username2;

    @FXML
    Label label;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button button4;
    private int num = 0;
    private boolean rmi = true;
    private static Timer timer;

    /**
     * connects the client to a player's selected server using the ip variable
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry(ip, 1099);
                ServerRMI_Interface server = (ServerRMI_Interface) registry.lookup("ServerRMI_Interface");
                this.registry = registry;
                this.server = server;
            }catch (Exception e){
                System.out.println("Exception in LogInController initialize");
            }
        });

    }

    /**
     * This method is called by pressing the RMI button in the LogIn interface.
     * Establishes the RMI connection between the client and the server then switches the stage to the next scene.
     * Uses a timer to handle inactive users, if a users doesn't perform an action in 30 seconds he's automatically
     * disconnected.
     * @throws Exception
     */
    @FXML
    private void RMIconnection() throws Exception {
        server.block();  //lock the server
        server.setTemp(false);
        scheduleTimer();
        if (server.getController().getClientList().size() == 0) {
            server.setFirstPlayer();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogIn2.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LogInController loginController = fxmlLoader.<LogInController>getController();
            loginController.setIP(ip);
            loginController.initialize();
            fxmlLoader.setController(loginController);
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(new Scene(root, 1080, 720));
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInNotFirst.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LogInController loginController = fxmlLoader.<LogInController>getController();
            loginController.setIP(ip);
            loginController.initialize();
            fxmlLoader.setController(loginController);
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(new Scene(root, 1080, 720));
        }
    }

    /**
     * This method is called by pressing the Socket button in the LogIn interface.
     * Establishes the socket connection between the client and the server then switches the stage to the next scene.
     * Uses a timer to handle inactive users, if a users doesn't perform an action in 30 seconds he's automatically
     * disconnected.
     * @throws Exception
     */
    @FXML
    private void SocketConnection() throws Exception{
        server.block();
        scheduleTimer();
        server.setTemp(true);
        if (server.getController().getClientList().size() == 0) {
            server.setFirstPlayer();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogIn2.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LogInController loginController = fxmlLoader.<LogInController>getController();
            loginController.setIP(ip);
            loginController.initialize();
            fxmlLoader.setController(loginController);
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(new Scene(root, 1080, 720));
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInNotFirst.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LogInController loginController = fxmlLoader.<LogInController>getController();
            loginController.setIP(ip);
            loginController.initialize();
            fxmlLoader.setController(loginController);
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setResizable(false);
            stage.setScene(new Scene(root, 1080, 720));
        }
    }

    /**
     * Used only by the first player of each game to decide the player's number of the game and
     * to set his username, then switches scene to the game scene.
     * Implements a timer to handle inactive players by disconnecting them after 30 seconds with no actions
     * @param event : pressing the submit button
     * @throws Exception
     */
    public void submit(ActionEvent event) throws Exception{
        timer.cancel();
        if(!server.getTemp()){
            try {
                String user = username.getText();
                if ( num > 1 && num < 5 && user.length() > 0){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) start.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load(),1200,800));
                    stage.setResizable(false);
                    ClientRMI_Interface client = new Client_RMI();
                    ((Client_RMI) client).setControllerView(fxmlLoader.getController());
                    client.connectionGUI(server, client, server.getController(), num, user);
                } else {
                    labelNumPlayers.setOpacity(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            try{
                String user = username.getText();
                if (num > 1 && num < 5 && user.length() > 0) {
                    Client_Socket clientSocket = new Client_Socket();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    //clientSocket.sendInt(num);
                    Stage stage = (Stage) username.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(new Scene(fxmlLoader.load(),1200,800));
                    try {
                        clientSocket.setControllerGui(fxmlLoader.getController());
                        clientSocket.startGUI(server,ip, num, user);
                    }catch (Exception e){
                        System.out.println("error starting the socket");
                    }
                }else {
                    labelNumPlayers.setOpacity(1);
                }
            }catch (Exception e){

            }
        }
        server.release();
    }

    /**
     * Used by users that connects to a game already created.
     * Take the username of the player from the TextField and if it's not already it connects the player to the
     * game using that username.
     * Implements a timer to handle inactive players by disconnecting them after 30 seconds with no actions
     *  @param event : pressing the submit button
     * @throws RemoteException
     * @throws Exception
     */
    public void submitNotFirst(ActionEvent event) throws RemoteException, Exception {
        timer.cancel();
        if(!server.getTemp()){
            // rmi
            try {
                String user = username2.getText();
                ClientRMI_Interface client = new Client_RMI();
                if(!server.getController().checkExistingName(user) && user.length() >= 0){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) username2.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(new Scene(fxmlLoader.load(),1200,800));
                    ((Client_RMI) client).setControllerView(fxmlLoader.getController());
                    client.connectionGUI(server, client, server.getController(), user);
                } else {
                    label.setOpacity(1);
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }else{
            //socket
            try {
                Client_Socket clientSocket = new Client_Socket();
                String user = username2.getText();
                if(!server.getController().checkExistingName(user) && user.length() >= 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) username2.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(new Scene(fxmlLoader.load(),1200,800));
                    try {
                        clientSocket.setControllerGui(fxmlLoader.getController());
                        clientSocket.startGUI(server,ip, 0 , user);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    label.setOpacity(1);
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
        server.release();
    }

    /**
     * sets the num variable to 2.
     * The num variable it's used to select the player's number of a game
     * @param event : pressing the "2" button
     */
    public void set2(ActionEvent event){
        num = 2;
        button2.setOpacity(1);
        button3.setOpacity(0.5);
        button4.setOpacity(0.5);

    }

    /**
     * sets the num variable to 3
     * @param event : pressing the "3" button
     */
    public void set3(ActionEvent event){
        num = 3;
        button2.setOpacity(0.5);
        button3.setOpacity(1);
        button4.setOpacity(0.5);

    }

    /**
     * sets the num variable to 4
     * @param event : pressing the "4" button
     */
    public void set4(ActionEvent event){
        num = 4;
        button2.setOpacity(0.5);
        button3.setOpacity(0.5);
        button4.setOpacity(1);

    }

    /**
     * sets the ip
     * @param ip : ip we want to set
     */
    public void setIP(String ip){
        this.ip = ip;
    }

    /**
     * Timer that is called when we have to wait for a player's input.
     * After 30 seconds the TimerTask runs disconnecting the player.
     * It's used to handle inactive player and avoid a possible deadlock.
     * @throws RemoteException
     */
    private void scheduleTimer() throws RemoteException {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        timer.schedule(task, 30000);
    }
}
