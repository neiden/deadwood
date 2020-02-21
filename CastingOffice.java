import java.util.ArrayList;

public class CastingOffice extends Set{



    public ArrayList<Upgrade>  upgrades;

    public CastingOffice(int shotsRemaining, String name, ArrayList<Role> roleList, ArrayList<String> neighboringScenes, ArrayList<Upgrade> upgrades) {
        super(shotsRemaining, name, roleList, neighboringScenes);
        this.upgrades = upgrades;
    }



    public void upgradePlayer(Player player){

    }
}
