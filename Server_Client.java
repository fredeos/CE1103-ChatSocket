import java.io.*;
import java.net.*;
/*The Server_Client class calls the server and client sockets */
public class Server_Client{
    public static void main(String[] args){
        System.out.println("Starting sockets ...");
        Server.startup();
        Client.connect();
    }
}
class Server{
    //The server socket must start at an specific IP and port (give the server a backlog maximum of 20)
    public static void startup(){

    }
}
class Client{
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
