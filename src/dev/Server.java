package src.dev;

import java.io.*;
import java.net.*;
/*Class for Server creation and manipulation*/
public class Server {
    private static String receivedmsg; // String variable for storing a message
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out; //PrintWriter for sending messgae
    private static BufferedReader in; //Reader for receiving and decoding a message

    /* Boots up the Server Sokcet and starts the queue for receiving clients
    *@param argIP a string containing the IP to boot the server on
    *@param argPort a integer value containing the port for the server
    */
    public static void startup(String argIP, int argPort) throws IOException { //Iniatilizing the server using sockets
        InetAddress address = InetAddress.getByName(argIP); //This converts the received IP address to an Inet address usable for creating the ServerSocket
        serverSocket = new ServerSocket(argPort,1,address); //Creates the server socket
        System.out.println("Server started at: " + argPort + ", " + argIP);

        //A new thread is created to wait on standby until a client connects to the sever
        Thread clientThread = new Thread(()->{
            try {
                serverSocket.setSoTimeout(10000); //Sets a 10 second wait until timeout(the method receives miliseconds)
                clientSocket = serverSocket.accept(); // Creates a client socket based on the connected client
                System.out.println("Client connected: " + clientSocket.getInetAddress());
            // Messages of entry and exit
                out = new PrintWriter(clientSocket.getOutputStream(), true); //Writes OUT messages to the client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Receives IN messages from the client
            } catch (IOException error){
                error.getStackTrace();
            }
        });
        clientThread.start(); //Starts the thread
    }

    /* Sends a a message to the BufferedReader of the client
    *@param message a string text that the server wants to send
    */
    public static void sendMessage(String message) {// Method to send messages to client
        out.println(message);
        System.out.println("You(host) sent: "+ message);
    }

    /* Checks for a received message and updates the log on the Client side
    */
    public static void receiveMessage() throws IOException {// method to receive messages from client
        receivedmsg = in.readLine().trim(); //Receives the decoded message and trims it to remove any blank spaces.
        System.out.println("Client sent: " + receivedmsg);
        chatGI.updatelog(receivedmsg, true); //Moves the message over to the client message display(log)
    }

    /*Stops the server socket and the connection with the client*/
    public static void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server stopped");
    }
}
