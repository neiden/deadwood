import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class GameModel {
    private Player currPlayer;
    private ArrayList<Player> playerList;
    private Board board;
    private Bank bank;
    private Scanner scanner;
    private int dayNumber;
    private int initialDay;
    private boolean running;
    private int numPlayers;
    private ArrayList<Scene> scenes;
    private ArrayList<String> devOptions;


    public GameModel(ArrayList<Set> sets, ArrayList<Scene> scenes){
        board = new Board(sets);
        scanner = new Scanner(System.in);
        playerList = new ArrayList<>();
        running = true;
        this.scenes = scenes;
        initialDay = 0;
        dayNumber = 0;
        devOptions = new ArrayList<>();
        bank = new Bank();
    }


    //Initializes the game with the appropriate player names
    public void init(ArrayList<String> playerNames){

        devOptions.add("locations");
        devOptions.add("playerInfo");
        devOptions.add("setLocation");
        devOptions.add("setRank");
        devOptions.add("setCurrency");
        devOptions.add("setRemainingScenes");
        devOptions.add("setDay");
        devOptions.add("infoSet");

        int numPlayers = playerNames.size();


        board.setNumPlayers(numPlayers);
        for(int i = 0; i < numPlayers; i ++){
            playerList.add(new Player(playerNames.get(i), board.getSet("trailer"), board, (i+1)));
        }

        setDayRules(numPlayers);
        board.setScenes(scenes);
        currPlayer = playerList.get(0);
        currPlayer.setTurn(true);
    }

    //Helper class to sort players based on who has more points
    class SortbyRoll implements Comparator<Player> {

        public int compare(Player a, Player b){
            return b.points - a.points;
        }
    }
    //Auxiliary method to determine if a scene should be closed or not
    public void sceneClosureCheck(){
        if(currPlayer.currSet.getCurrScene() != null){
            if(currPlayer.currSet.getShotsRemaining() < 1){//If scene completed, clean up variables
                bank.bonusMoneyDistribution(playerList, currPlayer.currSet);
                killScene(currPlayer.currSet.getCurrScene());
                currPlayer.currSet.setCurrScene(null);
            }
        }
    }
    //Method that ends the current turn, setting all of the values to their proper positions.
    //Return true if the last day has just been completed, false otherwise
    public boolean endCurrTurn(){
        boolean end = false;
        currPlayer.setTurn(false);
        if (playerList.indexOf(currPlayer) == playerList.size() - 1) {
            currPlayer = playerList.get(0);
        } else {
            currPlayer = playerList.get(playerList.indexOf(currPlayer) + 1);
        }

        sceneClosureCheck();

        currPlayer.setTurn(true);
        currPlayer.setMoved(false);
        currPlayer.setUpgraded(false);

        currPlayer.createOptionList();


        if (board.scenesRemaining() < 2) {
            if(endDay()){
                end = true;
            }
            if(dayNumber > 0){
                startDay();
            }
        }

        return end;
    }
    //Creates a list of Player objects sorted in order of highest points
    public ArrayList<Player> calcScore(){
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).points = playerList.get(i).credits + playerList.get(i).dollars + (5 * playerList.get(i).rank);
        }
        Collections.sort(playerList, new SortbyRoll());

        return playerList;
    }


    //Cleans up a scene, deleting itself from the set it was related to and cleaning all affected Player objects
    private void killScene(Scene scene){
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).currSet.getCurrScene() != null) {
                if (playerList.get(i).currSet.getCurrScene().name.equals(scene.name) && (playerList.get(i).role != null)) {
                    playerList.get(i).currSet.setRoleActor(playerList.get(i).role.getName(), null);
                    playerList.get(i).practiceChips = 0;
                    if(i == playerList.size() - 1) {
                        board.getSet(playerList.get(i).currSet.name).setCurrScene(null);
                    }
                    playerList.get(i).role = null;
                }
            }
        }
    }

    private void startDay(){
        board.setScenes(scenes);

    }
    //Ends the day, placing all players back at the trailer and resetting the board
    private boolean endDay(){
        dayNumber--;
        if(dayNumber != 0) {
            for (int i = 0; i < playerList.size(); i++) {
                playerList.get(i).currSet = board.getSet("trailer");
                playerList.get(i).role = null;
                playerList.get(i).practiceChips = 0;
            }
            board.resetShots();
            return false;
        }
        else{
            return true;
        }
    }


    //Sets the rules based on how many people are playing
    private void setDayRules(int numPlayers) {

            switch (numPlayers) {
                case 2:
                case 3:
                    dayNumber = 3;
                    initialDay = 3;
                    break;
                case 4:
                    dayNumber = 4;
                    initialDay = 4;
                    break;
                case 5:
                    dayNumber = 4;
                    initialDay = 4;
                    for (int i = 0; i < playerList.size(); i++) {
                        playerList.get(i).setCredits(2);
                    }
                    break;
                case 6:
                    dayNumber = 4;
                    initialDay = 4;
                    for (int i = 0; i < playerList.size(); i++) {
                        playerList.get(i).setCredits(4);
                    }
                    break;
                case 7:
                case 8:
                    dayNumber = 4;
                    initialDay = 4;
                    for (int i = 0; i < playerList.size(); i++) {
                        playerList.get(i).setRank(2);
                        playerList.get(i).updatePlayerIcon();
                    }
                    break;
            }
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

    public ArrayList<Set> getSets(){
        return board.sets;
    }



}
