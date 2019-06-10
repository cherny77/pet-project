package cracker.controller;

import cracker.logic.*;
import cracker.ui.MobView;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
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

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {

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
    private Game game;
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
    private ImageView addTowerImage;
    private Stage stage;

    @FXML
    private AnchorPane progectilePane;

    public AnchorPane getPane() {
        return pane;
    }

    public void init(Game game) {
        towerCursor = new ImageView();
        towerCursor.setFitHeight(70);
        towerCursor.setFitWidth(70);
        gamePane.getChildren().add(towerCursor);
        towerCursor.setVisible(false);
        this.game = game;

        Image ghostImage = new Image("/image/ghost.gif");
        Image skeletonImage = new Image("/image/skeleton.gif");
        Image slimeImage = new Image("/image/slime.gif");

        List<Wave> waves = game.getMap().getWaves();
        for (Wave wave : waves) {
            for (Mob mob : wave.getMobs()) {
                MobView mobView;
                if (mob.getType().equals(MobType.GHOST))
                    mobView = new MobView(ghostImage, mob);
                else if (mob.getType().equals(MobType.SKELETON)) {
                    mobView = new MobView(skeletonImage, mob);
                } else mobView = new MobView(slimeImage, mob);
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
        setOptionButton();
        coinImageInit();
        minimizeButtonInit();
        closeButtonInit();
        setTowerBarBinding();
        setGoToMenuBtn();

        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                heartLabel.setText(String.valueOf(game.getMap().getRemainedLives()));
                if (selectedTower != null) {
                    dragTower(event);
                }
            }
        });

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedTower != null && event.getPickResult().getIntersectedNode() != selectedTower) {
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
        towerCursor.setImage(new Image(getTowerButtonImagePath(selectedTower.getId(), "cursor-enabled")));
        towerCursor.setFitHeight(90);
        towerCursor.setFitWidth(90);
        towerCursor.setVisible(true);
        towerCursor.toFront();
        towerCursor.setX(event.getSceneX() - towerCursor.getFitWidth() / 2);
        towerCursor.setY(event.getSceneY() - towerCursor.getFitHeight() / 2);
    }

    public void addTower(MouseEvent event) {
        String imagePath = getTowerImagePath(selectedTower.getId(), "tower");
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);
        imageView.setX(event.getSceneX() - imageView.getFitWidth() / 2);
        imageView.setY(event.getSceneY() - imageView.getFitHeight() / 2);
        gamePane.getChildren().add(imageView);
        towerCursor.setVisible(false);
        pane.setCursor(new ImageCursor(new Image("/image/cursor.png"), 100, 100));
        ((ImageView) selectedTower).setImage(new Image(getTowerButtonImagePath(selectedTower.getId(), "exited")));
        selectedTower = null;
        Tower tower = new Tower(TowerType.ARROW, new Position(imageView.getX(), imageView.getY()), game.getMap());
        game.getMap().addTower(tower);
        tower.setCallback(this::onFire);
    }

    private String getProjectfileImagePath(ProjectileType type) {
        return "image/projectile/" + type.toString().toLowerCase() + ".png";
    }

    public void onFire(Projectile projectile) {
        Image image = new Image(getProjectfileImagePath(projectile.getProjectileType()));
        ImageView imageView = new ImageView(image);
        imageView.setX(projectile.getStartPosition().getX());
        imageView.setX(projectile.getStartPosition().getY());
        Platform.runLater(() -> progectilePane.getChildren().add(imageView));
        javafx.scene.shape.Path path = new javafx.scene.shape.Path();
        MobView mobView = findMobView(projectile.getTargetMob());
        double deltaY;
        double deltaX;
        if (mobView != null) {
            deltaY = mobView.getImage().getHeight() / 2;
            deltaX = mobView.getImage().getWidth() / 2;
        } else {
            deltaY = 0;
            deltaX = 0;
        }

        MoveTo moveTo = new MoveTo(projectile.getStartPosition().getX(), projectile.getStartPosition().getY());
        CubicCurveTo cubicCurveTo = new CubicCurveTo(projectile.getStartPosition().getX()
                - projectile.getProjectileType().getControlDeltaX(),
                projectile.getStartPosition().getY() - projectile.getProjectileType().getControlDeltaY(),
                projectile.getEndPosition().getX() + deltaX + projectile.getProjectileType().getControlDeltaX(),
                projectile.getEndPosition().getY() + deltaY - projectile.getProjectileType().getControlDeltaY(),
                projectile.getEndPosition().getX() + deltaX, projectile.getEndPosition().getY() + deltaY);
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
        ScheduledExecutorService executor = getGame().getExecutor();
        executor.schedule(() -> Platform.runLater(() -> progectilePane.getChildren().remove(imageView)), projectile.getDuration(), TimeUnit.MILLISECONDS);
    }

    public MobView findMobView(Mob mob) {
        for (Node node : gamePane.getChildren()) {
            if (node instanceof MobView) {
                MobView mobView = (MobView) node;
                if (mob == mobView.getMob()) {
                    return mobView;
                }
            }
        }
        return null;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Game getGame() {
        return game;
    }

    private void setPlayAgainBtn(){
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
    }

    private void setGoToMenuBtn(){
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

