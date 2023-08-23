package src.dev;

import src.dev.Server;
import java.io.*;
import java.net.*;
import java.util.zip.InflaterInputStream;
import javax.imageio.IIOException;

/*The Client.java(& Client.class) is the separate file for setting up the client socket */
public class Client{
    public static String receivedmsg;

    private static Socket clientSocket;
    private static BufferedReader in;
    private static PrintWriter out;

/*Initiates the socket to connect to the server
 * parameters(String argIP, int argPort)
*/
    public static void connect(String argIP, int argPort) throws IOException{
    // These are the port and IP addres to which the client connects
        String SERVER_IP = argIP;
        int SERVER_PORT =  argPort;
        try{
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Connecting to server at: " + "PORT=" + SERVER_PORT + "; IP=" + SERVER_IP);
        } catch (IOException error){ //El catch obtiene los errores cuando no hay conexion con los puertos o con la IP.
            System.out.println("No server found at: " + SERVER_IP+", "+ SERVER_PORT);
            error.getStackTrace();
        }
    }
    public static void sendmessage(String message) throws IOException{
        out.println(message);
    }

    public static void recievemsg() throws IOException{
        if (!(in.readLine().trim()).isEmpty()){
            receivedmsg = in.readLine();
        } else {
            receivedmsg = null;
        }
    }
}
