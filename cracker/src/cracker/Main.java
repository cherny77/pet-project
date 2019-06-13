package cracker;

import cracker.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		primaryStage.setScene(new Scene(root, 1024, 868));
		AppController appController = fxmlLoader.getController();
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		appController.init();
		appController.getLevelController().setStage(primaryStage);
	}
}
