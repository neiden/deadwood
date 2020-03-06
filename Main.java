import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Set> sets = new ArrayList<>();
    public static ArrayList<Scene> scenes = new ArrayList<>();

    public static void main(String[] args){
        ParseXML parse = new ParseXML();
        String boardName = "src/oard.xml";
        String cardName = "src/cards.xml";
        GUI gui = new GUI();


        try{

            sets = parse.readBoardData(parse.getDocFromFile(boardName));
            scenes = parse.readCardData(parse.getDocFromFile(cardName));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



        Game game = new Game(sets, scenes);
        game.init();
        while(game.getRunning()){
            game.run();
        }
        System.out.println("Thanks for playing!");

    }
}
