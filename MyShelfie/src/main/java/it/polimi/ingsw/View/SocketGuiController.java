package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Client.Socket.Client_Socket;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Server.RMI.GameInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SocketGuiController {
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
    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
    GameInterface server = (GameInterface) registry.lookup("GameInterface");

    private int num;
    @FXML
    Button button2;
    @FXML
    Button button3;
    @FXML
    Button button4;

    public SocketGuiController() throws RemoteException, NotBoundException {
    }

    @FXML
    public void submit(ActionEvent event){
        try{
            if (num > 1 && num < 5) {
                Client_Socket clientSocket = new Client_Socket();
                String user = username.getText();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                //clientSocket.sendInt(num);
                Platform.runLater(() ->{
                    try {
                        clientSocket.startGUI(server, num, user);
                        clientSocket.setControllerGui(fxmlLoader.getController());
                    }catch (Exception e){
                        System.out.println("error starting the socket");
                    }
                });
                Stage stage = (Stage) start.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load()));
                clientSocket.setControllerGui(fxmlLoader.getController());
            }else {
            labelNumPlayers.setOpacity(1);
            }
        }catch (Exception e){

        }
    }

    @FXML
    public void submitNotFirst(){
        try {
            Client_Socket clientSocket = new Client_Socket();
            String user = username2.getText();
            if(!server.getController().checkExistingName(user)) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MyShelfieGui.fxml"));
                Platform.runLater(() -> {
                    try {
                        clientSocket.startGUI(server, 0 , user);
                        clientSocket.setControllerGui(fxmlLoader.getController());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                Stage stage = (Stage) username2.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load(), 1240, 1024));
                clientSocket.setControllerGui(fxmlLoader.getController());
            } else {
                label.setOpacity(1);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    public void set2(ActionEvent event){
        num = 2;
        button2.setOpacity(0.8);
        button3.setOpacity(1);
        button4.setOpacity(1);

    }
    @FXML
    public void set3(ActionEvent event){
        num = 3;
        button2.setOpacity(1);
        button3.setOpacity(0.8);
        button4.setOpacity(1);

    }
    @FXML
    public void set4(ActionEvent event){
        num = 4;
        button2.setOpacity(1);
        button3.setOpacity(1);
        button4.setOpacity(0.8);

    }
}
