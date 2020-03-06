import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Game {
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

    public Game(ArrayList<Set> sets, ArrayList<Scene> scenes){
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

    public void init(){

        devOptions.add("locations");
        devOptions.add("playerInfo");
        devOptions.add("setLocation");
        devOptions.add("setRank");
        devOptions.add("setCurrency");
        devOptions.add("setRemainingScenes");
        devOptions.add("setDay");
        devOptions.add("infoSet");

        boolean running = true;
        System.out.println("Welcome to Deadwood!");
        System.out.print("Enter number of players: ");
        numPlayers = scanner.nextInt();

        System.out.println();

        while(running) {
            if (numPlayers > 1 && numPlayers < 9) {
                board.setNumPlayers(numPlayers);
                for(int i = 0; i < numPlayers; i ++){
                    System.out.print("Enter Player " + (i+1) + "'s name: ");
                    playerList.add(new Player(scanner.next(), board.getSet("trailer"), board));
                    System.out.println();
                }

                setDayRules(numPlayers);
                board.setScenes(scenes);
                currPlayer = playerList.get(0);
                currPlayer.setTurn(true);
                running = false;

            } else {
                System.out.println("Invalid number of players!");
                System.out.print("Enter number of players: ");
                numPlayers = scanner.nextInt();
                System.out.println();
            }
        }
    }

    public void run(){
        boolean notDevSelected = true;
        if(dayNumber < 1){
            calcScore();
            System.out.println("Game over!");
            running = false;
        }else {

            while (currPlayer.getTurn() && notDevSelected) {
                System.out.println("It's " + currPlayer.name + "'s turn." +
                        "\nAvailable moves: ");
                currPlayer.createOptionList();

                System.out.println("Player Options: " + currPlayer.getOptions());
                System.out.println("Developer Options: " + devOptions);

                String input = scanner.next();

                if (!devOptions(input)) {
                    currPlayer.validateAction(input);
                }else{
                    notDevSelected = false;
                }
            }
            if(notDevSelected) {
                if(currPlayer.currSet.getCurrScene() != null){
                    if(currPlayer.currSet.getShotsRemaining() < 1){//If scene completed, clean up variables
                        bank.bonusMoneyDistribution(playerList, currPlayer.currSet);
                        killScene(currPlayer.currSet.getCurrScene());
                        currPlayer.currSet.setCurrScene(null);
                    }
                }

                if (playerList.indexOf(currPlayer) == playerList.size() - 1) {
                    currPlayer = playerList.get(0);
                } else {
                    currPlayer = playerList.get(playerList.indexOf(currPlayer) + 1);
                }

                currPlayer.setTurn(true);
                currPlayer.setMoved(false);
                currPlayer.setUpgraded(false);

                if (board.scenesRemaining() < 2) {
                    endDay();
                    if(dayNumber > 0){
                        startDay();
                    }
                }
            }

        }

    }

    class SortbyRoll implements Comparator<Player> {

        public int compare(Player a, Player b){
            return b.points - a.points;
        }
    }

    private void calcScore(){
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).points = playerList.get(i).credits + playerList.get(i).dollars + (5 * playerList.get(i).rank);
        }
        Collections.sort(playerList, new SortbyRoll());
        System.out.println("Final Scores:");
        for (int i = 0; i < playerList.size(); i++) {
            System.out.println("\t"+(i + 1) + ".] " + playerList.get(i).name + ", " + playerList.get(i).points + " pts.");
        }
    }

    private void killScene(Scene scene){
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).currSet.getCurrScene() != null) {
                if (playerList.get(i).currSet.getCurrScene().name.equals(scene.name)) {
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

    private void location(){
        for (int i = 0; i < playerList.size(); i++) {
            System.out.print(playerList.get(i));
            if(playerList.get(i).equals(currPlayer)){
                System.out.print(" <--- Active Player");
            }
            System.out.println();
        }
    }

    private void setLocation(){
        System.out.println("Where would you like to move: ");
        scanner.nextLine();
        String input = scanner.nextLine();
        currPlayer.currSet = board.getSet(input);
        System.out.println(currPlayer.name + " teleported to " + input);
    }

    private void setRank(){
        System.out.println("What value of rank would you like: ");
        int input = scanner.nextInt();
        currPlayer.rank = input;
        System.out.println(currPlayer.name + "'s rank set to " + input);
    }
    
    private void setRemainingScenes(){
        System.out.println("How many scenes would you like remaining?");
        int input = scanner.nextInt();
        for (int i = 0; i < board.sets.size() - (2 +input); i++) {
            if(board.sets.get(i).hasScene()){
                board.sets.get(i).setCurrScene(null);
            }
        }
        for (int i = 0; i < board.sets.size(); i++) {
            if(board.sets.get(i).hasScene()){
                System.out.println(board.sets.get(i).name + " still has a scene.");
            }
        }
        System.out.println(input + " scenes remaining.");
    }
    

    private void setCurrency(){
        System.out.println("Enter desired dollars: ");
        int dollars = scanner.nextInt();
        System.out.println("Enter desired credits: ");
        int credits = scanner.nextInt();
        currPlayer.credits = credits;
        currPlayer.dollars = dollars;
        System.out.println("Set " + currPlayer.name + "'s credits to " + credits + " and dollars to " + dollars);
    }

    private void playerInfo(){
            String roleName = "false";
            String sceneName = "false";
            if(currPlayer.role != null){
                roleName = currPlayer.role.getName();
            }
            if(currPlayer.currSet.getCurrScene() != null){
                sceneName = currPlayer.currSet.getCurrScene().name;
            }

            System.out.println("Player Info:\n----------------------------" +
                    "\nName: " + currPlayer.name + "\nLocation: " + currPlayer.currSet.name + "\nScene on Set: " +
                    sceneName +  "\nWorking: " + roleName + "\nCredits: " +
                    currPlayer.credits + "\nDollars: " + currPlayer.dollars + "\nRank: " + currPlayer.rank + "\nPractice Chips: " + currPlayer.practiceChips +
                    "\n----------------------------");

    }

    private void setDay(){
        System.out.println("Enter day: ");
        int input = scanner.nextInt();
        dayNumber = input;
        System.out.println("Day set to " + dayNumber);
    }

    private void infoSet(){
        System.out.println(currPlayer.currSet);
    }


    private boolean devOptions(String input){
        switch(input){
            case "locations":
                location();
                return true;
            case "playerInfo":
                playerInfo();
                return true;
            case "setCurrency":
                setCurrency();
                return true;
            case "setRank":
                setRank();
                return true;
            case "infoSet":
                infoSet();
                return true;
            case "setDay":
                setDay();
                return true;
            case "setRemainingScenes":
                setRemainingScenes();
                return true;
            case "setLocation":
                setLocation();
                return true;
            default:
                return false;
        }
    }

    private void startDay(){
        board.setScenes(scenes);

    }

    private void endDay(){
        System.out.println("Day " + ((initialDay - dayNumber) + 1) + " has now been completed! ");
        dayNumber--;
        System.out.println(dayNumber + " days remain.");
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).currSet = board.getSet("trailer");
            playerList.get(i).role = null;
            playerList.get(i).practiceChips = 0;
        }
        board.resetShots();
    }



    private void setDayRules(int numPlayers){
        switch(numPlayers){
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
                }
                break;
        }
    }

    public boolean getRunning() {
        return running;
    }


}
