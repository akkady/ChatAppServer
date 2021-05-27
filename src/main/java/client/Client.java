package client;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
    private InputOutPut io ;
    public static void main(String[] args) {
        new Client().start();
    }
    @Override
    public void run() {
        System.out.print("Please enter your username : ");
        try {
            Socket socket = new Socket("localhost",1234);
            io = new InputOutPut(socket);
            io.reader();
            io.writer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
