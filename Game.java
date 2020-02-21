import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player currPlayer;
    private ArrayList<Player> playerList;
    private Board board;
    private Bank bank;
    private Scanner scanner;
    private int dayNumber;
    private int dayDeadline;
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
        dayDeadline = 0;
        dayNumber = 1;
        devOptions = new ArrayList<>();
        bank = new Bank();
    }

    public void init(){

        devOptions.add("locations");
        devOptions.add("playerInfo");

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
        if(dayNumber > dayDeadline){
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
                        bank.bonusMoneyDistribution(playerList, currPlayer.currSet.name);
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

                if (board.scenesRemaining() < 1) {
                    dayNumber--;
                    board.setScenes(scenes);
                }
            }

        }

    }

    private void killScene(Scene scene){
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).currSet.getCurrScene() != null) {
                if (playerList.get(i).currSet.getCurrScene().name.equals(scene.name)) {
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

    private boolean devOptions(String input){
        switch(input){
            case "locations":
                location();
                return true;
            case "playerInfo":
                playerInfo();
                return true;
            default:
                return false;
        }
    }

    private void startDay(){

    }

    private void endDay(){

    }

    private void setCurrPlayer(Player player){

    }

    private void setDayRules(int numPlayers){
        switch(numPlayers){
            case 2:
            case 3:
                dayDeadline = 3;
                break;
            case 4:
                dayDeadline = 4;
                break;
            case 5:
                dayDeadline = 4;
                for (int i = 0; i < playerList.size(); i++) {
                    playerList.get(i).setCredits(2);
                }
                break;
            case 6:
                dayDeadline = 4;
                for (int i = 0; i < playerList.size(); i++) {
                    playerList.get(i).setCredits(4);
                }
                break;
            case 7:
            case 8:
                dayDeadline = 4;
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
