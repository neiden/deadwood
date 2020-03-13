
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import java.util.ArrayList;


public class InitController{


    private static Button two;
    private static Button three;
    private static Button four;
    private static Button five;
    private static Button six;
    private static Button seven;
    private static Button eight;
    private static int numPlayers;
    public static ArrayList<String> playerList = new ArrayList<>();
    public static Stage stage;

    //Helper method to open a given file with the users default pdf reader
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

    //Creates the title screen with the options to start, quit, and view the rules.
    //Runs until quit or the start process is finished.
    //Main takes the list of players before this process finishes and uses it to start game with appropriate data.
    public static void run(){
            File file = new File("src/Deadwood-Free-Edition-Rules.pdf");
            stage = new Stage();
        Scene game = new Scene(new Group());
        VBox startLayout = new VBox(50);
        ImageView deadwood = new ImageView();
        Image deadwood1 = new Image("/startscreen.png");
        deadwood.setImage(deadwood1);
        deadwood.setPreserveRatio(true);
        deadwood.setFitHeight(1600);
        deadwood.setFitWidth(1200);

        Button start = new Button("Start");
        Button quit = new Button("Quit");
        Button rules = new Button("View Rules");

        start.setStyle("-fx-background-color: #e4cab0;");
        start.setPrefWidth(200);
        start.setPrefHeight(60);

        quit.setStyle("-fx-background-color: #e4cab0;");
        quit.setPrefWidth(200);
        quit.setPrefHeight(60);

        rules.setStyle("-fx-background-color: #e4cab0;");
        rules.setPrefWidth(200);
        rules.setPrefHeight(60);

        startLayout.getChildren().addAll(deadwood,start, quit, rules);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setStyle("-fx-background-color: #b17246;");
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
                System.out.println(ex.getMessage());
            }
        });

        stage.setScene(startScene);
        stage.setFullScreen(true);
        stage.showAndWait();
    }

    //Initializes menu for users to input number of players and their names
    private static void initializeMenu(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setFullScreen(true);


        VBox playerNameLayout = new VBox(10);
        Scene playerNameScene = new Scene(playerNameLayout);
        Stage nameStage = new Stage();
        nameStage.setScene(playerNameScene);
        nameStage.setFullScreen(true);

        Label msg = new Label("How many players?");
        msg.setStyle("-fx-background-color: #e4cab0;");

        two = new Button("2");
        three = new Button("3");
        four = new Button("4");
        five = new Button("5");
        six = new Button("6");
        seven = new Button("7");
        eight = new Button("8");

        Button[] numButton = new Button[]{two, three, four, five, six, seven, eight};
        for (int i = 0; i < numButton.length; i++){
            numButton[i].setStyle("-fx-background-color: #e4cab0;");
            numButton[i].setPrefWidth(200);
            numButton[i].setPrefHeight(60);
        }
        ArrayList<TextField> playerNames = new ArrayList<>();

        Label errorMsg = new Label();

        Button currButton = new Button("Submit");
        currButton.setPrefWidth(200);
        currButton.setPrefHeight(60);
        currButton.setStyle("-fx-background-color: #e4cab0;");

        currButton.setOnAction(e ->{
            for (int i = 0; i < playerNames.size(); i++) {
                playerList.add(playerNames.get(i).getText());
            }
            window.close();
        });

        Button[] generatePlayerNames = new Button[]{two, three, four, five, six, seven, eight};
        for (int i = 0; i < generatePlayerNames.length; i++){
            int finalI = i;
            generatePlayerNames[i].setOnAction(e -> {
                numPlayers = finalI + 2;
                for (int j = 0; j < numPlayers; j++) {
                    TextField currText = new TextField();
                    playerNames.add(currText);
                    playerNameLayout.getChildren().addAll(new Label("Enter player " + (j + 1) + "'s name"), currText);
                }
                playerNameLayout.getChildren().add(currButton);
                playerNameLayout.setStyle("-fx-background-color: #b17246;");
                window.setScene(playerNameScene);
                window.setMinHeight(playerNameLayout.getHeight() + 100);
                window.setMinWidth(playerNameLayout.getWidth() + 200);
                window.setFullScreen(true);

            });
        }

        VBox playerNumLayout = new VBox(10);
        playerNumLayout.setPadding(new Insets(20, 20, 20, 20));
        playerNumLayout.getChildren().addAll(msg, two, three, four, five, six, seven, eight, errorMsg);
        playerNumLayout.setStyle("-fx-background-color: #b17246;");
        playerNumLayout.setAlignment(Pos.CENTER);

        Scene playerNumberScene = new Scene(playerNumLayout);



        window.setScene(playerNumberScene);
        window.showAndWait();
        Main.playerList = playerList;



    }

}
