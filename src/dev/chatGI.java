package src.dev;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/* Chat Graphical Interface(chatgI) is the file class for running the hub, server and client windows.
*/
public class chatGI extends Application{
// Class variables for the program
    private int[] rootSize = {600,450}; //Main window(hub) window size
    private String wIP="127.0.0.1"; //Selected IP
    private int wPort=3000; //Selected Port
    private static int[] subSize = {650, 350}; //Server and client windows 
    
// TextArea objects(nodes) for storing the messages during a chat session. They are declarated outside any method for usage in other methods
    private static TextArea clientlog = new TextArea();
    private static TextArea serverlog = new TextArea();

/*Initializes the main window(hub) for creating and joining chats*/
    @Override
    public void start(Stage primaryStage) throws Exception {
    //Containers for the window and nodes
        Pane rootPane = new Pane();
        AnchorPane mainanchor = new AnchorPane(); //Anchor for margin based coordinates

    //Nodes
        //     _______________________
        //____/ 1st order objects(Plain/non-interactive)
        Rectangle sidebar = new Rectangle(80.0,(double)rootSize[1], Color.BLACK);
        mainanchor.setTopAnchor(sidebar, 0.0);
        mainanchor.setLeftAnchor(sidebar, 0.0);

        Rectangle topbar = new Rectangle((double)rootSize[0], 30.0, Color.CYAN);
        mainanchor.setTopAnchor(topbar, 0.0);
        mainanchor.setLeftAnchor(topbar, 0.0);

        Text headertxt = new Text("Welcome to ChatSocket");
        headertxt.setStyle("-fx-font-size: 17pt");
        headertxt.setFill(Color.BLACK);
        headertxt.setTextAlignment(TextAlignment.CENTER);
        headertxt.setLayoutX((double)rootSize[0]/2-130);
        headertxt.setLayoutY(20.0);

        Text disIP = new Text("Enter the IP address:");
        disIP.setStyle("-fx-font-size: 13pt");
        disIP.setFill(Color.BLACK);
        disIP.setLayoutX(150.0);
        disIP.setLayoutY((double)rootSize[1]/2-70);

        Text disPort = new Text("Enter the port to connect:");
        disPort.setStyle("-fx-font-size: 13pt");
        disPort.setFill(Color.BLACK);
        disPort.setLayoutX(inIP.getLayoutX()+35);
        disPort.setLayoutY(inIP.getLayoutY()+90);

        Text help = new Text("Your IP and Port were saved! Please select to Start or Join a server");
        help.setStyle("-fx-font-size: 8pt");
        help.setFill(Color.RED);
            help.setLayoutX((double)rootSize[0]/4);
            help.setLayoutY((double)rootSize[1]-40);
        help.setVisible(false);

        //     _______________________
        //____/ 2nd order objects(Textfields)
        TextField enterIP = new TextField();
        enterIP.setPromptText("Enter a valid IP(NONE:by default 127.0.0.1)");
        enterIP.setPrefSize((double)rootSize[0]/2-30, 25.0);
        enterIP.setLayoutX(inIP.getLayoutX());
        enterIP.setLayoutY(inIP.getLayoutY()+20);

        TextField enterPort = new TextField();
        enterPort.setPromptText("Enter a port(NONE:by default 3000)");
        enterPort.setPrefSize(enterIP.getPrefWidth(), enterIP.getPrefHeight());
        enterPort.setLayoutX(inPort.getLayoutX());
        enterPort.setLayoutY(inPort.getLayoutY()+20);
        
        //     _______________________c
        //____/ 3rd order objects(Interactive: Buttons)
        Button startbutton = new Button("Start Hosting");
        startbutton.setOnAction(event ->{ //Event handler for the action of clicking the button
            System.out.println("Starting your chat at: IP "+wIP+", PORT "+wPort);
            Server.startup(); // Calls the booting up method of the Server.java file
            chatGI.serverwindow(); // Creates another window for the server(host) chat
        });
        startbutton.setAlignment(Pos.CENTER);
        startbutton.setTextAlignment(TextAlignment.CENTER);
        startbutton.setPrefSize(70.0, 40.0);
        startbutton.setWrapText(true);
            mainanchor.setTopAnchor(startbutton, (double)rootSize[1]/2 -50);
            mainanchor.setLeftAnchor(startbutton, 5.0);
        
        Button joinbutton = new Button("Join a Host");
        joinbutton.setOnAction(event ->{ //Eveny handler for the action of clicking the button
            System.out.println("Joining your chat at: IP "+wIP+", PORT "+wPort);
            Client.connect(); // Calls the connect method of the Client.java file for joining an on-going server
            chatGI.clientwindow(); //Creates another windows for the client chat
        });
        joinbutton.setAlignment(Pos.CENTER);
        joinbutton.setTextAlignment(TextAlignment.CENTER);
        joinbutton.setPrefSize(70.0, 40.0);
        joinbutton.setWrapText(true);
            mainanchor.setTopAnchor(joinbutton, (double)rootSize[1]/2 +25);
            mainanchor.setLeftAnchor(joinbutton, 5.0);
        
        Button okbutton = new Button("OK");
        okbutton.setOnAction(event ->{ //Event handler for the action of clicking the button and saving any written IPs or Ports on the texfields
            help.setVisible(true); // Displays a message for the user to know that IP and Port on the textfields were saved
            String temptxt = enterIP.getText().trim(); //Gets the text on IP textfield and trims to remove any blank spaces
            if(temptxt.isEmpty()){
                wIP = "127.0.0.1"; //IF no written input or just a blank text it sets to default IP
            } else {
                wIP = temptxt; //ELSE there was actual input, it set the IP to the one written in the textfield
            }
            temptxt = enterPort.getText().trim();//Gets the text on Port textfield and trims to remove blank spaces
            if(temptxt.isEmpty()){
                wPort = 3000; //IF no writen input or just blank text it sets to default Port
            }else {
                wPort = Integer.parseInt(temptxt);//ELSE there was actual input, it casts the String input into integer and sets it as the port
            }
            System.out.println("Your IP("+wIP+") and port("+wPort+") were succesfully saved");
        });
        okbutton.setPrefSize(40.0, 20.0);
        okbutton.setTextAlignment(TextAlignment.CENTER);
            okbutton.setLayoutX(enterPort.getLayoutX()+90);
            mainanchor.setBottomAnchor(okbutton, 90.0);
        
    // Creation of the scene(window)
        //Addition of nodes to the containers
        mainanchor.getChildren().addAll(sidebar, topbar,joinbutton, startbutton, okbutton);
        rootPane.getChildren().addAll(mainanchor,headertxt, help, disIP, disPort, enterIP, enterPort);
        //Setting the stage
        primaryStage.setTitle("ChatSocket Hub");
        primaryStage.setResizable(false);
        Scene mainscene = new Scene(rootPane, rootSize[0], rootSize[1]);
        primaryStage.setScene(mainscene);
        primaryStage.show();
    }

/*Initializes a second window for displaying the client chat window
*/
    public static void clientwindow(){
    //Containers for the client windows and the nodes
        Pane subPane = new Pane();
        AnchorPane margins = new AnchorPane(); // Anchor for margins based coordinates
            clientlog.setPrefSize((double)subSize[0],(double)subSize[1]-48); // Size of the messages display
            clientlog.setEditable(false);
            margins.setTopAnchor(clientlog, 0.0);
            margins.setLeftAnchor(clientlog, 0.0);
        ScrollPane txtscroll = new ScrollPane(clientlog); //Creates a scrollbar for the meesages display

    //Nodes
        //     _______________________
        //____/ 1st order objects(Plain/non-interactive)
        Rectangle bottombar = new Rectangle((double)subSize[0]-28, 40.0,Color.DARKCYAN);
        bottombar.setArcHeight(20);
        bottombar.setArcWidth(20);
            bottombar.setLayoutX(13.0);
            bottombar.setLayoutY((double)subSize[1]-44);

        //     ________________________
        //____/ 2nd order objects(interactive:Textfield)
        TextField entermsg = new TextField();
        entermsg.setPromptText("Write something");
        entermsg.setPrefSize(bottombar.getWidth()-85, bottombar.getHeight()-10);
            entermsg.setLayoutX(bottombar.getLayoutX()+75);
            entermsg.setLayoutY(bottombar.getLayoutY()+5);

        //     _______________________
        //____/ 3rd order objects(interactive:buttons)
        Button sendbutton = new Button("SEND");
        sendbutton.setPrefSize(60.0, 25.0);
        sendbutton.setTextAlignment(TextAlignment.CENTER);
            sendbutton.setLayoutX(bottombar.getLayoutX()+7);
            sendbutton.setLayoutY(bottombar.getLayoutY()+7.5);
        sendbutton.setOnAction(event ->{ //Event handler for the action of clicking the button
            String msg = entermsg.getText().trim(); //Gets textfield input and trims to remove any blank spaces
            if(!msg.isEmpty()){
                clientlog.appendText("ClientSays: "+msg+"\n"); //IF there was actual input it writes it on the display
                try{
                    Client.sendMessage(msg); //Send via client
                    Server.receiveMessage(); //Calls the receival of the Server to update the message display
                } catch (IOException error){
                    error.getStackTrace();
                }
                entermsg.clear();
            }else {
                System.out.println("Message was empty"); //ELSE there was no input and doesnt send anything
            }
        });

    //Addition of the nodes to each layer
        margins.getChildren().addAll(txtscroll);
        subPane.getChildren().addAll(margins, bottombar, sendbutton, entermsg);
    //Stage setup
        Stage clientstage = new Stage();
        clientstage.setResizable(false);
        clientstage.setTitle("ClientChat");
        Scene nxtscene = new Scene(subPane, subSize[0], subSize[1]);
        clientstage.setScene(nxtscene);
        clientstage.setOnCloseRequest(event ->{ //Event Handler for closing the window
            clientlog.clear(); //Empties the message display so next client appears with no previous text
            try {
                Client.stop(); //Stops the current client socket
            } catch (IOException error) {
                error.printStackTrace();
            }
        });
        clientstage.show();
    }

/*Initializes a second window for displaying the server(host) chat window*/
    public static void serverwindow(){
    //Window containers
        Pane subPane = new Pane();
        AnchorPane margins = new AnchorPane(); //Anchor for margin based coordinates
            serverlog.setPrefSize((double)subSize[0],(double)subSize[1]-48); //Sets a size for the message display
            serverlog.setEditable(false);
            margins.setTopAnchor(serverlog, 0.0);
            margins.setLeftAnchor(serverlog, 0.0);
        ScrollPane txtscroll = new ScrollPane(serverlog); //Creates a scrollbar for the message display
    
    //Nodes
        //     _______________________
        //____/ 1st order objects(Plain/non-interactive)
        Rectangle bottombar = new Rectangle((double)subSize[0]-28, 40.0,Color.DARKCYAN);
        bottombar.setArcHeight(20);
        bottombar.setArcWidth(20);
            bottombar.setLayoutX(13.0);
            bottombar.setLayoutY((double)subSize[1]-44);
        //     ________________________
        //____/ 2nd order objects(interactive:Textfield)
        TextField entermsg = new TextField();
        entermsg.setPromptText("Write something");
        entermsg.setPrefSize(bottombar.getWidth()-85, bottombar.getHeight()-10);
            entermsg.setLayoutX(bottombar.getLayoutX()+75);
            entermsg.setLayoutY(bottombar.getLayoutY()+5);

        //     _______________________
        //____/ 3rd order objects(interactive:buttons)
        Button sendbutton = new Button("SEND");
        sendbutton.setPrefSize(60.0, 25.0);
        sendbutton.setTextAlignment(TextAlignment.CENTER);
            sendbutton.setLayoutX(bottombar.getLayoutX()+7);
            sendbutton.setLayoutY(bottombar.getLayoutY()+7.5);
        
        sendbutton.setOnAction(event ->{ //Event handler for the action of clicking the button and sending messages
            String msg = entermsg.getText().trim(); //Gets the textfield input and trims it to remove any blank spaces
            if(!msg.isEmpty()){
                serverlog.appendText("ServerSays: "+msg+"\n"); //IF there was actual input it displays in the local message display
                try{
                    Server.sendMessage(msg); //Sends message via Server(host)
                    Client.receiveMessage(); //Uses the receival of the message to update the message display
                } catch (IOException error){
                    error.getStackTrace();
                }
                entermsg.clear(); //Clears the textfield
            }else {
                System.out.println("Message was empty"); //ELSE there was no input or blank spaces and doesnt send anything
            }
        });
        
    //Addition of nodes to each layer
        margins.getChildren().addAll(txtscroll);
        subPane.getChildren().addAll(margins, bottombar, sendbutton, entermsg);
    //Stage setup
        Stage serverstage = new Stage();
        serverstage.setResizable(false);
        serverstage.setTitle("ServerChat");
        Scene nxtscene = new Scene(subPane,subSize[0], subSize[1]);
        serverstage.setScene(nxtscene);
        servertstage.setOnCloseRequest(event ->{ //Event handler for action of closing the window
            serverlog.clear(); //Empties the message display so that upon new server creation it appear clear
            try {
                Server.stop();// Stops the Server socket
            } catch (IOException error) {
                error.printStackTrace();
            }
        });
        serverstage.show();
    }
    
    /*Method for updating the respective TextArea of the ongoing app by displaying a message on request by Server or Client
    *@param arg message to display
    *@param type true for updating serverlog on Client input receive , false for updating clientlog on Server input receive
    */
    public static void updatelog(String arg, Boolean type){
        if (type==true){
            serverlog.appendText("ClientSays: "+arg+"\n");
        } else{
            clientlog.appendText("ServerSays: "+arg+"\n");
        }
    }

    /* Method for launching the app
    */
    public static void main(String[] args){
        launch(args);
    }
}
