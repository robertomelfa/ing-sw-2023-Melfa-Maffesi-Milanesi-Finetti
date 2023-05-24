package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Client.RMI.Client;
import it.polimi.ingsw.Network.Client.RMI.GameClientInterface;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LogInController{

    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
    GameInterface server = (GameInterface) registry.lookup("GameInterface");

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
    private boolean RMI = true;

    public LogInController() throws RemoteException, NotBoundException {
    }
    @FXML
    private void RMIconnection() throws Exception {
        nextScene();
    }

    @FXML
    private void SocketConnection() throws Exception{
        RMI = false;
        nextScene();
    }

    public void nextScene() throws IOException {
        if (server.isFirstPlayer()) {
            server.setFirstPlayer();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogIn2.fxml"));
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load(), 1080, 720));
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LogInNotFirst.fxml"));
            Stage stage = (Stage) RMIButton.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load(), 1080, 720));
        }

    }


    public void submit(ActionEvent event){
        if(RMI) {
            try {
                if ( num > 1 && num < 5){
                    String user = username.getText();
                    GameClientInterface client = new Client();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    server.release();
                    client.connectionGUI(server, client, server.getController(), num, user);
                    Stage stage = (Stage) start.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load()));
                } else {
                    labelNumPlayers.setOpacity(1);
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                if( num > 1 && num < 5){
                    Client_Socket clientSocket = new Client_Socket();
                    clientSocket.startGUI(server);
                    String user = username.getText();
                    clientSocket.sendInt(num);
                    Message msg = new Message(MessageType.sendNickname, user);
                    clientSocket.sendMessage(msg);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) start.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    server.release();
                }else {
                    labelNumPlayers.setOpacity(1);
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void submitNotFirst(ActionEvent event){
        if(RMI){
            try {
                String user = username2.getText();
                GameClientInterface client = new Client();
                if(!server.getController().checkExistingName(user)){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) username2.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load()));
                    client.connectionGUI(server, client, server.getController(), user);
                    server.release();
                } else {
                    label.setOpacity(1);
                }
            }catch (Exception e){
                System.out.println(e);
            }
        } else {
            try {
                Client_Socket clientSocket = new Client_Socket();
                String user = username2.getText();
                if(!server.getController().checkExistingName(user)) {
                    clientSocket.startGUI(server);
                    Message msg = new Message(MessageType.sendNickname, user);
                    clientSocket.sendMessage(msg);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                    Stage stage = (Stage) username2.getScene().getWindow();
                    stage.setScene(new Scene(fxmlLoader.load(), 1240, 1024));
                } else {
                    label.setOpacity(1);
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void set2(ActionEvent event){
        num = 2;
        button2.setOpacity(0.8);
        button3.setOpacity(1);
        button4.setOpacity(1);

    }
    public void set3(ActionEvent event){
        num = 2;
        button2.setOpacity(1);
        button3.setOpacity(0.8);
        button4.setOpacity(1);

    }
    public void set4(ActionEvent event){
        num = 2;
        button2.setOpacity(1);
        button3.setOpacity(1);
        button4.setOpacity(0.8);

    }
}
