import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    ArrayList<Button> workList;
    ArrayList<Button> upgradeList;
    HBox upgradeOptions;
    HBox playerOptions;
    HBox workOptions;
    BorderPane mainLayout;
    ImageView boardImg;

    VBox playerData;
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
        workList = new ArrayList<>();
        upgradeList = new ArrayList<>();
        workOptions = new HBox(5);
        moveOptions = new HBox(5);
        upgradeOptions = new HBox(5);


        createPlayers(playerList);
        createSets(sets);

        mainStage = new Stage();
        mainLayout = new BorderPane();

        //create HBox container with all player actions contained within, set to bottom of the mainLayout
        playerOptions = new HBox(5);
        playerOptions.getChildren().addAll(work, act, rehearse, move, upgrade, endturn);
        mainLayout.setBottom(playerOptions);

        //create board, set to center of mainLayout
        boardImg = new ImageView();
        Image image1 = new Image("board.jpg");
        boardImg.setImage(image1);
        boardImg.setPreserveRatio(true);
        boardImg.setFitHeight(1600);
        boardImg.setFitWidth(1200);
        mainLayout.getChildren().add(boardImg);


        Scene scene = new Scene(mainLayout);

        mainStage.setTitle("Deadwood");
        mainStage.setScene(scene);
        mainStage.setFullScreen(true);
        
    }


    public void addPlayerOptionListener(EventHandler<ActionEvent> listenForOptionButton){
        work.setOnAction(listenForOptionButton);
        act.setOnAction(listenForOptionButton);
        rehearse.setOnAction(listenForOptionButton);
        upgrade.setOnAction(listenForOptionButton);
        move.setOnAction(listenForOptionButton);
        endturn.setOnAction(listenForOptionButton);

    }

    public void initPlayerData(ArrayList<Player> playerList){
        playerData.getChildren().clear();
        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);
            VBox box = new VBox(1);
            Label name = new Label("Player : "  + currPlayer.name);
            Label rank = new Label("Rank: " + currPlayer.rank + "");
            Label dollars = new Label("Dollars: " + currPlayer.dollars + "");
            Label credits = new Label("Credits: " + currPlayer.credits + "");
            Label chips = new Label("Rehearsal Chips: " +currPlayer.practiceChips + "");
            Label role = new Label();
            if(currPlayer.role != null){
                role.setText("Role: " + currPlayer.role.getName());
            }
            else{
                role.setText("Role: Not working curently");
            }
            Label location = new Label("Location: " + currPlayer.currSet.name + "");
            box.getChildren().addAll(name, rank, dollars, credits, chips, role, location);
            box.setMaxHeight(.5);
            playerData.getChildren().add(box);
        }
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

    public void init(Player currPlayer, ArrayList<Set> sets, ArrayList<Player> playerList){
        currPlayer.createOptionList();
        validateOptions(currPlayer.getOptions());

        playerData = new VBox(5);
        initPlayerData(playerList);
        mainLayout.setRight(playerData);

        for (int i = 0; i < sets.size(); i++) {
            if(sets.get(i).getImageView() != null){
                mainLayout.getChildren().add(sets.get(i).getImageView());
            }
            if(sets.get(i).getShotsView() != null){
                for (int j = 0; j < sets.get(i).getShotsView().size(); j++) {
                    mainLayout.getChildren().add(sets.get(i).getShotsView().get(j));
                }
            }
        }
        //mainStage.setFullScreen(true);
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

    public void createWorkOptions(Player currPlayer){
        ArrayList<Role> availableRoles = currPlayer.getAvailableRoles();
        for (int i = 0; i < availableRoles.size(); i++) {
            String name = availableRoles.get(i).getName();
            String rank = availableRoles.get(i).getLevel() + "";

            Button currRole = new Button();
            currRole.setText(name + ", " + rank );
            workList.add(currRole);
            workOptions.getChildren().add(currRole);
        }
        //playerOptions.setVisible(false);
        mainLayout.setBottom(workOptions);
        System.out.println(workList);
    }

    public void createUpgradeOptions(Player currPlayer){
        ArrayList<Upgrade> availableUpgrades = currPlayer.getAvailableUpgrades();
        for (int i = 0; i < availableUpgrades.size(); i++) {
            String rank = "Level: " + availableUpgrades.get(i).level + ", ";
            String dollars = "Dollar Cost: " + availableUpgrades.get(i).dollars + ", ";
            String credits = "Credit Cost: " + availableUpgrades.get(i).credits + "";

            Button currUpgrade = new Button();
            currUpgrade.setText(rank + dollars + credits);
            upgradeList.add(currUpgrade);
            upgradeOptions.getChildren().add(currUpgrade);
        }

        mainLayout.setBottom(upgradeOptions);
        System.out.println(upgradeList);

    }

    public void addUpgradeOptionListener(EventHandler<ActionEvent> listenForUpgradeButton){
        for (int i = 0; i < workList.size(); i++) {
            upgradeList.get(i).setOnAction(listenForUpgradeButton);
        }
    }


    public void addWorkOptionListener(EventHandler<ActionEvent> listenForWorkButton){
        for (int i = 0; i < workList.size(); i++) {
            workList.get(i).setOnAction(listenForWorkButton);
        }
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
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(boardImg);
        for (int i = 0; i < sets.size(); i++) {
            if(sets.get(i).getImageView() != null){
                mainLayout.getChildren().add(sets.get(i).getImageView());
            }
            if(sets.get(i).getShotsView() != null){
                for (int j = 0; j < sets.get(i).getShotsRemaining(); j++) {
                    mainLayout.getChildren().add(sets.get(i).getShotsView().get(j));
                }
            }
        //TODO: implement displaying players location
    }
        playerData = new VBox(5);
        initPlayerData(playerList);
        mainLayout.setRight(playerData);




        upgradeOptions.getChildren().clear();
        moveOptions.getChildren().clear();
        workOptions.getChildren().clear();
        mainLayout.setBottom(playerOptions);


        upgradeList.clear();
        optionsList.clear();
        workList.clear();
        neighbors.clear();
    }




}
