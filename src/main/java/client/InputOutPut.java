package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class InputOutPut {

    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private OutputStream outputStream ;
    private PrintWriter printWriter ;

    public InputOutPut(Socket socket) throws IOException {
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream,true);
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void reader(){
        new InputWait().start();
    }
    public void writer(){
        new OutputWait().start();
    }
    class InputWait extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    System.out.println(getBufferedReader().readLine());
                } catch (IOException e) { }
            }
        }
    }
    class OutputWait extends Thread{
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true){
                getPrintWriter().println(scanner.nextLine());
            }
        }
    }
}
