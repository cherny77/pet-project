package cracker;

import cracker.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/app.fxml"));
		AnchorPane root = fxmlLoader.load();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image("/image/welcome/logo.png"));
		Scene scene = new Scene(root, 1024, 868);
		primaryStage.setScene(scene);
		root.getStylesheets().add(getClass().getResource("/fonts/FontStyle.css").toExternalForm());
		AppController appController = fxmlLoader.getController();
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		appController.init();
		appController.setScene(scene);
		appController.getLevelController().setStage(primaryStage);
	}
}
