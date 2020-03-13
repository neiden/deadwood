

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

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        InitController.run();

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
            view = new BoardView();
            gameController = new Controller(view, model, playerList);
            view.setVisible(true);

        }


    }
}
