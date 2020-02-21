public class Upgrade {
    public int dollars;
    public int credits;
    public int level;

    public Upgrade(int level){
        dollars = 0;
        credits = 0;
        this.level = level;
    }

    public void setDollars(int dollars){
        this.dollars = dollars;
    }

    public void setCredits(int credits){
        this.credits = credits;
    }

}
