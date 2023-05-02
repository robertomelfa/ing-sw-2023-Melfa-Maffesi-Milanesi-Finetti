package it.polimi.ingsw.Network.Client.Socket;

import it.polimi.ingsw.Model.GameTable;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client_Socket implements Serializable {

    private Socket socket;  // è il server

    public  void start() throws Exception{
        String ins;
        Scanner scanner=new Scanner(System.in);
        System.out.print("Make a choice:\nN: start a new game\nJ: join a game\n");
        ins=scanner.next();
        switch (ins.toUpperCase()){
            case "N":
                System.out.println("You chose N");
                //creare automaticamente una nuova istanza di server?

            break;
            case "J":
                System.out.println("You chose J");
                //ritornare la lista dei game attualmente attivi e permettere al client di scegliere una partita
                int port;
                System.out.println("Insert server port");
                port=scanner.nextInt();
                connect("127.0.0.1",port);

                try {
                    clientlogic();
                }catch (Exception e){
                    try{
                        socket.close();
                    }catch (IOException i){
                        System.out.println("Impossible to close socket");
                    }
                }
            break;
            default:
                System.out.println("Invalid choice");
            break;
        }
    }

    public  void connect(String host,int port){
        try {
            Socket socket = new Socket(host,port);
            this.socket=socket;
            System.out.println("Client is running...");
        }catch(IOException e){
            System.out.println("Client fatal error");
        }
    }

    public void receiveGameTable(){
        try{
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            GameTable gametable = (GameTable) ois.readObject();
            gametable.viewTable();
        }catch (IOException i){
            System.out.println("IOException");
        }catch (ClassNotFoundException c){
            System.out.println("ClassNotFoundException");
        }
    }

    public void clientlogic() throws Exception{
        // ricevo richiesta del nome e invio nome
        receiveMessage();
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        sendNickName(name);
        receiveGameTable();
    }
    public void receiveMessage() throws IOException, ClassNotFoundException, Exception{
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        System.out.println(line);
    }

    public void sendNickName(String name) throws IOException, ClassNotFoundException, Exception{
        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
        outToServer.println(name);
    }
}


