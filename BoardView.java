import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BoardView  {

    private Button work = new Button("Work");
    private Button act = new Button("Act");
    private Button rehearse = new Button("Rehearse");
    private Button move = new Button("Move");
    private Button upgrade = new Button("Upgrade");
    private Button endturn = new Button("End Turn");




    public BoardView(ArrayList<Player> playerList, ArrayList<Set> sets){
        createPlayers(playerList);
        createSets(sets);

        Stage mainStage = new Stage();
        BorderPane mainLayout = new BorderPane();

        //create HBox container with all player actions contained within, set to bottom of the mainLayout
        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(work, act, rehearse, move, upgrade, endturn);
        mainLayout.setBottom(hbox);

        //create board, set to center of mainLayout
        final ImageView imgView = new ImageView();
        Image image1 = new Image("board.jpg");
        imgView.setImage(image1);
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(1600);
        imgView.setFitWidth(1200);
        mainLayout.setCenter(imgView);

        Scene scene = new Scene(mainLayout);

        mainStage.setTitle("Deadwood");
        mainStage.setScene(scene);
        
    }



    public void createPlayers(ArrayList<Player> playerList){

    }

    public void createSets(ArrayList<Set> sets){

    }




}
