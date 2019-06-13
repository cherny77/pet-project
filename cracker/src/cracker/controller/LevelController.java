package cracker.controller;

import cracker.level.AbstractLevel;
import cracker.model.*;
import cracker.ui.MobView;
import cracker.ui.RangeView;
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
	private ImageView goToMenuButton;
	@FXML
	private ImageView playAgainBtn;
	@FXML
	private ImageView gearWheel;
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
	private ImageView coinView;
	@FXML
	private ImageView controlFrame;
	private Stage stage;
	private boolean placeIsFree;

	@FXML
	private AnchorPane progectilePane;

	public AnchorPane getPane() {
		return pane;
	}

	public void init(AbstractLevel level) {
		towerCursor = new ImageView();
		towerCursor.setFitHeight(70);
		towerCursor.setFitWidth(70);
		gamePane.getChildren().add(towerCursor);
		towerCursor.setVisible(false);
		this.level = level;

		Image ghostImage = new Image("/image/ghost.gif");
		Image skeletonImage = new Image("/image/skeleton.gif");
		Image slimeImage = new Image("/image/slime.gif");

		List<Wave> waves = level.getMap().getWaves();
		for (Wave wave : waves) {
			for (Mob mob : wave.getMobs()) {
				MobView mobView;
				if (mob.getType().equals(MobType.GHOST))
					mobView = new MobView(ghostImage, mob);
				else if (mob.getType().equals(MobType.SKELETON)) {
					mobView = new MobView(skeletonImage, mob);
				} else
					mobView = new MobView(slimeImage, mob);
				gamePane.getChildren().add(mobView);

			}
		}
	}

	private void coinImageInit() {
		coinView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				coinView.setImage(new Image("/image/coin.png"));
			}
		});

		coinView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				coinView.setImage(new Image("/image/coin.gif"));
			}
		});
	}

	private void minimizeButtonInit() {
		minimizeView.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/line.png"));
			}
		});

		minimizeView.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				minimizeView.setImage(new Image("/image/line-mousemoved.png"));
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
				crossView.setImage(new Image("/image/cross-mousemoved.png"));
			}
		});

		crossView.setOnMouseEntered(new EventHandler<MouseEvent>() {
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

	public void setBinding() {
		setPlayAgainBtn();
		coinImageInit();
		minimizeButtonInit();
		closeButtonInit();
		setTowerBarBinding();
		setGoToMenuBtn();

		pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				heartLabel.setText(String.valueOf(level.getMap().getRemainedLives()));
				if (selectedTower != null) {
					dragTower(event);
				}
			}
		});

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (selectedTower != null && event.getPickResult().getIntersectedNode() != selectedTower &&
						placeIsFree) {
					addTower(event);
				}
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
					selectedTower = event.getPickResult().getIntersectedNode();
					Image image = new Image(getTowerButtonImagePath(selectedTower.getId(), "selected"));
					towerView.setImage(image);
					pane.setCursor(Cursor.NONE);
					for (Node node1 : towerBar.getChildren()) {
						if (node1 != selectedTower) {
							Image image1 = new Image(getTowerButtonImagePath(node1.getId(), "exited"));
							((ImageView) node1).setImage(image1);
						}
					}
				}
			});
			towerView.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (selectedTower == null) {
						Image image = new Image(getTowerButtonImagePath(towerView.getId(), "exited"));
						towerView.setImage(image);
					}
				}
			});
			towerView.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (selectedTower == null) {
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
		gamePane.getChildren().add(imageView);

		towerCursor.setVisible(false);
		pane.setCursor(new ImageCursor(new Image("/image/cursor.png")));
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

		RangeView rangeView = new RangeView(imageView.getX() - -imageView.getFitWidth() / 2,
				imageView.getY() - -imageView.getFitHeight() / 2, tower.getType().getRange(), gamePane);
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
		tower.setCallback(this::onFire);
	}

	private String getProjectileImagePath(ProjectileType type) {
		return "image/projectile/" + type.toString().toLowerCase() + ".gif";
	}

	public void onFire(Projectile projectile) {
		System.out.println(projectile);
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
				playAgainBtn.setImage(new Image("image/play-again-button-entered.png"));
			}
		});

		playAgainBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playAgainBtn.setImage(new Image("image/play-again-button.png"));
			}
		});

		playAgainBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	}

	private void setGoToMenuBtn() {
		goToMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				goToMenuButton.setImage(new Image("image/go-to-menu-btn-entered.png"));
			}
		});

		goToMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				goToMenuButton.setImage(new Image("image/go-to-menu-btn.png"));
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

}

