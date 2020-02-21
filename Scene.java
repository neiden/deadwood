import java.util.ArrayList;

public class Scene {


    public String name;
    private int budget;
    private int sceneNum;
    private String description;
    private ArrayList<Role> roleList;

    public Scene(int budget, int sceneNum, String description, String name, ArrayList<Role> roleList){
        this.name = name;
        this.budget = budget;
        this.sceneNum = sceneNum;
        this.description = description;
        this.roleList = roleList;
    }

    public void reset(){

    }

    public void BonusMoneyDistribution(){

    }

    public boolean hasOpenMains(){
        for (int i = 0; i < roleList.size(); i++) {
            if(roleList.get(i).getActor() == null){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Role> getRoleList(){
        return roleList;
    }


    public void setRoleActor(String name, Player player){
        for (int i = 0; i < roleList.size(); i++) {
            if(roleList.get(i).getName().equals(name)){
                roleList.get(i).setActor(player);
            }
        }
    }

    public int getBudget(){
        return budget;
    }

    @Override
    public String toString() {
        return name + "\n" + "description: " + description + "\nAll main roles: " + roleList + "\nBudget: " + budget + "\nscene num: " + sceneNum;
    }
}
