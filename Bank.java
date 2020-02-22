import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Bank {

    private int totalCredits;
    private int totalDollars;

    public Bank(){

    }

    public void transaction(Player player, int dollars, int credits){

    }

    public int queryPlayerBalance(){

        return 0;
    }

    class SortbyRoll implements Comparator<Player> {

        public int compare(Player a, Player b){
            return b.points - a.points;
        }
    }


    public void bonusMoneyDistribution(ArrayList<Player> playerList, Set set){
        boolean main = false;
        Random rand = new Random();
        Scene scene = set.getCurrScene();
        String name = "";
        int[] rolls = new int[scene.getBudget()];
        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);
            if(currPlayer.role != null) {
                if (currPlayer.currSet.equals(set) && currPlayer.role.getType().equals("main")) {
                    name = currPlayer.name;
                    main = true;
                }
            }
        }
        if(main){
            for (int i = 0; i < scene.getBudget(); i++) {
                rolls[i] = rand.nextInt(6) + 1;
                System.out.println("A " + rolls[i] + " was rolled!");
            }
            for (int i = 0; i < playerList.size(); i++) {

            }

            System.out.println(name + " was working a main role on completion of scene, awarding bonus money!!");
            System.out.println("I will later implement this!!!");
        }
        else{
            System.out.println("There were no player working main roles on completion of scene, no bonus money");
        }



    }



}
