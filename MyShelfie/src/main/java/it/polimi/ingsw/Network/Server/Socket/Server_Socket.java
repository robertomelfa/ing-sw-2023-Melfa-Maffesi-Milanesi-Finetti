package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameLogic;
import it.polimi.ingsw.Network.Client.Socket.Client_Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_Socket {

    private  ArrayList<Socket> clientlist = new ArrayList<>();
    public  void start()  {
        try {
            int port=8080;
            ServerSocket serversocket = new ServerSocket(port);
            System.out.println("[SERVER] is running on port "+port);
            Scanner input= new Scanner(System.in);
            int numplayers;
            do {
                System.out.println("Insert player number");
                numplayers=input.nextInt();
            }while (numplayers<2 || numplayers>4);
            int i=0;
            while (i<numplayers){
                int remaining=numplayers-i;
                System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket =serversocket.accept();
                clientlist.add(socket);
                i++;
            }
            System.out.println("Game is starting");
            try {
                Game game=new Game(numplayers);
                GameLogic gameLogic=new GameLogic(game);
                System.out.println("Users:");
                for (int k=0; k<clientlist.size();k++){
                    System.out.println(clientlist.get(k));
                }

                //richiesta e creazione nickname ai client
                //aggiunta players al game
                //Start turn

            }catch (Exception e){
                System.out.println("Exception in game");
            }




        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
        }
    }
}

