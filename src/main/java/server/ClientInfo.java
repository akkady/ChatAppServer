package server;


import java.net.Socket;

public class ClientInfo {
    private String username ;
    private Socket socketClient ;
    public ClientInfo(String username,Socket socket){
        this.socketClient = socket;
        this.username = username ;
    }

    public ClientInfo(String username) {
        this.username = username;
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
