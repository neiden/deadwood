import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class InitController{


    /*public static void main(String[] args){
        launch(args);
    }*/
    public static ArrayList<String> playerList = new ArrayList<>();

    public static void openFile(File file) throws Exception {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    public static void run(){
            File file = new File("src/Deadwood-Free-Edition-Rules.pdf");
            Stage stage = new Stage();

            Scene game = new Scene(new Group());
            VBox startLayout = new VBox(50);
            Label startMessage = new Label("Welcome to Deadwood");

            Button start = new Button("Start");
            Button quit = new Button("Quit");
            Button rules = new Button("View Rules");

            startLayout.getChildren().addAll(startMessage, start, quit, rules);
            Scene startScene = new Scene(startLayout, 300, 300);

            start.setOnAction(e -> {
                initializeMenu();
                stage.close();
                                    });
            quit.setOnAction(e -> stage.close());
            rules.setOnAction(e -> {
                try {
                    openFile(file);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());//ex.printStackTrace();
                }
            });



            stage.setScene(startScene);
            stage.showAndWait();

    }

    private static int isValidPlayerNum(TextField textBox, String message, Label label){
        int rtrnNum = 0;
        try{
            rtrnNum = Integer.parseInt(message);
            if(rtrnNum < 2 || rtrnNum > 8){
                rtrnNum = -1;
                label.setText("Input must be between 2 - 8");
            }

            return rtrnNum;

        }catch(Exception e){
            label.setText(message + " isnt a number");
            return -1;
        }


    }

    private static void initializeMenu(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(450);

        VBox playerNameLayout = new VBox(10);
        Scene playerNameScene = new Scene(playerNameLayout);

        Label msg = new Label("Enter number of players (2 - 8)");
        TextField numPlayersBox = new TextField();
        Button submit = new Button("Submit");
        ArrayList<TextField> playerNames = new ArrayList<>();

        Label errorMsg = new Label();

        AtomicInteger numPlayers = new AtomicInteger(0);
        Button currButton = new Button("Submit");

        currButton.setOnAction(e ->{
            for (int i = 0; i < playerNames.size(); i++) {
                playerList.add(playerNames.get(i).getText());
            }
            window.close();
        });

        //creates valid number of players
        submit.setOnAction(e -> {
            numPlayers.set(isValidPlayerNum(numPlayersBox, numPlayersBox.getText(), errorMsg));
            if(numPlayers.get() != -1){
                for (int i = 0; i < numPlayers.get(); i++) {
                    TextField currText = new TextField();
                    playerNames.add(currText);
                    playerNameLayout.getChildren().addAll(new Label("Enter player " + (i + 1) + "'s name"), currText);
                }
                playerNameLayout.getChildren().add(currButton);
                window.setScene(playerNameScene);
                window.setMinHeight(playerNameLayout.getHeight() + 100);
                window.setMinWidth(playerNameLayout.getWidth() + 200);

            }
        });






        VBox playerNumLayout = new VBox(10);
        playerNumLayout.setPadding(new Insets(20, 20, 20, 20));
        playerNumLayout.getChildren().addAll(msg, numPlayersBox, submit, errorMsg);

        Scene playerNumberScene = new Scene(playerNumLayout);



        window.setScene(playerNumberScene);
        window.showAndWait();
        Main.playerList = playerList;



        //TODO: make the game start with user input generated
    }

}
