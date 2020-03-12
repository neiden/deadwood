import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;


public class Controller {
    private BoardView view;
    private GameModel model;

    public Controller(BoardView view, GameModel model, ArrayList<String> playerList){
        this.view = view;
        this.model = model;
        this.view.addPlayerOptionListener(playerOptionHandler);
        this.model.init(playerList);
        this.view.init(this.model.getCurrPlayer(), this.model.getSets());
    }

    //Defines the behavior of the buttons
    EventHandler<ActionEvent> playerOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            switch(text){
                case "Work":
                    //implement work
                    //call Game.work -> calls player.validateInput("Work");
                    System.out.println("Work");

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "Act":
                    //etc
                    System.out.println("Act");
                    break;
                case "Rehearse":
                    model.getCurrPlayer().practiceChips++;

                    System.out.println(model.getCurrPlayer().name + " has " + model.getCurrPlayer().practiceChips + " practice chips.");

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    //view.update(model.getAllPlayers(), model.getAllSets());
                    break;
                case "Move":
                    view.createMoveOptions(model.getCurrPlayer().currSet);
                    view.addMoveOptionListener(moveOptionHandler);


                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "Upgrade":
                    System.out.println("Upgrade");

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "End Turn":
                    model.endCurrTurn();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
        }
    }};

    //Defines behavior of the move option buttons
    EventHandler<ActionEvent> moveOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            model.getCurrPlayer().move(text);
            view.update(model.getPlayerList(), model.getSets());
        }};
}
