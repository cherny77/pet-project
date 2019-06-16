package cracker.controller;

import cracker.level.AbstractLevel;
import cracker.level.FirstLevel;
import cracker.model.*;
import cracker.ui.MobView;
import cracker.ui.RangeView;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LevelController {
	public final static double TOWER_WIDTH = 90;
	public final static double TOWER_HEIGHT = 90;

	@FXML
	private AnchorPane mobTowerPane;
	@FXML
	private Label waveLabel;
	@FXML
	private Label coinLabel;
	@FXML
	private ImageView coinView;
	@FXML
	private ImageView goToMenuButton;
	@FXML
	private ImageView playAgainBtn;
	@FXML
	private Node selectedTower;
	@FXML
	private AnchorPane towerBar;
	@FXML
	private AnchorPane gamePane;
	@FXML
	private AnchorPane pane;
	private AbstractLevel level;
	private ImageView towerCursor;
	@FXML
	private Label heartLabel;
	@FXML
	private ImageView crossView;
	@FXML
	private ImageView minimizeView;
	@FXML
	private ImageView controlFrame;
	@FXML
	private Stage stage;
	private boolean placeIsFree;
	private WelcomeController welcomeController;

	@FXML
	private AnchorPane progectilePane;
	@FXML
	private AnchorPane rangePane;

	@FXML
	private AnchorPane winPane;

	@FXML
	private ImageView loseView;
	@FXML
	private ImageView winView;
	@FXML
	private ImageView background;

	public AnchorPane getPane() {
		return pane;
	}

	public void setWelcomeController(WelcomeController welcomeController) {
		this.welcomeController = welcomeController;
	}

	public void init(AbstractLevel level) {
		System.out.println(level.getClass().getSimpleName());
		level.setCallback(() -> onFinish());
		level.getMap().setWaveCallback(() -> setWaveLabel());
		coinLabel.setText(String.valueOf(level.getMap().getMoney()));
		heartLabel.setText(String.valueOf(level.getMap().getLives()));

		towerCursor = new ImageView();
		towerCursor.setFitHeight(70);
		towerCursor.setFitWidth(70);
		mobTowerPane.getChildren().add(towerCursor);
		towerCursor.setVisible(false);
		this.level = level;
		background.setImage(getMapImage(this.level.getClass().getSimpleName()));
		List<Wave> waves = level.getMap().getWaves();
		for (Wave wave : waves) {
			for (Mob mob : wave.getMobs()) {
				MobView mobView = new MobView(getMobImage(mob.getType().toString()), mob);
				mobTowerPane.getChildren().add(mobView);
				mob.setFinishCallback(() -> setLives());
				mob.addKillCallback(() -> addMoney(mob));
			}
		}
	}

	private static Image getMobImage(String id){
		return new Image("/image/mob/" + id.toLowerCase() + ".gif");
	}

	private static Image getMapImage(String id){
		System.out.println("/image/level/" + id + "-background.png");
		return new Image("/image/level/" + id + "-background.png");
	}

	private void coinImageInit() {
		coinView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				coinView.setImage(new Image("/image/info-panel/coin-icon.png"));
			}
		});

		coinView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				coinView.setImage(new Image("/image/info-panel/coin-icon-animated.gif"));
			}
		});
	}

	private void setWaveLabel() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				waveLabel.setText(level.getMap().getWaveNumber() + "/" + level.getMap().getWaves().size());
			}
		});
	}

	private void minimizeButtonInit() {
		minimizeView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/window/line.png"));
			}
		});

		minimizeView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/window/line-entered.png"));
			}
		});

		minimizeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setIconified(true);

			}
		});
	}

	private void closeButtonInit() {
		crossView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				crossView.setImage(new Image("/image/window/cross-entered.png"));
			}
		});

		crossView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				crossView.setImage(new Image("/image/window/cross.png"));
			}
		});

		crossView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.exit(0);
			}
		});
	}

	public void setBinding() {
		setPlayAgainBtn();
		coinImageInit();
		minimizeButtonInit();
		closeButtonInit();
		setTowerBarBinding();
		setGoToMenuBtn();

		controlFrame.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pane.setCursor(new ImageCursor(new Image("/image/window/cursor.png")));
			}
		});
		gamePane.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (selectedTower != null) {
					pane.setCursor(Cursor.NONE);
					dragTower(event);
				}
			}
		});

		gamePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (selectedTower != null && event.getPickResult().getIntersectedNode() != selectedTower &&
						placeIsFree) {
					addTower(event);
				}
			}
		});
	}

	private void setLives() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				heartLabel.setText(String.valueOf(level.getMap().getRemainedLives()));
			}
		});

	}

	private void addMoney(Mob mob) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				coinLabel.setText(String.valueOf(level.getMap().getAddMoney()));
			}
		});
	}

	private String getTowerButtonImagePath(String id, String suffix) {
		return "image/tower/" + id + "-" + suffix + ".png";
	}

	private String getTowerImagePath(String id, String suffix) {
		return "image/tower/" + id + "-" + suffix + ".gif";
	}

	private void setTowerBarBinding() {
		for (Node node : towerBar.getChildren()) {
			ImageView towerView = (ImageView) node;
			towerView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (selectedTower != towerView) {
						selectedTower = event.getPickResult().getIntersectedNode();
						Image image = new Image(getTowerButtonImagePath(selectedTower.getId(), "selected"));

						towerView.setImage(image);
						for (Node node1 : towerBar.getChildren()) {
							if (node1 != selectedTower) {
								Image image1 = new Image(getTowerButtonImagePath(node1.getId(), "exited"));
								((ImageView) node1).setImage(image1);

							}
						}
					} else {
						Image image = new Image(getTowerButtonImagePath(selectedTower.getId(), "exited"));
						selectedTower = null;
						towerView.setImage(image);
						towerCursor.setVisible(false);
					}
				}

			});
			towerView.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (selectedTower != towerView) {
						Image image = new Image(getTowerButtonImagePath(towerView.getId(), "exited"));
						towerView.setImage(image);
					}
				}
			});
			towerView.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					pane.setCursor(new ImageCursor(new Image("/image/window/cursor.png")));

					if (selectedTower != towerView) {
						Image image = new Image(getTowerButtonImagePath(towerView.getId(), "entered"));
						towerView.setImage(image);
					}
				}
			});
		}
	}

	public void dragTower(MouseEvent event) {

		towerCursor.setFitHeight(100);
		towerCursor.setFitWidth(100);
		if (isFreePlace(new Position(event.getSceneX(), event.getSceneY()), towerCursor))
			towerCursor.setImage(new Image(getTowerButtonImagePath(selectedTower.getId(), "cursor-enabled")));
		else
			towerCursor.setImage(new Image(getTowerButtonImagePath(selectedTower.getId(), "cursor-disabled")));
		towerCursor.setVisible(true);
		towerCursor.toFront();
		towerCursor.setX(event.getSceneX() - towerCursor.getFitWidth() / 2);
		towerCursor.setY(event.getSceneY() - towerCursor.getFitHeight() / 2);

	}

	public void addTower(MouseEvent event) {

		String imagePath = getTowerImagePath(selectedTower.getId(), "tower");
		Image image = new Image(imagePath);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(TOWER_HEIGHT);
		imageView.setFitWidth(TOWER_WIDTH);
		imageView.setX(event.getSceneX() - imageView.getFitWidth() / 2);
		imageView.setY(event.getSceneY() - imageView.getFitHeight() / 2);
		mobTowerPane.getChildren().add(imageView);

		towerCursor.setVisible(false);

		((ImageView) selectedTower).setImage(new Image(getTowerButtonImagePath(selectedTower.getId(), "exited")));
		Tower tower;
		if (selectedTower.getId().toLowerCase().contains("bomb")) {
			tower = new Tower(TowerType.BOMB, new Position(imageView.getX(), imageView.getY()), level.getMap());
		} else if (selectedTower.getId().toLowerCase().contains("magic")) {
			tower = new Tower(TowerType.MAGIC, new Position(imageView.getX(), imageView.getY()), level.getMap());
		} else {
			tower = new Tower(TowerType.ARROW, new Position(imageView.getX(), imageView.getY()), level.getMap());
		}
		selectedTower = null;
		pane.setCursor(new ImageCursor(new Image("/image/window/cursor.png")));
		RangeView rangeView = new RangeView(imageView.getX() + imageView.getFitWidth() / 2,
				imageView.getY() - -imageView.getFitHeight() / 2, tower.getType().getRange(), rangePane);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (rangeView.isVisible()) {
					rangeView.setVisible(false);
				} else if (!rangeView.isVisible()) {
					rangeView.setVisible(true);
				}
			}
		});
		level.getMap().addTower(tower);
		coinLabel.setText(String.valueOf(level.getMap().getAddMoney()));
		tower.setCallback(this::onFire);
	}

	private String getProjectileImagePath(ProjectileType type) {
		return "image/projectile/" + type.toString().toLowerCase() + ".gif";
	}

	public void onFire(Projectile projectile) {
//		System.out.println(projectile);
		Image image = new Image(getProjectileImagePath(projectile.getProjectileType()));
		ImageView imageView = new ImageView(image);
		imageView.setX(projectile.getStartPosition().getX());
		imageView.setX(projectile.getStartPosition().getY());
		Platform.runLater(() -> progectilePane.getChildren().add(imageView));
		javafx.scene.shape.Path path = new javafx.scene.shape.Path();
		MoveTo moveTo = new MoveTo(projectile.getStartPosition().getX() + TOWER_WIDTH / 2,
				projectile.getStartPosition().getY());
		Position controlPoint = new Position(
				(projectile.getStartPosition().getX() + TOWER_WIDTH / 2 + projectile.getEndPosition().getX() +
						MobView.MOB_SIZE / 2) / 2,
				(projectile.getStartPosition().getY() + projectile.getEndPosition().getY() + MobView.MOB_SIZE / 2) / 2 -
						projectile.getProjectileType().getPlunging());
		CubicCurveTo cubicCurveTo =
				new CubicCurveTo(controlPoint.getX(), controlPoint.getY(), controlPoint.getX(), controlPoint.getY(),
						projectile.getEndPosition().getX() + MobView.MOB_SIZE / 2,
						projectile.getEndPosition().getY() + MobView.MOB_SIZE / 2);
		path.getElements().add(moveTo);
		path.getElements().add(cubicCurveTo);
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(projectile.getDuration()));
		pathTransition.setNode(imageView);
		pathTransition.setPath(path);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(1);
		pathTransition.setAutoReverse(false);
		pathTransition.play();
		ScheduledExecutorService executor = getLevel().getExecutor();
		if (projectile.getProjectileType().equals(ProjectileType.BOMB)) {
			executor.schedule(
					() -> Platform.runLater(() -> imageView.setImage(new Image("image/projectile/bomb-explosion.gif"))),
					projectile.getDuration(), TimeUnit.MILLISECONDS);
			executor.schedule(() -> Platform.runLater(() -> progectilePane.getChildren().remove(imageView)),
					projectile.getDuration() + 1000, TimeUnit.MILLISECONDS);
		} else if (projectile.getProjectileType().equals(ProjectileType.MAGIC)) {
			executor.schedule(() -> Platform
							.runLater(() -> imageView.setImage(new Image("image/projectile/magic-explosion.gif"))),
					projectile.getDuration(), TimeUnit.MILLISECONDS);
			executor.schedule(() -> Platform.runLater(() -> progectilePane.getChildren().remove(imageView)),
					projectile.getDuration() + 1000, TimeUnit.MILLISECONDS);
		} else
			executor.schedule(() -> Platform.runLater(() -> progectilePane.getChildren().remove(imageView)),
					projectile.getDuration(), TimeUnit.MILLISECONDS);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public AbstractLevel getLevel() {
		return level;
	}

	private void setPlayAgainBtn() {
		playAgainBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playAgainBtn.setImage(new Image("image/end-game/play-again-btn-entered.png"));
			}
		});

		playAgainBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playAgainBtn.setImage(new Image("image/end-game/play-again-btn.png"));
			}
		});

		playAgainBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println(gamePane.getChildren());
				clear();
				winPane.setVisible(false);
				level = new FirstLevel();
				level.init();
				init(level);
				setBinding();
				level.start();
			}
		});
	}

	private void clear() {
		mobTowerPane.getChildren().clear();
		progectilePane.getChildren().clear();
	}

	private void setGoToMenuBtn() {
		goToMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				goToMenuButton.setImage(new Image("image/end-game/go-to-menu-btn-entered.png"));
			}
		});

		goToMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				goToMenuButton.setImage(new Image("image/end-game/go-to-menu-btn.png"));
			}
		});

		goToMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				welcomeController.getWelcomePane().setVisible(true);
				FadeTransition fade = new FadeTransition();
				fade.setDuration(Duration.millis(1000));
				fade.setFromValue(0);
				fade.setToValue(100);
				fade.setCycleCount(1);
				fade.setNode(welcomeController.getWelcomePane());
				fade.play();
			}
		});
	}

	private ArrayList<Double> checkPlace(Position position) {
		ArrayList<Double> distances = new ArrayList<>();
		for (Path path : level.getMap().getPaths()) {
			for (int i = 1; i < path.getPositions().size(); i++) {
				double x = position.getX();
				double y = position.getY();
				double x1 = path.getPositions().get(i - 1).getX();
				double x0 = path.getPositions().get(i).getX();
				double y1 = path.getPositions().get(i - 1).getY();
				double y0 = path.getPositions().get(i).getY();
				if ((position.getX() < Math.max(x1, x0) && position.getX() > Math.min(x1, x0)) ||
						(position.getY() < Math.max(y1, y0) && position.getX() > Math.min(y1, y0))) {
					double d = ((y0 - y1) * x + (x1 - x0) * y + (x0 * y1 - x1 * y0)) /
							Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
					distances.add(d);
				} else {
					distances.add(Math.min(Position.getDistance(position, path.getPositions().get(i)),
							Position.getDistance(position, path.getPositions().get(i - 1))));
				}
			}
		}
		return distances;
	}

	private boolean isFreePlace(Position position, ImageView imageView) {
		ArrayList<Double> distances = checkPlace(position);
		for (Double distance : distances) {
			System.out.println(distance);
			if (imageView.getFitHeight() / 2 >= Math.abs(distance)) {
				placeIsFree = false;
				return false;
			}

		}
		placeIsFree = true;
		return true;
	}

	public void onFinish() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				FadeTransition opacityAnimation = new FadeTransition(Duration.millis(1000), winPane);
				loseView.setVisible(!level.isWin());
				winView.setVisible(level.isWin());
				winPane.setVisible(true);
				winPane.setOpacity(0);
				opacityAnimation.setFromValue(0);
				opacityAnimation.setToValue(100);
				opacityAnimation.play();

			}
		});
	}

}

