package src.dev;

import src.dev.Client;
import java.io.*;
import java.net.*;
import java.util.zip.InflaterInputStream;

public class Server {
    private static String receivedmsg;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void startup(String argIP, int argPort) throws IOException { //Iniatilizing the server using sockets
        String SERVER_IP = argIP;
        int SERVER_PORT = argPort;
        InetAddress address = InetAddress.getByName(SERVER_IP);
        serverSocket = new ServerSocket(SERVER_PORT,20,address);
        System.out.println("Server started at: " + SERVER_IP + ", " + SERVER_PORT);

        clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        // Messages of entry and exit
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    public static void sendMessage(String message) {// method to send messages to client
        out.println(message);
    }

    public static void receiveMessage() throws IOException {// method to receive messages from client
        if (!(in.readLine().trim()).isEmpty()){
            receivedmsg = in.readLine();
        } else {
            receivedmsg = null;
        }
    }

    //public static void getmessage(){}

    public static void stop() throws IOException {// method to stop the server if it required
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server stopped");
    }
}
