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
        //Se asigna una variable para convertir el IP del server
        InetAddress address = InetAddress.getByName(argIP);
        serverSocket = new ServerSocket(argPort,1,address);
        System.out.println("Server started at: " + argPort + ", " + argIP);

        //Se inicia un thread separado para esperar la conneccion de un socket cliente
        Thread clientThread = new Thread(()->{
            try {
                serverSocket.setSoTimeout(10000);
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
            // Messages of entry and exit
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException error){
                error.getStackTrace();
            }
        });
        clientThread.start();
    }
    public static void sendMessage(String message) {// method to send messages to client
        out.println(message);
        System.out.println("You(host) sent: "+ message);
    }

    public static void receiveMessage() throws IOException {// method to receive messages from client
        receivedmsg = in.readLine().trim();
        System.out.println("Client sent: " + receivedmsg);
        logicalgi.updatelog(receivedmsg, true);
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
