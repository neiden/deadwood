import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Player {

    public String name;
    public int dollars;
    public int credits;
    public int rank;
    public int practiceChips;
    public Role role;
    public Set currSet;
    public Scene currScene;
    private boolean hasMoved;
    private boolean hasUpgraded;
    private boolean isTurn;
    private Board board;
    private Scanner scanner;
    public ArrayList<String> options;
    private boolean turn;


    public Player(String name, Set currSet, Board board){
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
        this.board = board;
        scanner = new Scanner(System.in);
        hasMoved = false;
        hasUpgraded = false;
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

    public void validateAction(String input){
        boolean correctInput = false;
        for (int i = 0; i < options.size(); i++) {
            if(input.equals(options.get(i))){
                correctInput = true;
            }
        }
        if(correctInput) {
            switch (input) {
                case "move":
                    move();
                    break;
                case "startwork":
                    startWork();
                    isTurn = false;
                    break;
                case "act":
                    act();
                    isTurn = false;
                    break;
                case "rehearse":
                    isTurn = false;
                    rehearse();
                    break;
                case "endturn":
                    isTurn = false;
                    endTurn();
                    break;
                case "upgrade":
                    hasUpgraded = true;
                    upgrade();
                    break;
            }
        }
        else{
            System.out.println("Incorrect Input");
        }
    }


    public void createOptionList(){
        options.clear();

        if(role == null){
            if(currSet.hasScene()){
                ArrayList<Role> roles = currSet.getAvailableRoles(rank);
                if(roles.size() > 0){
                    options.add("startwork");
                }
            }
            if(!hasMoved) {
                options.add("move");
            }
        }
        else{
            if(practiceChips < currSet.getCurrScene().getBudget() - 1){
                options.add("rehearse");
            }
            options.add("act");
        }
        if(currSet.name.equals("office")){
            if(!hasUpgraded) {
                options.add("upgrade");
            }
        }
        options.add("endturn");
    }

    public void setMoved(boolean moved){
        hasMoved = moved;
    }

    public void setUpgraded(boolean upgraded){
        hasUpgraded = upgraded;
    }

    public boolean getTurn(){
        return isTurn;
    }

    public void setTurn(boolean turn){
        isTurn = turn;
    }

    private void endTurn(){

    }

    private void move(){
        boolean correctInput = false;
        ArrayList<String> availableSets = currSet.getNeighbors();
        while(!correctInput) {
            System.out.println("Available Sets: " + availableSets);

            String input = scanner.nextLine();
            for (int i = 0; i < availableSets.size(); i++) {
                if (input.equals(availableSets.get(i))) {
                    correctInput = true;
                }
            }
            if (correctInput) {
                currSet = board.getSet(input);
                hasMoved = true;
                System.out.println(name + " has moved to " + currSet.name);
            } else {
                System.out.println("Invalid Destination");
            }
        }



    }

    private void startWork(){
        boolean correctInput = false;
        ArrayList<Role> availableRoles = currSet.getAvailableRoles(rank);
        Iterator<Role> itr = availableRoles.iterator();
        while(itr.hasNext()){
            Role role = itr.next();
            if(role.getLevel() > rank){
                itr.remove();
            }
        }
        while(!correctInput) {
            System.out.println("Scene being shot: ");
            System.out.println(currSet.getCurrScene());
            System.out.println("Available Roles: ");
            for (int i = 0; i < availableRoles.size(); i++) {
                System.out.print(availableRoles.get(i).getName() + ", rank " + availableRoles.get(i).getLevel() + ", type " + availableRoles.get(i).getType());
                System.out.println();
            }
            String input = scanner.nextLine();
            for (int i = 0; i < availableRoles.size(); i++) {
                if (input.equals(availableRoles.get(i).getName())) {
                    correctInput = true;
                }
            }
            if (correctInput) {

                for (int i = 0; i < availableRoles.size(); i++) {
                    if (availableRoles.get(i).getName().equals(input)) {
                        role = availableRoles.get(i);
                        if(role.getType().equals("extra")){
                            currSet.setRoleActor(role.getName(), this);
                        }
                        else{
                            currSet.getCurrScene().setRoleActor(role.getName(), this);
                        }
                    }
                }

                System.out.println(name + " is now working as " + role.getName());
            } else {
                System.out.println("Incorrect Role Name");
            }
        }
    }

    private void act(){
        Random rand = new Random();
        int roll = rand.nextInt(7) + 1;
        System.out.println(name + " rolled a " + roll + ", + " + practiceChips + " is " + (roll + practiceChips) + "!");

        if(roll + practiceChips >= currSet.getCurrScene().getBudget()){
            if(role.getType().equals("extra")){
                dollars++;
                credits++;
            }
            else{
                credits += 2;
            }
            currSet.decrementShot();
            System.out.println("It's higher than " + currSet.getCurrScene().getBudget() + "! " + currSet.getShotsRemaining() + " shots remain.");
            if(currSet.getShotsRemaining() < 1) {
                System.out.println(name + " completed the scene!");
            }
        }
        else{
            System.out.println("It wasn't higher than " + currSet.getCurrScene().getBudget() + "...");
            if(role.getType().equals("extra")){
                dollars++;
                System.out.println("You still get 1 dollar though.");
            }
        }

    }

    private void upgrade(){
        ArrayList<String> upgradeOptions = new ArrayList<>();
    }

    private void rehearse(){
        practiceChips++;
        System.out.println(name + " now has " + practiceChips + " chip(s)");
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

    public String toString(){

        return "Name: " + name + " is at " + currSet.name;
    }



}
