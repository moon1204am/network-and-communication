import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {

    Socket clientSocket;

    public MyRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        /* the parameters */
        String hostname;
        String port;
        String string;

        try {
        /* stringbuilder and string to save the response from server in */
        StringBuilder sb = new StringBuilder();
        String s = "";
        /* the data from client */
        byte[] fromClientBuffer = new byte[1024];
        /* the data in the input stream */
        InputStream inputStream = clientSocket.getInputStream();
        /* recieves the first data length in fromClientBuffer from the socket */
        int fromClientLength = clientSocket.getInputStream().read(fromClientBuffer);
        s = new String(fromClientBuffer, 0, fromClientLength, StandardCharsets.UTF_8);
        sb.append(s);

        while (s != null && !s.contains("\r\n\r\n")) {
            /* recieves the data length in fromClientBuffer from the socket */
            fromClientLength = clientSocket.getInputStream().read(fromClientBuffer);
            if (fromClientLength == -1 || fromClientLength == 0) {
                    break;
            } else {
                /* decodes the from client byte array into a string */
                s = new String(fromClientBuffer, 0, fromClientLength, StandardCharsets.UTF_8);
                /* appends it to stringbuilder */
                sb.append(s);
            }
        }

        /* the GET request from the client */
        String request = sb.toString();

        /* split the URL that the client is asking for to extract parameters to later send to askServer method */
        String[] params = request.split("[?&= ]");

        /* resets the parameters */
        hostname = null;
        port = null;
        string = null;

        /* http get request includes in the url what the user wants, we can find the ones asked for by looking for those
        keywords and then save the data thats one index ahead of those. */
        for (int i = 0; i < params.length - 1; i++) {
            if (params[i].equals("hostname")) {
                hostname = params[++i];
            } else if (params[i].equals("port")) {
                port = params[++i];
            } else if (params[i].equals("string")) {
                string = params[++i];
            }
        }

        /* the response to send to the client */
        String response = null;
        String fromServer = null;

        /* if the params are valid then the response is ok, else bad request, the server cannot understand the request,
        otherwise an exception is thrown because it can't find what the client asks for. */
        if (params[0].equals("GET") && params[1].equals("/ask") && (params[6].startsWith("HTTP") || params[8].startsWith("HTTP")) && hostname != null && port != null) {
            try {
                if(string != null) {
                    fromServer = TCPClient.askServer(hostname, Integer.parseInt(port), string);
                    response = "HTTP/1.1 200 OK\r\n\r\n" + fromServer;
                } else {
                    fromServer = TCPClient.askServer(hostname, Integer.parseInt(port));
                    response = "HTTP/1.1 200 OK\r\n\r\n" + fromServer;
                }  
            } catch (IOException e) {
                response = "HTTP/1.1 404 Not Found\r\n\r\n HTTP/1.1 404 Not Found";
            }
        } else {
            response = "HTTP/1.1 400 Bad Request\r\n\r\n HTTP/1.1 400 Bad Request";
        }

        /* encodes the string response into a byte array */
        byte[] toClientBuffer = response.getBytes(StandardCharsets.UTF_8);

        clientSocket.getOutputStream().write(toClientBuffer);
        clientSocket.close();
        } catch(IOException e) {

        }
    }
}