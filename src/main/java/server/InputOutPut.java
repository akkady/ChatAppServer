package server;

import java.io.*;
import java.net.Socket;

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

}
