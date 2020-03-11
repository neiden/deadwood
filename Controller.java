import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;



public class Controller {
    private BoardView view;
    private GameModel model;

    public Controller(BoardView view, GameModel model){
        this.view = view;
        this.model = model;
        this.view.addPlayerOptionListener(playerOptionHandler);
    }

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
                    break;
                case "Act":
                    //etc
                    System.out.println("Act");
                    break;
                case "Rehearse":
                    //model.getCurrPlayer().practiceChips++;
                    System.out.println("Rehearse");

                    //view.update(model.getAllPlayers(), model.getAllSets());
                    break;
                case "Move":
                    System.out.println("Move");
                    break;
                case "End Turn":
                    System.out.println("End Turn");
                    break;
        }
    }};

}
