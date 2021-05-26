package server;

import persistence.DaoFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server extends Thread{

    public static DaoFactory daoFactory = new DaoFactory();
    public static List<ClientInfo> clientInfos = new ArrayList<>();
    private int clientNumber = 0 ;
    public static void main(String[] args) {
        new Server().start();
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234) ;
            System.out.println("demarage du serveur ... ");
            System.out.println("serveur est demaré ");
            while (true){
                Socket socket = serverSocket.accept() ;
                ++clientNumber;
                new Conversation(socket,clientNumber).start();
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
