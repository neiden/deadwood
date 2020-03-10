import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameController  {

   public static void startGame(ArrayList<String> playerList, Stage stage)throws FileNotFoundException {
      Stage gameStage = new Stage();
      gameStage.setFullScreen(true);
      Image board = new Image(new FileInputStream("src/board.jpg"));
      ImageView imageView = new ImageView(board);

      imageView.setPreserveRatio(true);
      imageView.setFitHeight(1000);
      imageView.setFitWidth(1200);
      Scene scene = new Scene(new Group(imageView));
      gameStage.setTitle("Deadwood");
      gameStage.setScene(scene);


      gameStage.setOnShowing(e -> {
        // Main.run(playerList);

      });

      stage.close();

      gameStage.show();



   }

}
