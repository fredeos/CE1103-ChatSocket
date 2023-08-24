package src.dev;

import java.io.*;
import java.net.*;

/*The Client.java(& Client.class) is the separate file for setting up the client socket */
public class Client{
    private static String receivedmsg; //String variable for received messages
    private static Socket clientSocket;
    private static BufferedReader in; //Reader for decoding messages from Server
    private static PrintWriter out; //PrintWriter for sending messages to the Server

/*Initiates the socket to connect to the server
 *@param argIP IP address to which the client socket tries to connect
 *@param argPort port to which the client socket connects
*/
    public static void connect(String argIP, int argPort) throws IOException{
        try{
            clientSocket = new Socket(argIP, argPort); //Creates the client at the desired IP and Port
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Reads IN messages from Server
            out = new PrintWriter(clientSocket.getOutputStream(), true);// Sends OUT messages to Server
            System.out.println("Connecting to server at: " + "PORT=" + argPort+ "; IP=" + argIP);
        } catch (IOException error){
            System.out.println("No server found at: " + argIP+", "+ argPort);
            error.getStackTrace();
        }
    }

    /*Sends a message to the Server
    *@param message string text to send
    */
    public static void sendmessage(String message) throws IOException{
        out.println(message); //Sends the message over
        System.out.println("You sent: " + message);
    }

    /*Receives an incoming message from the Server and updates the log*/
    public static void recievemsg() throws IOException{
        receivedmsg = in.readLine().trim(); //Gets the received message and trims it to remove any blank spaces.
        System.out.println("Server sent: " + receivedmsg);
        chatGI.updatelog(receivedmsg, false); //Updates the Client message display with the message sent by the Server
    }
    
    /*Stops the connection with the server socket and ends the client socket*/
    public static void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Client left");
    }
}
