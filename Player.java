import java.util.ArrayList;

public class Player {

    public String name;
    public int dollars;
    public int credits;
    public int rank;
    public Role role;
    private boolean turn;

    public Player(){

    }

    public Player(String name){
        this.name = name;
        dollars = 0;
        credits = 0;
        rank = 1;
        role = null;
        turn = false;
    }


    public int calcScore(){

        return 0;
    }

    private ArrayList<String> availableOptions(){

        return null;
    }

    private String promptPlayer(boolean turn){

        return "";
    }


    private void move(String input){

    }

    private void act(){

    }

    private void upgrade(){

    }

    private void rehearse(){

    }




}
