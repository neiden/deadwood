

import java.util.ArrayList;

public class Main {
    public static ArrayList<Set> sets = new ArrayList<>();
    public static ArrayList<Scene> scenes = new ArrayList<>();

    public static void run(ArrayList<String> playerList){
        ParseXML parse = new ParseXML();
        String boardName = "src/oard.xml";
        String cardName = "src/cards.xml";


        try{

            sets = parse.readBoardData(parse.getDocFromFile(boardName));
            scenes = parse.readCardData(parse.getDocFromFile(cardName));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



        Game game = new Game(sets, scenes);

        game.init(playerList);
        while(game.getRunning()){
            game.run();
        }
        System.out.println("Thanks for playing!");

    }
}
