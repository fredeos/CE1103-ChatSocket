package src.dev;

import src.dev.Server;
import java.io.*;
import java.net.*;
import java.util.zip.InflaterInputStream;
import javax.imageio.IIOException;

/*The Client.java(& Client.class) is the separate file for setting up the client socket */
public class Client{
    private static String receivedmsg;
    private static Socket clientSocket;
    private static BufferedReader in;
    private static PrintWriter out;

/*Initiates the socket to connect to the server
 * parameters(String argIP, int argPort)
*/
    public static void connect(String argIP, int argPort) throws IOException{
        try{
            clientSocket = new Socket(argIP, argPort);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Connecting to server at: " + "PORT=" + argPort+ "; IP=" + argIP);
        } catch (IOException error){ //El catch obtiene los errores cuando no hay conexion con los puertos o con la IP.
            System.out.println("No server found at: " + argIP+", "+ argPort);
            error.getStackTrace();
        }
    }
    public static void sendmessage(String message) throws IOException{
        out.println(message);
        System.out.println("You sent: " + message);
    }

    public static void recievemsg() throws IOException{
        receivedmsg = in.readLine().trim();
        System.out.println("Server sent: " + receivedmsg);
        logicalgi.updatelog(receivedmsg, false);
    }
    public static void stop() throws IOException {// method to stop the server if it required
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Client left");
    }
}
