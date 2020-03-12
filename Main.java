

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static ArrayList<Set> sets = new ArrayList<>();
    public static ArrayList<Scene> scenes = new ArrayList<>();
    public static ArrayList<String> playerList = new ArrayList<>();
    public BoardView view;
    public GameModel model;
    public Controller gameController;

    /*public static void start(ArrayList<String> playerList){
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

*/
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        InitController.run();
        System.out.println("Player List: " + playerList);

        ParseXML parse = new ParseXML();
        String boardName = "src/oard.xml";
        String cardName = "src/cards.xml";


        try {

            sets = parse.readBoardData(parse.getDocFromFile(boardName));
            scenes = parse.readCardData(parse.getDocFromFile(cardName));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(playerList.size() != 0) {
            model = new GameModel(sets, scenes);
            view = new BoardView(model.getPlayerList(), model.getSets());
            gameController = new Controller(view, model, playerList);
            view.setVisible(true);

        }



/*
        GameModel game = new GameModel(sets, scenes);

        game.init(InitController.playerList);
        while(game.getRunning()){
            game.run();
        }
        System.out.println("Thanks for playing!");

    }
    */
    }
}
