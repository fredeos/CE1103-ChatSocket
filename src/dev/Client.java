package src.dev;

import java.io.*;
import java.net.*;

public class Client{
    /*Initiates the socket to connect to the server*/
    public static void connect(){
    // These are the port and IP addres to which the client connects
        String SERVER_IP = "127.0.0.1";
        int SERVER_PORT =  3000;
        try{
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connecting to server at: " + "PORT=" + SERVER_PORT + "; IP=" + SERVER_IP);
        }
    //El catch obtiene los errores cuando no hay conexion con los puertos o con la IP.
        catch (IOException error){
            System.out.println("Got error");
            error.getStackTrace();
        }
    }
}