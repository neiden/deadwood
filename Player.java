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
    public int points;
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
        this.board = board;
        scanner = new Scanner(System.in);
        hasMoved = false;
        hasUpgraded = false;
        points = 0;
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
        int roll = rand.nextInt(6) + 1;
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
            System.out.println("It's greater or equal to " + currSet.getCurrScene().getBudget() + "! " + currSet.getShotsRemaining() + " shots remain.");
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
        ArrayList<Upgrade> upgradeOptions = new ArrayList<>();
        boolean correctInput = false;
        for (int i = 0; i < currSet.getUpgrades().size(); i++) {
            if(currSet.getUpgrades().get(i).level > rank){
                if(currSet.getUpgrades().get(i).credits <= credits || currSet.getUpgrades().get(i).dollars <= dollars){
                    upgradeOptions.add(currSet.getUpgrades().get(i));
                }
            }
        }
        if(upgradeOptions.size() > 0) {
            while (!correctInput) {
                System.out.println(upgradeOptions);
                System.out.println("Enter corresponding number for rank: ");
                int input = scanner.nextInt();
                Upgrade upgrade = null;
                for (int i = 0; i < upgradeOptions.size(); i++) {
                    if (upgradeOptions.get(i).level == input) {
                        correctInput = true;
                        upgrade = upgradeOptions.get(i);
                    }
                }
                if(correctInput){
                    rank = input;
                    if(dollars >= upgrade.dollars && credits >= upgrade.credits){
                        System.out.println("Which currency do you want to use? [dollars], [credits]");
                        boolean correctCurrency = false;
                        while(!correctCurrency){
                            String currency = scanner.next();
                            if(currency.equals("dollars") || currency.equals("credits")) {
                                correctCurrency = true;
                                if (currency.equals("dollars")) {
                                    dollars -= upgrade.dollars;
                                }
                                else if (currency.equals("credits")) {
                                    credits -= upgrade.credits;
                                }
                            }
                            else{
                                System.out.println("Enter a valid option!");
                            }
                        }
                    }
                    else if(dollars >= upgrade.dollars){
                        dollars -= upgrade.dollars;
                    }
                    else{
                        credits -= upgrade.credits;
                    }
                    System.out.println(name + " is now rank " + rank + "!");
                    scanner.nextLine();
                }
                else{
                    System.out.println("Invalid upgrade level.");
                }
            }
        }
        else {
            System.out.println("There are no available upgrades for you right now!");
        }
    }

    private void rehearse(){
        practiceChips++;
        System.out.println(name + " now has " + practiceChips + " chip(s)");
    }


    public ArrayList<String> getOptions(){
        return options;
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
