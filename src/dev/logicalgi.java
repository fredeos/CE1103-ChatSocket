package src.dev;

import src.dev.Server;
import src.dev.Client;

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

public class logicalgi extends Application{
//Class variables for the program
    private int[] rootSize = {600,450};
    private String wIP="127.0.0.1";
    private int wPort=3000;
    private static int[] subSize = {650, 350};
    
//TextArea objects(nodes) for storing the messages during a chat session.
    private static TextArea clientlog = new TextArea();
    private static TextArea serverlog = new TextArea();

/*Initializes the main window(hub) for creating a joining chats*/
    @Override
    public void start(Stage primaryStage) throws Exception {
    //Main container(Pane) for the scene
        Pane rootPane = new Pane();

    //Other containers
        AnchorPane mainanchor = new AnchorPane();

    // 1st order objects(Generally non-interactive)
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

        Text inIP = new Text("Enter the IP address:");
        inIP.setStyle("-fx-font-size: 13pt");
        inIP.setFill(Color.BLACK);
        inIP.setLayoutX(150.0);
        inIP.setLayoutY((double)rootSize[1]/2-70);

        Text inPort = new Text("Enter the port to connect:");
        inPort.setStyle("-fx-font-size: 13pt");
        inPort.setFill(Color.BLACK);
        inPort.setLayoutX(inIP.getLayoutX()+35);
        inPort.setLayoutY(inIP.getLayoutY()+90);

        Text help = new Text("Your IP and Port were saved! Please select to Start or Join a server");
        help.setStyle("-fx-font-size: 8pt");
        help.setFill(Color.RED);
            help.setLayoutX((double)rootSize[0]/4);
            help.setLayoutY((double)rootSize[1]-40);
        help.setVisible(false);
    // 2nd order objects(Textfields, etc...)
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
        
    // 3rd order objects(Interactive; like buttons)
        Button startbutton = new Button("Start Hosting");
        startbutton.setOnAction(event ->{
            System.out.println("Starting your chat at: IP "+wIP+", PORT "+wPort);
            Server.startup();
            logicalgi.serverwindow();
        });
        startbutton.setAlignment(Pos.CENTER);
        startbutton.setTextAlignment(TextAlignment.CENTER);
        startbutton.setPrefSize(70.0, 40.0);
        startbutton.setWrapText(true);
            mainanchor.setTopAnchor(startbutton, (double)rootSize[1]/2 -50);
            mainanchor.setLeftAnchor(startbutton, 5.0);
        
        Button joinbutton = new Button("Join a Host");
        joinbutton.setOnAction(event ->{
            System.out.println("Joining your chat at: IP "+wIP+", PORT "+wPort);
            Client.connect();
            logicalgi.clientwindow();
        });
        joinbutton.setAlignment(Pos.CENTER);
        joinbutton.setTextAlignment(TextAlignment.CENTER);
        joinbutton.setPrefSize(70.0, 40.0);
        joinbutton.setWrapText(true);
            mainanchor.setTopAnchor(joinbutton, (double)rootSize[1]/2 +25);
            mainanchor.setLeftAnchor(joinbutton, 5.0);
        
        Button okbutton = new Button("OK");
        okbutton.setOnAction(event ->{
            help.setVisible(true);
            String temptxt = enterIP.getText().trim();
            if(temptxt.isEmpty()){
                wIP = "127.0.0.1";
            } else {
                wIP = temptxt;
            }
            temptxt = enterPort.getText().trim();
            if(temptxt.isEmpty()){
                wPort = 3000;
            }else {
                wPort = Integer.parseInt(temptxt);
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
        rootPane.getChildren().addAll(mainanchor,headertxt,help, inIP, inPort, enterIP, enterPort);
        //Setting the stage
        primaryStage.setTitle("ChatSocket Hub");
        Scene mainscene = new Scene(rootPane, rootSize[0], rootSize[1]);
        primaryStage.setScene(mainscene);
        primaryStage.show();
    }

/*Initializes a second window for displaying the client chat window*/
    public static void clientwindow(){
//Window containers
        Pane subPane = new Pane();
        AnchorPane margins = new AnchorPane();
            clientlog.setPrefSize((double)subSize[0],(double)subSize[1]);
            clientlog.setEditable(false);
            margins.setTopAnchor(clientlog, 0.0);
            margins.setLeftAnchor(clientlog, 0.0);
        ScrollPane txtscroll = new ScrollPane(clientlog);

    //Nodes
        //     _______________________
        //____/ 1st order objects(Plain/non-interactive)
        Rectangle bottombar = new Rectangle((double)subSize[0]-30, 40.0,Color.DARKCYAN);
        bottombar.setArcHeight(20);
        bottombar.setArcWidth(20);
            margins.setBottomAnchor(bottombar, 5.0);
            margins.setLeftAnchor(bottombar, 15.0);

        //     ________________________
        //____/ 2nd order objects(interactive:Textfield)
        TextField entermsg = new TextField();
        entermsg.setPromptText("Write something");
        entermsg.setPrefSize(bottombar.getWidth()-90, bottombar.getHeight()-10);
            margins.setBottomAnchor(entermsg, 10.0);
            margins.setLeftAnchor(entermsg, 95.0);

        //     _______________________
        //____/ 3rd order objects(interactive:buttons)
        Button sendbutton = new Button("SEND");
        sendbutton.setPrefSize(60.0, 25.0);
        sendbutton.setTextAlignment(TextAlignment.CENTER);
            margins.setBottomAnchor(sendbutton, 12.0);
            margins.setLeftAnchor(sendbutton, 27.5);
        sendbutton.setOnAction(event ->{
            String msg = entermsg.getText().trim();
            if(!msg.isEmpty()){
                clientlog.appendText("ClientSays: "+msg+"\n");
                System.out.println("Message sent("+msg+")");
                entermsg.clear();
            }else {
                System.out.println("Message was empty");
            }
        });


    //Addition of the nodes to each layer
        margins.getChildren().addAll(txtscroll, bottombar, sendbutton, entermsg);
        subPane.getChildren().addAll(margins);
    //Stage setup
        Stage clientstage = new Stage();
        clientstage.setTitle("ClientChat");
        Scene nxtscene = new Scene(subPane, subSize[0], subSize[1]);
        clientstage.setScene(nxtscene);
        clientstage.show();
    }

/*Initializes a second window for displaying the server(host) chat window*/
    public static void serverwindow(){
    //Window containers
        Pane subPane = new Pane();
        AnchorPane margins = new AnchorPane();
            serverlog.setPrefSize((double)subSize[0],(double)subSize[1]);
            serverlog.setEditable(false);
            margins.setTopAnchor(serverlog, 0.0);
            margins.setLeftAnchor(serverlog, 0.0);
        ScrollPane txtscroll = new ScrollPane(serverlog);

    //Nodes
        //     _______________________
        //____/ 1st order objects(Plain/non-interactive)
        Rectangle bottombar = new Rectangle((double)subSize[0]-30, 40.0,Color.DARKCYAN);
        bottombar.setArcHeight(20);
        bottombar.setArcWidth(20);
            margins.setBottomAnchor(bottombar, 5.0);
            margins.setLeftAnchor(bottombar, 15.0);
        //     ________________________
        //____/ 2nd order objects(interactive:Textfield)
        TextField entermsg = new TextField();
        entermsg.setPromptText("Write something");
        entermsg.setPrefSize(bottombar.getWidth()-90, bottombar.getHeight()-10);
            margins.setBottomAnchor(entermsg, 10.0);
            margins.setLeftAnchor(entermsg, 95.0);

        //     _______________________
        //____/ 3rd order objects(interactive:buttons)
        Button sendbutton = new Button("SEND");
        sendbutton.setPrefSize(60.0, 25.0);
        sendbutton.setTextAlignment(TextAlignment.CENTER);
            margins.setBottomAnchor(sendbutton, 12.0);
            margins.setLeftAnchor(sendbutton, 27.5);
        sendbutton.setOnAction(event ->{
            String msg = entermsg.getText().trim();
            if(!msg.isEmpty()){
                serverlog.appendText("ServerSays: "+msg+"\n");
                System.out.println("Message sent("+msg+")");
                entermsg.clear();
            }else {
                System.out.println("Message was empty");
            }
        });
    //Addition of nodes to each layer
        
        margins.getChildren().addAll(txtscroll, bottombar, sendbutton, entermsg);
        subPane.getChildren().addAll(margins);
    //Stage setup
        Stage serverstage = new Stage();
        serverstage.setTitle("ServerChat");
        Scene nxtscene = new Scene(subPane,subSize[0], subSize[1]);
        serverstage.setScene(nxtscene);
        serverstage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
