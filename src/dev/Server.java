package src.dev;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        //Iniatilizing the variables for server
        final ServerSocket serverSocket ;
        final Socket clientSocket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc=new Scanner(System.in);
        //trying to make a connection
        try {
            serverSocket = new ServerSocket(5000);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

            Thread sender= new Thread(new Runnable() {
                String msg; //variable that contain the data of users
                @Override   // run method is overriding
                public void run() {
                    while(true){
                        msg = sc.nextLine(); //reads data from user's keybord
                        out.println(msg);    // write data stored in msg in the clientSocket
                        out.flush();   // forces the sending of the data
                    }
                }
            });
            //iniatilizing sender
            sender.start();

            Thread receive= new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        //tant que le client est connecté
                        while(msg!=null){
                            System.out.println("Client : "+msg); // reading and print the message, this must be reading and show by the app
                            msg = in.readLine();
                        }

                        System.out.println("Client déconecté");// reading if the client is conected

                        out.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
