import java.util.ArrayList;

public class Scene {

    private int shotsRemaining;
    public String name;
    private String availableRoles;
    private ArrayList<Role> roleList;
    private ArrayList<String> neighboringScenes;

    public Scene(int shotsRemaining, String name, ArrayList<Role> roleList, ArrayList<String> neighboringScenes){
        this.shotsRemaining = shotsRemaining;
        this.name = name;
        this.roleList = roleList;
        this.neighboringScenes = neighboringScenes;
    }

    public void reset(){

    }

    public void BonusMoneyDistribution(){

    }

    @Override
    public String toString() {
        return name + "\n" + "shots remaining: " + shotsRemaining + "\n available roles: " + roleList + "\n neighboring scenes: " + neighboringScenes;
    }
}
