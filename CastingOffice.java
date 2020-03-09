import java.util.ArrayList;

public class CastingOffice extends Set {


    public Bank bank;
    public ArrayList<Upgrade>  upgrades;

    public CastingOffice(ArrayList<Shot> shotsRemaining, String name, ArrayList<Role> roleList, ArrayList<String> neighboringScenes, ArrayList<Upgrade> upgrades, int[] setCoordinate) {
        super(shotsRemaining, name, roleList, neighboringScenes, setCoordinate);
        this.upgrades = upgrades;
        bank = new Bank();
    }

    public ArrayList<Upgrade> getUpgrades(){
        return upgrades;
    }


    public void upgradePlayer(Player player, String rank){
        player.rank = Integer.parseInt(rank);
    }


}
