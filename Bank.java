import java.util.*;

public class Bank {

    public Bank(){

    }


    class SortByRank implements Comparator<Role> {

        public int compare(Role a, Role b){
            return b.getLevel() - a.getLevel();
        }
    }


    public void bonusMoneyDistribution(ArrayList<Player> playerList, Set set){
        boolean main = false;
        Random rand = new Random();
        Scene scene = set.getCurrScene();
        String name = "";

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
            System.out.println(name + " was working a main role on completion of scene, awarding bonus money!!");
            Integer[] rolls = new Integer[scene.getBudget()];

            for (int i = 0; i < scene.getBudget(); i++) {
                rolls[i] = rand.nextInt(6) + 1;
                System.out.println("A " + rolls[i] + " was rolled!");
            }


            int[] payoutValues = new int[scene.getRoleList().size()];
            Arrays.sort(rolls, Collections.reverseOrder());

            ArrayList<Role> mainRoles = scene.getRoleList();
            Collections.sort(mainRoles, new SortByRank());

            for (int i = 0; i < mainRoles.size(); i++) {
                for (int j = i; j < rolls.length; j += mainRoles.size()) {
                    payoutValues[i] += rolls[j];
                }
            }

            for (int i = 0; i < playerList.size(); i++) {
                for (int j = 0; j < mainRoles.size(); j++) {
                    if(playerList.get(i).role != null) {
                        if (playerList.get(i).role.equals(mainRoles.get(j))) {
                            playerList.get(i).dollars += payoutValues[j];
                            System.out.println(playerList.get(i).name + " receives " + payoutValues[j] + " in bonus money as a main actor!");
                        }
                    }
                }
                if(playerList.get(i).role != null){
                    if(playerList.get(i).role.getType().equals("extra") && playerList.get(i).currSet.equals(set)){
                        playerList.get(i).dollars += playerList.get(i).role.getLevel();
                        System.out.println(playerList.get(i).name + " receives " + playerList.get(i).role.getLevel() + " in bonus money as an extra!");
                    }
                }
            }
        }
        else{
            System.out.println("There were no player working main roles on completion of scene, no bonus money");
        }



    }



}
