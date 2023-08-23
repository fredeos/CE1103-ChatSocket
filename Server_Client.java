import java.io.*;
import java.net.*;

public class Server{
    /*Boots the server to listen to the designed port and IP*/
    public static void startup(){
        String IP = "127.0.0.1";
        int PORT =  3000;
        try{
            InetAddress address = InetAddress.getByName(IP);
            // Line 18: binds the desired string IP to the network as a host
            ServerSocket serverSocket = new ServerSocket(PORT, 20, address);
            System.out.println("Listening on: " + PORT + " at " + IP);

        }
        catch(IOException error){
            System.out.println("Got error");
            error.getStackTrace();
        }
    }
}
    }
}
class Client{
    public static void main(String[] args){

    }
}
