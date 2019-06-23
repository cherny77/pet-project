package cracker.controller;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class AppController {
	@FXML
	LevelController levelController;
	@FXML
	WelcomeController welcomeController;
	@FXML
	SettingsController settingsController;
	Scene scene;
	public SettingsController getSettingsController() {
		return settingsController;
	}

	public LevelController getLevelController() {
		return levelController;
	}

	public WelcomeController getWelcomeController() {
		return welcomeController;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void init() {
		welcomeController.setSettingsController(settingsController);
		settingsController.setLevelController(levelController);
		settingsController.setWelcomeController(welcomeController);
		welcomeController.setLevelController(levelController);
		welcomeController.init();
		settingsController.init();
		levelController.setWelcomeController(welcomeController);
		Image image = new Image("/image/window/cursor.png");
		levelController.getPane().setCursor(new ImageCursor(image));
		welcomeController.getWelcomePane().setCursor(new ImageCursor(image));
		settingsController.getSettingPane().setCursor(new ImageCursor(image));
		levelController.setAppController(this);
	}
}
