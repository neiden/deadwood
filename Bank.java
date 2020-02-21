import java.util.ArrayList;

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

    public void bonusMoneyDistribution(ArrayList<Player> playerList, String set){
        boolean main = false;
        String name = "";
        for (int i = 0; i < playerList.size(); i++) {
            Player currPlayer = playerList.get(i);
            if(currPlayer.role != null) {
                if (currPlayer.currSet.name.equals(set) && currPlayer.role.getType().equals("main")) {
                    name = currPlayer.name;
                    main = true;
                }
            }
        }
        if(main){
            System.out.println(name + " was working a main role on completion of scene, awarding bonus money!!");
            System.out.println("I will later implement this!!!");
        }
        else{
            System.out.println("There were no player working main roles on completion of scene, no bonus money");
        }



    }



}
