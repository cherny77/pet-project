package cracker.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
	@FXML
	private AnchorPane settingPane;

	public void init() {
		settingPane.setVisible(false);
	}

	public void open(){
		settingPane.setVisible(false);
	}



}
