package server;

import persistence.MessageToSend;
import persistence.User;

import java.io.*;
import java.net.Socket;

public class Conversation extends Thread {
    private Socket socket ;
    private int clientNumber;
    private  InputOutPut io ;
    private String conversationOwner ;
    private boolean exit = false ;
    public Conversation(Socket socket,int clientNumber) {
        this.clientNumber = clientNumber;
        this.socket = socket ;
    }

    @Override
    public void run() {
        try {
            io = new InputOutPut(socket);
            boolean usernameExist = true;
            while (usernameExist){
                conversationOwner = io.getBufferedReader().readLine().trim();

                if (conversationOwner.contains("old:")){
                    String [] co = conversationOwner.split(":");
                    conversationOwner = co[1];
                    Server.clientInfos.add(new ClientInfo(co[1],socket));
                    System.out.println(conversationOwner+" was connected from : "+socket.getRemoteSocketAddress());
                    usernameExist = false ;
                    if (Server.daoFactory.messageToSends(conversationOwner) != null) {
                        for (MessageToSend message : Server.daoFactory.messageToSends(conversationOwner)) {
                            io.getPrintWriter().println(message.getSender() + ":" + message.getMessage());
                            Server.daoFactory.deleteMessage(message);
                        }
                    }
                }
                else if (check(conversationOwner)){
                    io.getPrintWriter().println("token:");
                }else {
                    Server.clientInfos.add(new ClientInfo(conversationOwner,socket));
                    System.out.println(conversationOwner+" was connected from : "+socket.getRemoteSocketAddress());
                    io.getPrintWriter().println("ok:");
                    usernameExist = false ;
                }
            }
            while (!exit){
                String msg = io.getBufferedReader().readLine();
                if (msg.equals("*s*") ) {
                    for (ClientInfo ci : Server.clientInfos){
                        if (ci.getUsername().equals(conversationOwner)) {
                            Server.clientInfos.remove(ci);
                            System.out.println(conversationOwner+ " was disconnected");
                            socket.close();
                            break;
                        }
                    }
                    io.getPrintWriter().println("you are disconnected");
                    exit = true ;
                }
                else if( msg.contains("=>") ){
                    String [] info = msg.split("=>");
                    sendTo(info[0],info[1]);
                }else {
                    if(Server.daoFactory.findUserByUsername(msg)!=null)io.getPrintWriter().println("$yes");
                    else io.getPrintWriter().println("$no");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendTo(String username, String msg) {
        for (ClientInfo ci : Server.clientInfos) {
            if (username.equals(ci.getUsername())){
                try {
                    InputOutPut io = new InputOutPut(ci.getSocketClient());
                    io.getPrintWriter().println(conversationOwner+" : "+msg);
                    return;
                }catch (IOException e) {}
            }
        }
        User receiver = Server.daoFactory.findUserByUsername(username);
        if (receiver!=null)  Server.daoFactory.registerMsgForAUser(receiver,conversationOwner,msg);
        else io.getPrintWriter().println("there is no such user");
    }

    private boolean check ( String username){
        for (ClientInfo cl : Server.clientInfos ) {
            if (cl.getUsername().equals(username)) return true ;
        }
        if (Server.daoFactory.addUser(username))return true;
        Server.clientInfos.add(new ClientInfo(conversationOwner,socket));
        return false ;
    }
}
