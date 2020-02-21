import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {

    public String name;
    public int dollars;
    public int credits;
    public int rank;
    public int practiceChips;
    public Role role;
    public Set currSet;
    public Scene currScene;
    public ArrayList<String> options;
    private boolean turn;

    public Player(){

    }

    public Player(String name, Set currSet){
        options = new ArrayList<>();
        this.name = name;
        dollars = 0;
        credits = 0;
        rank = 1;
        role = null;
        turn = false;
        practiceChips = 0;
        this.currSet = currSet;
        currScene = null;
    }


    public int calcScore(){

        return 0;
    }

    private ArrayList<String> availableOptions(){

        return options;
    }

    private String promptPlayer(boolean turn){

        return "";
    }

    public void createOptionList(){
        options.clear();

        if(role == null){
            if(currSet.hasScene() && currSet.hasAvailableRoles()){
                options.add("start work");
            }
            options.add("move");
        }
        else{
            if(practiceChips < currScene.getBudget() - 1){
                options.add("rehearse");
            }
            options.add("act");
        }
        if(currSet.name.equals("Casting Office")){
            options.add("upgrade");
        }
        options.add("end turn");
    }


    private void move(String input){

    }

    private void act(){

    }

    private void upgrade(){

    }

    private void rehearse(){

    }

    public ArrayList<String> getOptions(){
        return options;
    }

    public void setCurrScene(Scene scene){
        currScene = scene;
    }

    public void setCredits(int credits){
        this.credits = credits;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public void setCurrSet(Set currSet){
        this.currSet = currSet;
    }



}
