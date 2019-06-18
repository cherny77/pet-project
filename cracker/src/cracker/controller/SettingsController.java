package cracker.controller;

import cracker.model.Complexity;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SettingsController {
	@FXML
	private AnchorPane settingPane;

	@FXML
	private ImageView mapView;

	@FXML
	private ImageView leftSoundButton;

	@FXML
	private ImageView rightSoundButton;

	@FXML
	private ImageView leftMusicButton;

	@FXML
	private ImageView rightMusicButton;

	@FXML
	private Label soundLabel;

	@FXML
	private Label musicLabel;

	@FXML
	private ImageView easyButton;

	@FXML
	private ImageView mediumButton;

	@FXML
	private ImageView hardButton;

	@FXML ImageView cancelButton;
	@FXML ImageView saveButton;

	private Node selected;
	private Complexity complexity;
	LevelController levelController;
	WelcomeController welcomeController;
	private int soundLevel;
	private int musicLevel;

	public void init() {
		settingPane.setVisible(false);
	}

	public void open(){
		complexity = levelController.getComplexity();
		soundLevel = welcomeController.getSoundLevel();
		musicLevel = welcomeController.getMusicLevel();
		settingPane.setVisible(true);
		soundLabel.setText(String.valueOf(soundLevel));
		musicLabel.setText(String.valueOf(musicLevel));
		if (complexity == Complexity.EASY) onEasyButtonClicked();
		else if (complexity == Complexity.NORMAL) onMediumButtonClicked();
		else onHardButtonClicked();
	}

	public void setWelcomeController(WelcomeController welcomeController) {
		this.welcomeController = welcomeController;
	}



	public void setLevelController(LevelController levelController) {
		this.levelController = levelController;
	}

	public void onLeftSoundClicked() {
		if (soundLevel > 0)
		soundLevel --;
		soundLabel.setText(String.valueOf(soundLevel));
	}

	public void onRightSoundClicked() {
		if (soundLevel < 10)
			soundLevel ++;
		soundLabel.setText(String.valueOf(soundLevel));
	}

	public void onRightSoundEntered() {
		rightSoundButton.setImage(new Image("image/welcome/right-arrow-btn-entered.png"));
	}

	public void onRightSoundExited() {
		rightSoundButton.setImage(new Image("image/welcome/right-arrow-btn.png"));
	}

	public void onLeftSoundEntered() {
		leftSoundButton.setImage(new Image("image/welcome/left-arrow-button-entered.png"));
	}

	public void onLeftSoundExited() {
		leftSoundButton.setImage(new Image("image/welcome/left-arrow-button.png"));
	}
	public void onLeftMusicClicked() {
		if (musicLevel > 0)
			musicLevel --;
		musicLabel.setText(String.valueOf(musicLevel));
	}

	public void onRightMusicEntered() {
		rightMusicButton.setImage(new Image("image/welcome/right-arrow-btn-entered.png"));
	}

	public void onRightMusicExited() {
		rightMusicButton.setImage(new Image("image/welcome/right-arrow-btn.png"));
	}

	public void onLeftMusicEntered() {
		leftMusicButton.setImage(new Image("image/welcome/left-arrow-button-entered.png"));
	}

	public void onLeftMusicExited() {
		leftMusicButton.setImage(new Image("image/welcome/left-arrow-button.png"));
	}

	public void onRightMusicClicked() {
		if (musicLevel < 10)
			musicLevel ++;
		musicLabel.setText(String.valueOf(musicLevel));
	}

	public void onEasyButtonEntered() {
		if (selected != easyButton)
		easyButton.setImage(new Image("image/settings/easy-btn-entered.png"));
	}

	public void onEasyButtonExited() {
		if (selected != easyButton)
		easyButton.setImage(new Image("image/settings/easy-btn-exited.png"));
	}

	public void onEasyButtonClicked() {
		selected = easyButton;
		easyButton.setImage(new Image("image/settings/easy-btn-selected.png"));
		onMediumButtonExited();
		onHardButtonExited();
		complexity = complexity.EASY;

	}


	public void onMediumButtonEntered() {
		if (selected != mediumButton)
		mediumButton.setImage(new Image("image/settings/medium-btn-entered.png"));
	}

	public void onMediumButtonExited() {
		if (selected != mediumButton)
		mediumButton.setImage(new Image("image/settings/medium-btn-exited.png"));
	}

	public void onMediumButtonClicked() {
		selected = mediumButton;
		mediumButton.setImage(new Image("image/settings/medium-btn-selected.png"));
		onEasyButtonExited();
		onHardButtonExited();
		complexity = complexity.NORMAL;

	}

	public void onHardButtonEntered() {
		if (selected != hardButton)
		hardButton.setImage(new Image("image/settings/hard-btn-entered.png"));
	}

	public void onHardButtonExited() {
		if (selected != hardButton)
		hardButton.setImage(new Image("image/settings/hard-btn-exited.png"));
	}

	public void onHardButtonClicked() {
		selected = hardButton;
		hardButton.setImage(new Image("image/settings/hard-btn-selected.png"));
		onEasyButtonExited();
		onMediumButtonExited();
		complexity = complexity.HARD;

	}


	public void onSaveButtonEntered() {
		saveButton.setImage(new Image("image/settings/save-btn-entered.png"));
	}

	public void onSaveButtonExited() {
		saveButton.setImage(new Image("image/settings/save-btn-exited.png"));
	}


	public void onCancelButtonEntered() {
		cancelButton.setImage(new Image("image/settings/cancel-btn-entered.png"));
	}

	public void onCancelButtonExited() {
		cancelButton.setImage(new Image("image/settings/cancel-btn-exited.png"));
	}

	public void onSaveClicked(){
		levelController.setComplexity(complexity);
		welcomeController.setMusicLevel(musicLevel);
		welcomeController.setSoundLevel(soundLevel);
		welcomeController.changeVolume();
		settingPane.setVisible(false);
	}

	public void onCancelClicked(){
		settingPane.setVisible(false);
	}





}
