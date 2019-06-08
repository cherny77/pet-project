package cracker;

import cracker.logic.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/app.fxml"));
        AnchorPane root = fxmlLoader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.setTitle("Cracker");
        primaryStage.setScene(new Scene(root, 1024, 868));
        Controller controller = fxmlLoader.getController();
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        Game game = new Game();
        game.init();

        controller.setStage(primaryStage);
        controller.init(game);
        controller.setBinding();
        game.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
