package cheditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppLaunch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinWidth(350);
        FXMLLoader fxmlLoader = new FXMLLoader(AppLaunch.class.getResource("/MainStage.fxml"));
        AnchorPane root = fxmlLoader.load();
        primaryStage.setTitle("ChEditor");
        primaryStage.getIcons().add(new Image(AppLaunch.class.getResourceAsStream("/icon.png")));
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("app.css");
        AppController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.setBinding();
        controller.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {

        launch(args);
    }

}



