import java.net.*;
import java.io.*;
import tcpclient.TCPClient.*;

public class ConcHTTPAsk {

    public static void main(String[] args) throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true) {
            /* wait for a client to connect and create thread with connection */
            new Thread(new MyRunnable(welcomeSocket.accept())).start();
        }
    }
}

