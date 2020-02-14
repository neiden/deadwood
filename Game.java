import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player currPlayer;
    private ArrayList<Player> playerList;
    private Board board;
    private Bank bank;
    private Scanner scanner;
    private boolean running;
    private int numPlayers;

    public Game(){
        scanner = new Scanner(System.in);
        playerList = new ArrayList<>();
        running = true;
    }

    public void init(){
        boolean running = true;
        System.out.println("Welcome to Deadwood!");
        System.out.print("Enter number of players: ");
        numPlayers = scanner.nextInt();
        System.out.println();
        while(running) {
            if (numPlayers > 1) {
                for(int i = 0; i < numPlayers; i ++){
                    System.out.print("Enter Player " + (i+1) + "'s name: ");
                    playerList.add(new Player(scanner.next()));
                    System.out.println();
                }
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
        System.out.println("It's " + currPlayer.name + "'s turn.\nWhat's your move: ");
        String input = scanner.next();
        System.out.println(currPlayer.name + " used " + input + " !");
        if(playerList.indexOf(currPlayer) == playerList.size() - 1){
            currPlayer = playerList.get(0);
        }
        else{
            currPlayer = playerList.get(playerList.indexOf(currPlayer)+1);
        }



    }

    private void startDay(){

    }

    private void endDay(){

    }

    private void setCurrPlayer(Player player){

    }

    public boolean getRunning() {
        return running;
    }


}
