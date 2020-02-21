import java.util.ArrayList;

public class Set {
    private Scene currScene;
    private int shotsRemaining;
    public String name;
    private ArrayList<Role> extras;
    private ArrayList<String> setNeighbors;

    public Set(int shotsRemaining, String name, ArrayList<Role> extras, ArrayList<String> setNeighbors){
        this.shotsRemaining = shotsRemaining;
        this.name = name;
        this.extras = extras;
        this.setNeighbors = setNeighbors;

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


    public boolean hasScene(){
        return currScene != null;
    }


    public boolean hasOpenExtras(){
        for (int i = 0; i < extras.size(); i++) {
            if(extras.get(i).getActor() == null){
                return true;
            }
        }
        return false;
    }

    public boolean hasAvailableRoles(){

        return currScene.hasOpenMains() && hasOpenExtras();
    }

    public String toString(){
        return "Name: " + name + " \nshots remaining: " + shotsRemaining + " \n" + extras + "\nScene: " + currScene + " \nneighbors " + setNeighbors + "\n";
    }


}
