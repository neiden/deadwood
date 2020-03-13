
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BoardView  {

    private Button work;
    private Button act ;
    private Button rehearse;
    private Button move;
    private Button upgrade;
    private Button endturn;
    ArrayList<Button> neighbors;
    ArrayList<Button> optionsList;
    ArrayList<Button> workList;
    ArrayList<Button> upgradeList;
    VBox upgradeOptions;
    VBox playerOptions;
    VBox workOptions;
    VBox moveOptions;
    BorderPane mainLayout;
    ImageView boardImg;

    HBox playerData;
    public Stage mainStage;


    //constructor initializes all the necessary components
    public BoardView(){
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
        workOptions = new VBox(5);
        moveOptions = new VBox(5);
        upgradeOptions = new VBox(5);

        work.setStyle("-fx-background-color: #e4cab0;");
        work.setPrefWidth(200);
        work.setPrefHeight(60);

        act.setStyle("-fx-background-color: #e4cab0;");
        act.setPrefWidth(200);
        act.setPrefHeight(60);

        rehearse.setStyle("-fx-background-color: #e4cab0;");
        rehearse.setPrefWidth(200);
        rehearse.setPrefHeight(60);

        move.setStyle("-fx-background-color: #e4cab0;");
        move.setPrefWidth(200);
        move.setPrefHeight(60);

        upgrade.setStyle("-fx-background-color: #e4cab0;");
        upgrade.setPrefWidth(200);
        upgrade.setPrefHeight(60);

        endturn.setStyle("-fx-background-color: #e4cab0;");
        endturn.setPrefWidth(200);
        endturn.setPrefHeight(60);

        mainStage = new Stage();
        mainLayout = new BorderPane();


        //create HBox container with all player actions contained within, set to bottom of the mainLayout
        playerOptions = new VBox(5);
        playerOptions.getChildren().addAll(work, act, rehearse, move, upgrade, endturn);
        playerOptions.setSpacing(20);
        mainLayout.setRight(playerOptions);

        //create board, set to center of mainLayout
        boardImg = new ImageView();
        Image image1 = new Image("board.jpg");
        boardImg.setImage(image1);
        boardImg.setPreserveRatio(true);
        boardImg.setFitHeight(1600);
        boardImg.setFitWidth(1200);
        mainLayout.getChildren().add(boardImg);
        mainLayout.setStyle("-fx-background-color: #b17246;");


        Scene scene = new Scene(mainLayout);

        mainStage.setTitle("Deadwood");
        mainStage.setScene(scene);
        mainStage.setFullScreen(true);
        
    }

    //Allows the Controller class to define the behavior of each button;
    //waits for an event to occur
    public void addPlayerOptionListener(EventHandler<ActionEvent> listenForOptionButton){
        work.setOnAction(listenForOptionButton);
        act.setOnAction(listenForOptionButton);
        rehearse.setOnAction(listenForOptionButton);
        upgrade.setOnAction(listenForOptionButton);
        move.setOnAction(listenForOptionButton);
        endturn.setOnAction(listenForOptionButton);
    }

    //Creates the player data boxes at the bottom of the screen.
    //This method is called on each update to accurately reflect state of game.
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
            box.setAlignment(Pos.BOTTOM_CENTER);
            box.setStyle("-fx-background-color: #e4cab0;");
            if(playerList.get(i).getTurn()){
                String hexCode = playerList.get(i).getHexCode();
                box.setStyle("-fx-background-color: #" + hexCode + ";");
            }
            playerData.getChildren().add(box);
        }
    }

    //Sets the player option buttons to be correspondingly grey or available.
    //Compares the valid options from the Player class to determine this.
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

    //Initializes the board with correct graphical components.
    //Very similar to update, but doesn't need to clear anything and creates the player options and playerIcon first.
    public void init(Player currPlayer, ArrayList<Set> sets, ArrayList<Player> playerList){
        currPlayer.createOptionList();
        currPlayer.updatePlayerIcon();
        validateOptions(currPlayer.getOptions());

        playerData = new HBox(5);
        initPlayerData(playerList);
        mainLayout.setBottom(playerData);

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
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).getImageView() != null){
                mainLayout.getChildren().add(playerList.get(i).getImageView());
            }
        }
    }

    //Creates a set of move buttons displaying the neighbors of the current set
    public void createMoveOptions(Set currSet){
        for (int i = 0; i < currSet.getNeighbors().size() ; i++) {
            Button currButton = new Button();
            currButton.setText(currSet.getNeighbors().get(i));
            currButton.setStyle("-fx-background-color: #e4cab0;");
            currButton.setPrefWidth(200);
            currButton.setPrefHeight(60);

            neighbors.add(currButton);
            moveOptions.getChildren().add(neighbors.get(i));
        }
        mainLayout.setRight(moveOptions);
        moveOptions.setSpacing(20);
    }
    //Creates set of buttons representing the available roles a player can take
    public void createWorkOptions(Player currPlayer){
        ArrayList<Role> availableRoles = currPlayer.getAvailableRoles();
        for (int i = 0; i < availableRoles.size(); i++) {
            String name = availableRoles.get(i).getName();
            String rank = availableRoles.get(i).getLevel() + "";

            Button currRole = new Button();
            currRole.setText(name + ", " + rank );

            currRole.setStyle("-fx-background-color: #e4cab0;");
            currRole.setPrefWidth(200);
            currRole.setPrefHeight(60);

            workList.add(currRole);
            workOptions.getChildren().add(currRole);
        }
        workOptions.setSpacing(20);
        mainLayout.setRight(workOptions);
    }
    //Creates set of available upgrade options that the user can take
    public void createUpgradeOptions(Player currPlayer){
        ArrayList<Upgrade> availableUpgrades = currPlayer.getAvailableUpgrades();
        for (int i = 0; i < availableUpgrades.size(); i++) {
            String rank = "Level: " + availableUpgrades.get(i).level + ", ";
            String dollars = "Dollar Cost: " + availableUpgrades.get(i).dollars + ", ";
            String credits = "Credit Cost: " + availableUpgrades.get(i).credits + "";

            Button currUpgrade = new Button();

            currUpgrade.setStyle("-fx-background-color: #e4cab0;");
            currUpgrade.setPrefWidth(200);
            currUpgrade.setPrefHeight(60);
            currUpgrade.setText(rank + dollars + credits);
            upgradeList.add(currUpgrade);
            upgradeOptions.getChildren().add(currUpgrade);
        }
        upgradeOptions.setSpacing(20);
        mainLayout.setRight(upgradeOptions);

    }

    //Sets every upgrade button to have a behavior defined in the Controller class
    public void addUpgradeOptionListener(EventHandler<ActionEvent> listenForUpgradeButton){
        for (int i = 0; i < upgradeList.size(); i++) {
            upgradeList.get(i).setOnAction(listenForUpgradeButton);
        }
    }

    // " " " "
    public void addWorkOptionListener(EventHandler<ActionEvent> listenForWorkButton){
        for (int i = 0; i < workList.size(); i++) {
            workList.get(i).setOnAction(listenForWorkButton);
        }
    }


    // " " " "
    public void addMoveOptionListener(EventHandler<ActionEvent> listenForMoveButton){
        for (int i = 0; i < neighbors.size(); i++) {
            neighbors.get(i).setOnAction(listenForMoveButton);
        }
    }


    //Sets the stage as showing or not
    public void setVisible(boolean visible){
        if(visible){
            mainStage.show();
        }
        else{
            mainStage.close();
        }
    }

    //The main graphical method that wipes the screen clear and then repopulates it with correct components
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
    }
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).updatePlayerIcon();
            if(playerList.get(i).getImageView() != null){
                mainLayout.getChildren().add(playerList.get(i).getImageView());
            }
        }

        playerData = new HBox(5);
        initPlayerData(playerList);
        mainLayout.setBottom(playerData);




        upgradeOptions.getChildren().clear();
        moveOptions.getChildren().clear();
        workOptions.getChildren().clear();
        mainLayout.setRight(playerOptions);


        upgradeList.clear();
        optionsList.clear();
        workList.clear();
        neighbors.clear();
    }
    //Creates end screen displaying the players standings and scores
    public void setEndScreen(ArrayList<Player> players){
        BorderPane endLayout = new BorderPane();
        endLayout.setStyle("-fx-background-color: #e4cab0;");
        VBox endBox = new VBox(5);
        for (int i = 0; i < players.size(); i++) {
            Label score = new Label((i+1) + "st: " + players.get(i).name + " with " + players.get(i).points);
            endBox.getChildren().add(score);
        }

        endBox.setStyle("-fx-background-color: #e4cab0;");
        endLayout.setCenter(endBox);
        Scene endScene = new Scene(endLayout);
        mainStage.setTitle("Game Over");
        mainStage.setScene(endScene);
        mainStage.setFullScreen(true);
        mainStage.show();
    }




}
