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
        this.view.init(this.model.getCurrPlayer(), this.model.getSets(), this.model.getPlayerList());
    }

    //Defines the behavior of the buttons
    EventHandler<ActionEvent> playerOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            switch(text){
                case "Work":
                    System.out.println("Work");
                    view.createWorkOptions(model.getCurrPlayer());
                    view.addWorkOptionListener(workOptionHandler);

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "Act":
                    //etc
                    System.out.println("Act");
                    model.getCurrPlayer().act();

                    model.sceneClosureCheck();
                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    view.update(model.getPlayerList(), model.getSets());
                    break;
                case "Rehearse":
                    model.getCurrPlayer().rehearse();

                    System.out.println(model.getCurrPlayer().name + " has " + model.getCurrPlayer().practiceChips + " practice chips.");

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    view.update(model.getPlayerList(), model.getSets());
                    //view.update(model.getAllPlayers(), model.getAllSets());
                    break;
                case "Move":
                    view.createMoveOptions(model.getCurrPlayer().currSet);
                    view.addMoveOptionListener(moveOptionHandler);
                    break;
                case "Upgrade":
                    System.out.println("Upgrade");
                    view.createUpgradeOptions(model.getCurrPlayer());
                    view.addUpgradeOptionListener(upgradeOptionHandler);

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    view.update(model.getPlayerList(), model.getSets());
                    break;
                case "End Turn":
                    model.endCurrTurn();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
        }
    }};

    EventHandler<ActionEvent> workOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            String[] names = text.split(",");
            model.getCurrPlayer().startWork(names[0]);

            model.getCurrPlayer().createOptionList();
            view.validateOptions(model.getCurrPlayer().getOptions());
            view.update(model.getPlayerList(), model.getSets());
        }};

    //Defines behavior of the move option buttons
    EventHandler<ActionEvent> moveOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            model.getCurrPlayer().move(text);

            model.getCurrPlayer().createOptionList();
            view.validateOptions(model.getCurrPlayer().getOptions());
            view.update(model.getPlayerList(), model.getSets());
        }};

    EventHandler<ActionEvent> upgradeOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            model.getCurrPlayer().upgrade(currButton.getText());

            model.getCurrPlayer().createOptionList();
            view.validateOptions(model.getCurrPlayer().getOptions());
            view.update(model.getPlayerList(), model.getSets());
        }};
}
