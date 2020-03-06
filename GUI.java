import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI extends Application{


    public static void main(String[] args){
        launch(args);
    }

    public static void openFile(File file) throws Exception {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void start(Stage stage){
            File file = new File("src/Deadwood-Free-Edition-Rules.pdf");

            Scene game = new Scene(new Group());
            VBox startLayout = new VBox(50);
            Label startMessage = new Label("Welcome to Deadwood");

            Button start = new Button("Start");
            Button quit = new Button("Quit");
            Button rules = new Button("View Rules");

            startLayout.getChildren().addAll(startMessage, start, quit, rules);
            Scene startScene = new Scene(startLayout, 300, 300);

            start.setOnAction(e -> {stage.setScene(game);
                                    stage.setFullScreen(true);});
            quit.setOnAction(e -> stage.close());
            rules.setOnAction(e -> {
                try {
                    openFile(file);
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            });


            VBox layout = new VBox();


            final ImageView imgView = new ImageView();
            Image image1 = new Image("board.jpg");
            imgView.setImage(image1);
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(1600);
            imgView.setFitWidth(1200);

            layout.getChildren().addAll(imgView);
            game.setRoot(layout);

            stage.setTitle("Board");
             stage.setScene(startScene);
            stage.show();

    }

}
