import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.ArrayList;


public class Controller {
    private BoardView view;
    private GameModel model;
    //Creates Controller object that has an instance of the model and view so that it can communicate between the two
    public Controller(BoardView view, GameModel model, ArrayList<String> playerList){
        this.view = view;
        this.model = model;
        this.view.addPlayerOptionListener(playerOptionHandler);
        this.model.init(playerList);
        this.view.init(this.model.getCurrPlayer(), this.model.getSets(), this.model.getPlayerList());
    }

    //Defines the behavior of the player option buttons
    //After each button press, updates the graphics to ensure that the screen is accurately representing the game
    EventHandler<ActionEvent> playerOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            switch(text){
                case "Work":
                    view.createWorkOptions(model.getCurrPlayer());
                    view.addWorkOptionListener(workOptionHandler);

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "Act":
                    model.getCurrPlayer().act();

                    model.sceneClosureCheck();
                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    view.update(model.getPlayerList(), model.getSets());
                    break;
                case "Rehearse":
                    model.getCurrPlayer().rehearse();

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    view.update(model.getPlayerList(), model.getSets());
                    break;
                case "Move":
                    view.createMoveOptions(model.getCurrPlayer().currSet);
                    view.addMoveOptionListener(moveOptionHandler);
                    break;
                case "Upgrade":
                    view.createUpgradeOptions(model.getCurrPlayer());
                    view.addUpgradeOptionListener(upgradeOptionHandler);

                    model.getCurrPlayer().createOptionList();
                    view.validateOptions(model.getCurrPlayer().getOptions());
                    break;
                case "End Turn":
                    if(model.endCurrTurn()){
                        view.setEndScreen(model.calcScore());
                    }
                    else{
                        view.validateOptions(model.getCurrPlayer().getOptions());
                        view.update(model.getPlayerList(), model.getSets());
                    }
                    break;
        }
    }};

    //Defines behavior for the work options
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
    //Defines behavior for the upgrade buttons
    EventHandler<ActionEvent> upgradeOptionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button currButton = ((Button)actionEvent.getSource());
            String text = currButton.getText();
            model.getCurrPlayer().upgrade(text);

            model.getCurrPlayer().createOptionList();
            view.validateOptions(model.getCurrPlayer().getOptions());
            view.update(model.getPlayerList(), model.getSets());
        }};
}
