import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardView  {

    private Button work;
    private Button act ;
    private Button rehearse;
    private Button move;
    private Button upgrade;
    private Button endturn;
    HBox moveOptions;
    ArrayList<Button> neighbors;
    ArrayList<Button> optionsList;
    HBox playerOptions;
    BorderPane mainLayout;
    public Stage mainStage;



    public BoardView(ArrayList<Player> playerList, ArrayList<Set> sets){
        work = new Button("Work");
        act = new Button("Act");
        rehearse = new Button("Rehearse");
        move = new Button("Move");
        upgrade = new Button("Upgrade");
        endturn = new Button("End Turn");
        neighbors = new ArrayList<>();
        optionsList = new ArrayList<>();
        moveOptions = new HBox(5);


        createPlayers(playerList);
        createSets(sets);

        mainStage = new Stage();
        mainLayout = new BorderPane();

        //create HBox container with all player actions contained within, set to bottom of the mainLayout
        playerOptions = new HBox(5);
        playerOptions.getChildren().addAll(work, act, rehearse, move, upgrade, endturn);
        mainLayout.setBottom(playerOptions);

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


    public void addPlayerOptionListener(EventHandler<ActionEvent> listenForOptionButton){
        work.setOnAction(listenForOptionButton);
        act.setOnAction(listenForOptionButton);
        rehearse.setOnAction(listenForOptionButton);
        upgrade.setOnAction(listenForOptionButton);
        move.setOnAction(listenForOptionButton);
        endturn.setOnAction(listenForOptionButton);

    }


    public void createPlayers(ArrayList<Player> playerList){

    }

    public void validateOptions(ArrayList<String> validOptions){
        for (Node node : playerOptions.getChildren()){
            optionsList.add((Button)node);
        }

        for (int i = 0; i < optionsList.size(); i++) {
            optionsList.get(i).setDisable(true);
            for (int j = 0; j < validOptions.size(); j++) {
                if(optionsList.get(i).getText().equals(validOptions.get(j))){
                    optionsList.get(i).setDisable(false);
                }
            }
        }
    }

    public void createSets(ArrayList<Set> sets){

    }

    public void init(Player currPlayer, ArrayList<Set> sets){
        currPlayer.createOptionList();
        validateOptions(currPlayer.getOptions());


    }

    public void createMoveOptions(Set currSet){
        for (int i = 0; i < currSet.getNeighbors().size() ; i++) {
            neighbors.add(new Button(currSet.getNeighbors().get(i)));
            moveOptions.getChildren().add(neighbors.get(i));
        }
        //playerOptions.setVisible(false);
        mainLayout.setBottom(moveOptions);
        System.out.println(currSet.getNeighbors());
    }

    public void addMoveOptionListener(EventHandler<ActionEvent> listenForMoveButton){
        for (int i = 0; i < neighbors.size(); i++) {
            neighbors.get(i).setOnAction(listenForMoveButton);
        }
    }



    public void setVisible(boolean visible){
        if(visible){
            mainStage.show();
        }
        else{
            mainStage.close();
        }
    }

    public void update(ArrayList<Player> playerList, ArrayList<Set> sets){
        for (int i = 0; i < sets.size(); i++) {
            sets.get(i).getSceneCoordinate();
        }
        moveOptions.getChildren().clear();
        mainLayout.setBottom(playerOptions);
        neighbors.clear();
    }




}
