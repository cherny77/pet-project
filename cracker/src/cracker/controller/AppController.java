package cracker.controller;

import javafx.fxml.FXML;

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
}
