package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class LogInController implements Serializable {

    private Registry registry;
    private GameInterface server;
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

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry(ip, 1099);
                GameInterface server = (GameInterface) registry.lookup("GameInterface");
                this.registry = registry;
                this.server = server;
            }catch (Exception e){
                System.out.println("Exception in LogInController initialize");
            }
        });

    }
    @FXML
    private void RMIconnection() throws Exception {
        server.block();
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

    @FXML
    private void SocketConnection() throws Exception{
        server.block();
        scheduleTimer();
        if (server.getController().getClientList().size() == 0) {
            server.setFirstPlayer();
            server.setTemp();
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
            server.setTemp();
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
                    GameClientInterface client = new Client();
                    ((Client) client).setControllerView(fxmlLoader.getController());
                    client.connectionGUI(server, client, server.getController(), num, user);
                } else {
                    scheduleTimer();
                    labelNumPlayers.setOpacity(1);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            System.out.println("New socket client");
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
                    scheduleTimer();
                }
            }catch (Exception e){

            }
        }
        server.release();
    }

    public void submitNotFirst(ActionEvent event) throws RemoteException, Exception {
        timer.cancel();
        if(!server.getTemp()){
            // rmi
            try {
                String user = username2.getText();
                GameClientInterface client = new Client();
                if(!server.getController().checkExistingName(user) && user.length() >= 0){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) username2.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(new Scene(fxmlLoader.load(),1200,800));
                    ((Client) client).setControllerView(fxmlLoader.getController());
                    client.connectionGUI(server, client, server.getController(), user);
                } else {
                    label.setOpacity(1);
                    scheduleTimer();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }else{
            //socket
            System.out.println("sono in socket");
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
                    scheduleTimer();
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
        server.release();
    }

    public void set2(ActionEvent event){
        num = 2;
        button2.setOpacity(0.8);
        button3.setOpacity(1);
        button4.setOpacity(1);

    }
    public void set3(ActionEvent event){
        num = 3;
        button2.setOpacity(1);
        button3.setOpacity(0.8);
        button4.setOpacity(1);

    }
    public void set4(ActionEvent event){
        num = 4;
        button2.setOpacity(1);
        button3.setOpacity(1);
        button4.setOpacity(0.8);

    }

    public void setIP(String ip){
        this.ip = ip;
    }

    private void scheduleTimer(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
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
        timer.schedule(task, 30000);
    }
}
