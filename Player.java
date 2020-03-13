import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Player {

    public String name;
    public int dollars;
    public int credits;
    public int rank;
    public int practiceChips;
    public Role role;
    public Set currSet;
    public int points;
    private boolean hasMoved;
    private boolean hasUpgraded;
    private boolean isTurn;
    private Board board;
    private Scanner scanner;
    private ImageView imgView;
    private Image icon;
    private int playerNum;
    private String playerIcon;
    String hexCode;
    public ArrayList<String> options;


    public Player(String name, Set currSet, Board board, int playerNum){
        this.playerNum = playerNum;
        createPlayerIcon(playerNum);
        options = new ArrayList<>();
        this.name = name;
        dollars = 0;
        credits = 0;
        rank = 1;
        role = null;
        practiceChips = 0;
        this.currSet = currSet;
        this.board = board;
        scanner = new Scanner(System.in);
        hasMoved = false;
        hasUpgraded = false;
        points = 0;
        imgView = null;
        icon = new Image("dice/" + playerIcon + rank + ".png");
        updatePlayerIcon();
    }
    //Updates the players icon to correctly match their position and rank.
    public void updatePlayerIcon(){
        int[] coordinates = currSet.getSceneCoordinate();
        icon = new Image("dice/" + playerIcon + rank + ".png");
        imgView = new ImageView();
        imgView.setImage(icon);
        if(role == null){
            imgView.setX(coordinates[0] + (playerNum * 20));
            imgView.setY(coordinates[1]);
            imgView.setFitHeight(44);
            imgView.setFitWidth(45);
        }
        else{
            int[] roleCoordinates = role.getCoordinates();
            if(role.getType().equals("main")) {
                imgView.setX(coordinates[0] + roleCoordinates[0] -1);
                imgView.setY(coordinates[1] + roleCoordinates[1]);
            }
            else{
                imgView.setX(roleCoordinates[0]);
                imgView.setY(roleCoordinates[1]);
            }
            imgView.setFitHeight(44);
            imgView.setFitWidth(45);
        }

    }
    //Creates the icon file name and hexCode from the player number
    private void createPlayerIcon(int playerNum){
        switch(playerNum){
            case 1:
                playerIcon = "b";
                hexCode = "005482";
                break;
            case 2:
                playerIcon = "c";
                hexCode = "00FFFF";
                break;
            case 3:
                playerIcon = "g";
                hexCode = "33FF33";
                break;
            case 4:
                playerIcon = "o";
                hexCode = "FF8000";
                break;
            case 5:
                playerIcon = "p";
                hexCode = "FFCCCC";
                break;
            case 6:
                playerIcon = "r";
                hexCode = "FF0000";
                break;
            case 7:
                playerIcon = "v";
                hexCode = "CC00CC";
                break;
            case 8:
                playerIcon = "w";
                hexCode = "FFFFFF";
                break;
        }
    }

    public String getHexCode(){
        return hexCode;
    }
    //Main logic in determining the options a player has at any given moment. Will always offer end turn.
    public void createOptionList(){
        options.clear();


        if(isTurn) {
            if (role == null) {
                if (currSet.hasScene()) {
                    ArrayList<Role> roles = currSet.getAvailableRoles(rank);
                    if (roles.size() > 0) {
                        options.add("Work");
                    }
                }
                if (!hasMoved) {
                    options.add("Move");
                }
            } else {
                if (practiceChips < currSet.getCurrScene().getBudget() - 1) {
                    options.add("Rehearse");
                }
                options.add("Act");
            }
            if (currSet.name.equals("office")) {
                if (!hasUpgraded && getAvailableUpgrades().size() > 0) {
                    options.add("Upgrade");
                }
            }
        }
        options.add("End Turn");
    }

    public void setMoved(boolean moved){
        hasMoved = moved;
    }

    public void setUpgraded(boolean upgraded){
        hasUpgraded = upgraded;
    }

    public ImageView getImageView(){
        return imgView;
    }

    public boolean getTurn(){
        return isTurn;
    }

    public void setTurn(boolean turn){
        isTurn = turn;
    }


    public void move(String input){
                currSet = board.getSet(input);
                currSet.setShowing(true);
                hasMoved = true;
    }

    //Given an input, sets the players role to the desired role.
    public void startWork(String input){
        ArrayList<Role> availableRoles = currSet.getAvailableRoles(rank);
        Iterator<Role> itr = availableRoles.iterator();
        while(itr.hasNext()){
            Role role = itr.next();
            if(role.getLevel() > rank){
                itr.remove();
            }
        }

        for (int i = 0; i < availableRoles.size(); i++) {
            if (availableRoles.get(i).getName().equals(input)) {
                role = availableRoles.get(i);
                if(role.getType().equals("extra")){
                    currSet.setRoleActor(role.getName(), this);
                }
                else{
                    currSet.getCurrScene().setRoleActor(role.getName(), this);
                }
            }
        }
        isTurn = false;

    }

    //Sets a random number from 1 to 6 against the budget of the scene and compares its value.
    public void act(){
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;

        if(roll + practiceChips >= currSet.getCurrScene().getBudget()){
            if(role.getType().equals("extra")){
                dollars++;
                credits++;
            }
            else{
                credits += 2;
            }
            currSet.decrementShot();
        }
        else{
            if(role.getType().equals("extra")){
                dollars++;
            }
        }

        isTurn = false;
    }

    //Given an input, upgrades a player based on their desired rank.
    //Given an equal number of dollars and credits, always defaults dollars to be taken first.
    public void upgrade(String input){
        String[] commaSplit = input.split(",");
        String strRank  = commaSplit[0].substring(7);
        int rankOption = Integer.parseInt(strRank);
        ArrayList<Upgrade> upgradeOptions = new ArrayList<>();
        for (int i = 0; i < currSet.getUpgrades().size(); i++) {
            if(currSet.getUpgrades().get(i).level > rank){
                if(currSet.getUpgrades().get(i).credits <= credits || currSet.getUpgrades().get(i).dollars <= dollars){
                    upgradeOptions.add(currSet.getUpgrades().get(i));
                }
            }
        }
        if(upgradeOptions.size() > 0) {
                Upgrade upgrade = null;
                for (int i = 0; i < upgradeOptions.size(); i++) {
                    if (upgradeOptions.get(i).level == rankOption) {
                        upgrade = upgradeOptions.get(i);
                    }
                }
                    rank = rankOption;

                    if(dollars >= upgrade.dollars){
                        dollars -= upgrade.dollars;
                    }
                    else{
                        credits -= upgrade.credits;
                    }


                    }
        hasUpgraded = true;
        }


    public void rehearse(){
        practiceChips++;
        isTurn = false;
    }
    //Returns list of the available upgrade options
    public ArrayList<Upgrade> getAvailableUpgrades(){
        ArrayList<Upgrade> upgradeOptions = new ArrayList<>();
        for (int i = 0; i < currSet.getUpgrades().size(); i++) {
            if(currSet.getUpgrades().get(i).level > rank){
                if(currSet.getUpgrades().get(i).credits <= credits || currSet.getUpgrades().get(i).dollars <= dollars){
                    upgradeOptions.add(currSet.getUpgrades().get(i));
                }
            }
        }

        return upgradeOptions;
    }
    //Returns list of available roles
    public ArrayList<Role> getAvailableRoles(){
        ArrayList<Role> availableRoles = currSet.getAvailableRoles(rank);
        Iterator<Role> itr = availableRoles.iterator();
        while(itr.hasNext()){
            Role role = itr.next();
            if(role.getLevel() > rank){
                itr.remove();
            }
        }

        return availableRoles;
    }


    public ArrayList<String> getOptions(){
        return options;
    }



    public void setCredits(int credits){
        this.credits = credits;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public String toString(){

        return "Name: " + name + " is at " + currSet.name;
    }



}
