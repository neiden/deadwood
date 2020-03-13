import com.sun.jdi.ShortType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Set {
    private Scene currScene;
    private ArrayList<Shot> shots;
    private int initialShots;
    private int shotsRemaining;
    public String name;
    private ArrayList<Role> extras;
    private ArrayList<Upgrade> upgrades;
    private ArrayList<String> setNeighbors;
    private ImageView imgMainView;
    private ArrayList<ImageView> shotCounterView;

    private Image mainImage;
    private Image backImage;
    private int[] sceneCoordinate;

    public Set(ArrayList<Shot> shots, String name, ArrayList<Role> extras, ArrayList<String> setNeighbors, int[] sceneCoordinate){
        if(shots != null){
            initialShots = shots.size();
        }
        else{
            initialShots = 0;
        }
        this.shots = shots;
        this.name = name;
        this.extras = extras;
        this.setNeighbors = setNeighbors;
        currScene = null;
        upgrades = null;
        shotsRemaining = initialShots;
        this.sceneCoordinate = sceneCoordinate;
        shotCounterView = null;
        imgMainView = null;
    }

    public String getName(){
        return name;
    }

    public ImageView getImageView(){
        return imgMainView;
    }

    public ArrayList<ImageView> getShotsView(){
        return shotCounterView;
    }

    public void setShowing(boolean playerPresent){
        if(playerPresent){
            if(imgMainView != null) {
                imgMainView.setImage(mainImage);
            }
        }
    }

    public void addScene(Scene scene){
        imgMainView = new ImageView();
        currScene = scene;

        backImage = new Image("CardBack-small.jpg");
        mainImage = new Image("Cards/"+scene.getImageName());

        imgMainView.setImage(backImage);
        imgMainView.setX(sceneCoordinate[0]);
        imgMainView.setY(sceneCoordinate[1]);
        imgMainView.setFitHeight(sceneCoordinate[2]);
        imgMainView.setFitWidth(sceneCoordinate[3]);


        shotCounterView = new ArrayList<>();
        for (int i = 0; i < shotsRemaining; i++) {
            ImageView shotImgView = new ImageView();
            shotImgView.setImage(new Image("shot.png"));
            shotImgView.setX(shots.get(i).shotCoordinates[0]);
            shotImgView.setY(shots.get(i).shotCoordinates[1]);
            shotImgView.setFitHeight(shots.get(i).shotCoordinates[2]);
            shotImgView.setFitWidth(shots.get(i).shotCoordinates[3]);

            shotCounterView.add(shotImgView);
        }


    }

    public void removeScene(){
        currScene = null;

    }

    public int[] getSceneCoordinate(){

        return sceneCoordinate;
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
        if(shotsRemaining == 0){
            imgMainView = null;
        }
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
