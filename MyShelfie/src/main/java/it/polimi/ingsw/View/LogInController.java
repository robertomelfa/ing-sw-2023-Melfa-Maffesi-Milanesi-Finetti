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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LogInController {

    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
    GameInterface server = (GameInterface) registry.lookup("GameInterface");

    @FXML
    Button RMIButton;
    @FXML
    TextField numPlayer;
    @FXML
    TextField username;
    @FXML
    Button start;

    Client_Socket clientSocket;

    private boolean RMI = true;

    public LogInController() throws RemoteException, NotBoundException {
    }
    @FXML
    private void RMIconnection() throws Exception {
        nextScene();
    }

    @FXML
    private void SocketConnection() throws Exception{
        Client_Socket clientSocket = new Client_Socket();
        RMI = false;
        clientSocket.startGUI(server, (Stage) RMIButton.getScene().getWindow());
        server.release();
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
                String user = username.getText();
                int n = Integer.parseInt(numPlayer.getText());
                GameClientInterface client = new Client();
                client.connectionGUI(server, client, server.getController(), n, user);
                server.release();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                Stage stage = (Stage) start.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load(), 1240, 1024));

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                int n = Integer.parseInt(numPlayer.getText());
                String user = username.getText();
                clientSocket.sendInt(n);
                Message msg = new Message(MessageType.sendNickname, user);
                clientSocket.sendMessage(msg);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void submitNotFirst(ActionEvent event){
        if(RMI){
            try {
                String user = username.getText();
                GameClientInterface client = new Client();
                client.connectionGUI(server, client, server.getController(), user);
                server.release();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                Stage stage = (Stage) start.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load(), 1240, 1024));
            }catch (Exception e){

            }
        }else {
            try {
                String user = username.getText();
                Message msg = new Message(MessageType.sendNickname, user);
                clientSocket.sendMessage(msg);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

}
