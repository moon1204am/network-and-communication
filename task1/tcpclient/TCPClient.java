package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        /* input buffer from server */
        byte[] fromServerBuffer = new byte[1024];

        Socket clientSocket = new Socket(hostname, port);

        clientSocket.setSoTimeout(5000);

        /* encodes the string ToServer into a byte array */
        byte [] fromUserBuffer = ToServer.getBytes(StandardCharsets.UTF_8);

        /* user input length */
        int fromUserLength = fromUserBuffer.length;

        /* sends the data in fromUserBuffer over the socket */
        clientSocket.getOutputStream().write(fromUserBuffer, 0, fromUserLength);

        /* recieves the first data length in from server buffer from the socket */
        int fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);
        /* stringbuilder and string to save the answer from server in */
        StringBuilder sb = new StringBuilder();
        String fromServer = "";
        /* decodes the from server byte array into a string */
        fromServer = new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8);
        sb.append(fromServer);

        try {
            while((fromServerLength = clientSocket.getInputStream().read(fromServerBuffer)) != -1) {
            /* recieves the data length in fromServerBuffer from the socket */
            fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);
            if(fromServerLength == -1) {
                break;
            }
            /* decodes the from server byte array into a string */
            fromServer = new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8);
            /* appends it to the stringbuilder */
            sb.append(fromServer);
            }
        } catch (SocketTimeoutException e) {

        }

        fromServer = sb.toString();

        clientSocket.close();

        return fromServer;
    }

    public static String askServer(String hostname, int port) throws  IOException {
        /* input buffer from server */
        byte[] fromServerBuffer = new byte[1024];

        Socket clientSocket = new Socket(hostname, port);

        clientSocket.setSoTimeout(5000);

        /* recieves the data length in fromServerBuffer from the socket */
        int fromServerLength = clientSocket.getInputStream().read(fromServerBuffer);

        /* decodes the server byte array into a string */
        String fromServer = new String(fromServerBuffer, 0, fromServerLength, StandardCharsets.UTF_8);

        clientSocket.close();

        return fromServer;
    }
}

