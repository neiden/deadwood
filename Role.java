public class Role {
    private int level;
    private String phrase;
    private String name;

    public Role(int level, String phrase, String name){
        this.level = level;
        this.phrase = phrase;
        this.name = name;
    }


    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
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
        return name + ", " + phrase + ", " + level;
    }
}
