package cracker.controller;

import cracker.logic.Character;
import cracker.logic.CharacterType;
import cracker.logic.Game;
import cracker.ui.CharacterView;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WelcomeController {
	ArrayList<CharacterView> characters;
	int characterNumber = 0;
	@FXML
	private ImageView charName;
	@FXML
	private ImageView charDescription;
	@FXML
	private ImageView gearWheel;
	@FXML
	private ImageView minimizeView;
	@FXML
	private ImageView crossView;
	@FXML
	private GameController gameController;
	@FXML
	private AnchorPane welcomePane;
	@FXML
	private ImageView leftButton;
	@FXML
	private ImageView rightButton;
	@FXML
	private ImageView characterImage;
	@FXML
	private ImageView playButton;

	@FXML
	public void onPlay() {
		Game game = new Game();
		game.init();
		gameController.init(game);
		gameController.setBinding();
		game.start();
//        welcomePane.setVisible(false);
		FadeTransition fade = new FadeTransition();

		//setting the duration for the Fade transition
		fade.setDuration(Duration.millis(1000));

		//setting the initial and the target opacity value for the transition
		fade.setFromValue(100);
		fade.setToValue(0);

		//setting cycle count for the Fade transition
		fade.setCycleCount(1);

		//setting Circle as the node onto which the transition will be applied
		fade.setNode(welcomePane);

		//playing the transition
		fade.play();
		ScheduledExecutorService executor = gameController.getGame().getExecutor();
		executor.schedule(() -> Platform.runLater(() -> welcomePane.setVisible(false)), 1000, TimeUnit.MILLISECONDS);
	}

	public void init() {
		playButtonInit();
		closeButtonInit();
		minimizeButtonInit();
		characters = new ArrayList<>();
		Character knight = new Character(CharacterType.KNIGHT);
		Image knightImage = new Image("image/knight.gif");
		Character archer = new Character(CharacterType.ARCHER);
		Image archerImage = new Image("image/archer.gif");
		Character wizard = new Character(CharacterType.WIZARD);
		Image wizardImage = new Image("image/wizard.gif");
		CharacterView knightView = new CharacterView(knight, knightImage);
		CharacterView archerView = new CharacterView(archer, archerImage);
		CharacterView wizardView = new CharacterView(wizard, wizardImage);
		characters.add(knightView);
		characters.add(archerView);
		characters.add(wizardView);
		setOptionButton();

	}

	private void setOptionButton() {

		RotateTransition rt = new RotateTransition(Duration.millis(250), gearWheel);
		gearWheel.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rt.setByAngle(10);
				rt.setCycleCount(1);
				rt.play();
			}
		});

		gearWheel.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rt.setByAngle(-10);
				rt.setCycleCount(1);
				rt.play();

			}
		});

	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void onExitedLeftButton() {
		Image image = new Image("image/left-arrow-button.png");
		leftButton.setImage(image);
	}

	public void onEnteredLeftButton() {
		Image image = new Image("image/left-arrow-button-entered.png");
		leftButton.setImage(image);
	}

	public void onExitedRightButton() {
		Image image = new Image("image/right-arrow-button.png");
		rightButton.setImage(image);
	}

	public void onEnteredRightButton() {
		Image image = new Image("image/right-arrow-button-entered.png");
		rightButton.setImage(image);
	}

	private String getImagePath(String id, String suffix) {
		return "image/" + id + "-" + suffix + ".png";
	}

	public void onLeftButton() {
		if (characterNumber == 0) {
			characterNumber = 2;
			characterImage.setImage(characters.get(characterNumber).getImage());
			getCharacter(characterNumber);

		} else {
			characterImage.setImage(characters.get(--characterNumber).getImage());
			getCharacter(characterNumber);

		}
	}

	public void onRightButton() {
		if (characterNumber == 2) {
			characterNumber = 0;
			getCharacter(characterNumber);
			characterImage.setImage(characters.get(characterNumber).getImage());
		} else {
			characterImage.setImage(characters.get(++characterNumber).getImage());
			getCharacter(characterNumber);
		}
	}

	public void getCharacter(int number) {
		charName.setImage(new Image(
				getImagePath(characters.get(characterNumber).getCharacter().getType().toString().toLowerCase(),
						"name")));
		charDescription.setImage(new Image(
				getImagePath(characters.get(characterNumber).getCharacter().getType().toString().toLowerCase(),
						"description")));
	}

	public AnchorPane getWelcomePane() {
		return welcomePane;
	}

	private void minimizeButtonInit() {
		this.minimizeView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/line.png"));
			}
		});

		this.minimizeView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/line-mousemoved.png"));
			}
		});

		minimizeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				gameController.getStage().setIconified(true);

			}
		});
	}

	private void closeButtonInit() {
		this.crossView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				crossView.setImage(new Image("/image/cross-mousemoved.png"));
			}
		});

		this.crossView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				crossView.setImage(new Image("/image/cross.png"));
			}
		});

		crossView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.exit(0);
			}
		});
	}

	private void playButtonInit() {
		this.playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playButton.setImage(new Image("/image/play.png"));
			}
		});

		this.playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playButton.setImage(new Image("/image/play-entered.png"));
			}
		});

		playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onPlay();
			}
		});
	}
}
