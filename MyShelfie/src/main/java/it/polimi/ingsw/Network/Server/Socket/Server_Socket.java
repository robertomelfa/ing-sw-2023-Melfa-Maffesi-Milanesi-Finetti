package it.polimi.ingsw.Network.Server.Socket;

import it.polimi.ingsw.Network.Client.Socket.ClientClass;
import it.polimi.ingsw.Controller.Socket.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_Socket implements Serializable {

    private  ArrayList<ClientClass> clientlist = new ArrayList<>();
    private SocketController controller;
    public  void start() throws Exception{
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
            while (i < numplayers){
                int remaining=numplayers-i;
                System.out.println("Waiting for a client...\n"+remaining+" Clients remaining");
                Socket socket = serversocket.accept();  // questo è il client
                ClientClass client = new ClientClass(socket);   // associo il client ad un player
                clientlist.add(client);
                i++;
            }

            // chiedo il nome a tutti i client (potrei fare una funzione unica)
            for(int j = 0; j < clientlist.size(); j++){
                sendMessage("Inserisci nome del giocatore: ", j);
                clientlist.get(j).setPlayer(receiveString(clientlist.get(j).getSocket()));  // nomino il player
                System.out.println("il giocatore " + clientlist.get(j).getPlayer().getNickname() + " aggiunto");
            }

            System.out.println("Game is starting");

            try {
                controller = new SocketController(serversocket,clientlist,numplayers);
                controller.shufflePlayers();
                gameTableToAll();
            }catch (Exception e){
                System.out.println("Exception in game");
            }

        }catch (IOException e){
            System.out.println("[SERVER] fatal error");
        }
    }

    public void gameTableToAll() throws IOException{
        for (int i=0; i < clientlist.size(); i++){
            ObjectOutputStream oos = new ObjectOutputStream(clientlist.get(i).getSocket().getOutputStream());
            oos.writeObject(controller.getGameLogic().getGame().getGameTable());
            System.out.println("GameTable sent to " + clientlist.get(i).getPlayer().getNickname());
        }
    }

    public String receiveString(Socket client) throws IOException, ClassNotFoundException{
        InputStream in = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        return line;
    }

    public void sendMessage(String msg, int i) throws IOException, ClassNotFoundException{
        PrintWriter outToClient = new PrintWriter(clientlist.get(i).getSocket().getOutputStream(), true);
        outToClient.println(msg);
    }
}

