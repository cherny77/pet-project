package cracker.controller;

import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class AppController {
	@FXML
	GameController gameController;
	@FXML
	WelcomeController welcomeController;

	public GameController getGameController() {
		return gameController;
	}

	public WelcomeController getWelcomeController() {
		return welcomeController;
	}

	public void init() {
		welcomeController.setGameController(gameController);
		welcomeController.init();
		Image image = new Image("/image/cursor.png");
		gameController.getPane().setCursor(new ImageCursor(image));
		welcomeController.getWelcomePane().setCursor(new ImageCursor(image));

	}
}
