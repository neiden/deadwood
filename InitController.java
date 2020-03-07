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
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class InitController extends Application{


    public static void main(String[] args){
        launch(args);
    }

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

    @Override
    public void start(Stage stage){
            File file = new File("src/Deadwood-Free-Edition-Rules.pdf");

            Scene game = new Scene(new Group());
            VBox startLayout = new VBox(50);
            Label startMessage = new Label("Welcome to Deadwood");

            Button start = new Button("Start");
            Button quit = new Button("Quit");
            Button rules = new Button("View Rules");

            startLayout.getChildren().addAll(startMessage, start, quit, rules);
            Scene startScene = new Scene(startLayout, 300, 300);

            start.setOnAction(e -> {initializeMenu();
                                    });
            quit.setOnAction(e -> stage.close());
            rules.setOnAction(e -> {
                try {
                    openFile(file);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());//ex.printStackTrace();
                }
            });


            VBox layout = new VBox();


            final ImageView imgView = new ImageView();
            Image image1 = new Image("board.jpg");
            imgView.setImage(image1);
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(1600);
            imgView.setFitWidth(1200);

            layout.getChildren().addAll(imgView);
            game.setRoot(layout);

            stage.setTitle("Board");
            stage.setScene(startScene);
            stage.show();

    }

    private int isValidPlayerNum(TextField textBox, String message){
        int rtrnNum = 0;
        try{
            rtrnNum = Integer.parseInt(message);
            if(rtrnNum < 2 || rtrnNum > 8){
                rtrnNum = -1;
            }
            System.out.println("Input must be between 2 - 8");
            return rtrnNum;

        }catch(Exception e){
            System.out.println(message + " isnt a number");
            return -1;
        }


    }

    private void initializeMenu(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(450);

        Label msg = new Label("Enter number of players (2 - 8)");
        TextField numPlayersBox = new TextField();
        Button submit = new Button("Submit");

        AtomicInteger numPlayers = new AtomicInteger(0);

        //creates valid number of players
        submit.setOnAction(e -> {
            numPlayers.set(isValidPlayerNum(numPlayersBox, numPlayersBox.getText()));
            if(numPlayers.get() != -1){
                window.close();
            }
            System.out.println(numPlayers);
        });

        //TODO: create textfields for players to enter their names, should match number of players



        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(msg, numPlayersBox, submit);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();


        //TODO: make the game start with user input generated here

    }

}
