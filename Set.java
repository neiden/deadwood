import java.util.ArrayList;

public class Set {
    private Scene currScene;
    private int initialShots;
    private int shotsRemaining;
    public String name;
    private ArrayList<Role> extras;
    private ArrayList<Upgrade> upgrades;
    private ArrayList<String> setNeighbors;

    public Set(int initialShots, String name, ArrayList<Role> extras, ArrayList<String> setNeighbors){
        this.initialShots = initialShots;
        this.name = name;
        this.extras = extras;
        this.setNeighbors = setNeighbors;
        currScene = null;
        upgrades = null;
        shotsRemaining = initialShots;
    }

    public String getName(){
        return name;
    }

    public void addScene(Scene scene){
        currScene = scene;
    }

    public void removeScene(){
        currScene = null;

    }

    public void setShotsRemaining(){
        shotsRemaining = initialShots;
    }

    public ArrayList<String> getNeighbors(){
        return setNeighbors;
    }

    public boolean hasScene(){
        return currScene != null;
    }

    public Scene getCurrScene(){
        return currScene;
    }

    public void setRoleActor(String name, Player player){
        for (int i = 0; i < extras.size(); i++) {
            if(extras.get(i).getName().equals(name)){
                extras.get(i).setActor(player);
            }
        }
    }


    public  ArrayList<Upgrade> getUpgrades(){
        return null;
    }


    public ArrayList<Role> getAvailableRoles(int rank){
        ArrayList<Role> allRoles = new ArrayList();
        for (int i = 0; i < extras.size(); i++) {
            if(extras.get(i).getActor() == null && extras.get(i).getLevel() <= rank){
                allRoles.add(extras.get(i));
            }
        }
        for (int i = 0; i < currScene.getRoleList().size(); i++) {
            if(currScene.getRoleList().get(i).getActor() == null && currScene.getRoleList().get(i).getLevel() <= rank){
                allRoles.add(currScene.getRoleList().get(i));
            }
        }

        return allRoles;
    }


    public boolean hasOpenExtras(){
        for (int i = 0; i < extras.size(); i++) {
            if(extras.get(i).getActor() == null){
                return true;
            }
        }
        return false;
    }

    public void setCurrScene(Scene scene){
        currScene = scene;
    }

    public void decrementShot(){
        shotsRemaining--;
    }

    public boolean hasAvailableRoles(){
        if(currScene != null) {
            return currScene.hasOpenMains() && hasOpenExtras();
        }
        else{
            return false;
        }
    }

    public int getShotsRemaining(){
        return shotsRemaining;
    }

    public String toString(){
        return "Name: " + name + " \nshots remaining: " + shotsRemaining + " \n" + extras + "\nScene: " + currScene + " \nneighbors " + setNeighbors + "\n";
    }


}
