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

    public Game(ArrayList<Set> sets, ArrayList<Scene> scenes){
        board = new Board(sets);
        scanner = new Scanner(System.in);
        playerList = new ArrayList<>();
        running = true;
        this.scenes = scenes;
        dayDeadline = 0;
        dayNumber = 0;
    }

    public void init(){

        boolean running = true;
        System.out.println("Welcome to Deadwood!");
        System.out.print("Enter number of players: ");
        numPlayers = scanner.nextInt();

        System.out.println();

        while(running) {
            if (numPlayers > 1) {
                board.setNumPlayers(numPlayers);
                for(int i = 0; i < numPlayers; i ++){
                    System.out.print("Enter Player " + (i+1) + "'s name: ");
                    playerList.add(new Player(scanner.next(), board.getSet("Trailer")));
                    System.out.println();
                }

                setDayRules(numPlayers);
                board.setScenes(scenes);
                currPlayer = playerList.get(0);
                running = false;

            } else {
                System.out.println("You must enter more than 1 player!");
                System.out.print("Enter number of players: ");
                numPlayers = scanner.nextInt();
                System.out.println();
            }
        }
    }

    public void run(){
        if(dayNumber < dayDeadline){
            running = false;
        }else {

            System.out.println("It's " + currPlayer.name + "'s turn." +
                    "\nAvailable moves: ");
            currPlayer.createOptionList();

            System.out.println(currPlayer.getOptions());
            String input = scanner.next();

            System.out.println(currPlayer.name + " used " + input + " !");


            if (playerList.indexOf(currPlayer) == playerList.size() - 1) {
                currPlayer = playerList.get(0);
            } else {
                currPlayer = playerList.get(playerList.indexOf(currPlayer) + 1);
            }

            if (board.scenesRemaining() < 1) {
                dayNumber--;
                board.setScenes(scenes);
            }

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
