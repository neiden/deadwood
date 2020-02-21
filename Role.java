public class Role {
    private int level;
    private String phrase;
    private String name;
    private String type;
    private Player actor;

    public Role(int level, String phrase, String name, String type){
        this.level = level;
        this.phrase = phrase;
        this.name = name;
        this.type = type;
        actor = null;
    }

    public Player getActor(){
        return actor;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setActor(Player actor){

        this.actor = actor;
    }


    public void setPhrase(String phrase) {
        this.phrase = phrase;

    }

    public String getType(){
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Name: " + name + ", Phrase: " + phrase + ", Required Rank: " + level + " Type: " + type;
    }
}
