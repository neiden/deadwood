import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
public class Board {

    public ArrayList<Set> sets;

    public Board( ArrayList<Set> sets) {
        this.sets = sets;
    }


    public void setScenes(ArrayList<Scene> scenes){
        Random rand = new Random();

        for (int i = 0; i < sets.size() - 2; i++) {
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



    public void setNumPlayers(int numPlayers){

    }

    public void resetShots(){
        for (int i = 0; i < sets.size(); i++) {
            sets.get(i).setShotsRemaining();
        }
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
