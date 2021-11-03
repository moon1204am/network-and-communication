import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        /* calls socket(), bind(), and listen() system calls to create a "welcoming" socket with port number 8888 */
        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true) {
            /* wait for a client to connect, create a new socket for communication with the client */
            Socket connectionSocket = welcomeSocket.accept();
            /* stringbuilder and string to save the response from server in */
            StringBuilder sb = new StringBuilder();
            String s = "";
            /* the data from client */
            byte[] fromClientBuffer = new byte[1024];
            /* the data in the input stream */
            InputStream inputStream = connectionSocket.getInputStream();
            /* recieves the first data length in fromClientBuffer from the socket */
            int fromClientLength = connectionSocket.getInputStream().read(fromClientBuffer);
            s = new String(fromClientBuffer, 0, fromClientLength, StandardCharsets.UTF_8);
            sb.append(s);

            while(s != null && !s.contains("\r\n\r\n")) {
                /* recieves the data length in fromClientBuffer from the socket */
                fromClientLength = connectionSocket.getInputStream().read(fromClientBuffer);
                if(fromClientLength == -1 || fromClientLength == 0) {
                    break;
                } else {
                    /* decodes the from client byte array into a string */
                    s = new String(fromClientBuffer, 0, fromClientLength, StandardCharsets.UTF_8);
                    /* appends it to stringbuilder */
                    sb.append(s);
                }
            }

            String toClient = "HTTP/1.1 200 OK\r\n\r\n" + sb.toString();

            /* encodes the string toClient into a byte array */
            byte[] toClientBuffer = toClient.getBytes(StandardCharsets.UTF_8);

            connectionSocket.getOutputStream().write(toClientBuffer);
            connectionSocket.close();
        }
    }
}