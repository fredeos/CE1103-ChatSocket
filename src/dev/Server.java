package src.dev;
//Libraries needed for the class
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {//Iniatilizing the server using sockets
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado en el puerto " + port);

        clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

        // messages of entry and exit
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String message) {// method to send messeges to client
        out.println(message);
    }

    public String receiveMessage() throws IOException {// method to receive messeges from client
        return in.readLine();
    }

    public void stop() throws IOException {// method to stop the server if it requiered
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Servidor detenido");
    }

    """public static void main(String[] args) { // this method is an example of the way to use the class
        ChatServer server = new ChatServer();
        int port = 12345; // Port that the server will listen
        try {
            server.start(port);

            // This is just an example of how to use the class
            server.sendMessage("¡Hola cliente!");
            String receivedMessage = server.receiveMessage();
            System.out.println("Mensaje del cliente: " + receivedMessage);

            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }"""
    }

