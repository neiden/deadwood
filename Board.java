import java.util.ArrayList;
import java.util.Random;
public class Board {

    public ArrayList<Set> sets;
    private Player currPlayer;
    private int numPlayers;

    public Board( ArrayList<Set> sets) {
        this.sets = sets;
    }


    public void setScenes(ArrayList<Scene> scenes){
        Random rand = new Random();
        for (int i = 0; i < sets.size(); i++) {
            int randIdx = rand.nextInt(scenes.size());
            sets.get(i).addScene(scenes.get(randIdx));
            scenes.remove(randIdx);
        }

    }

    public int scenesRemaining(){
        int count = 0;
        for (int i = 0; i < sets.size(); i++) {
            if(sets.get(i).hasScene()){
                count++;
            }
        }
        return count;
    }



    private void init(){


    }

    public void setNumPlayers(int numPlayers){

    }

    public Set getSet(String setName){
        Set currset = null;
        for (int i = 0; i < sets.size(); i++) {
            if(sets.get(i).getName().equals(setName)){
                currset = sets.get(i);
            }
        }

        return currset;
    }




}
