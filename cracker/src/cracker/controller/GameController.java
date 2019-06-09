package cracker.controller;

import cracker.logic.*;
import cracker.ui.MobView;
import javafx.animation.PathTransition;
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

public class GameController {
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
        int size = pane.getChildren().size();
        for (Wave wave : waves) {
            for (Mob mob : wave.getMobs()) {
                MobView mobView;
                if (mob.getType().equals(MobType.GHOST))
                    mobView = new MobView(ghostImage, mob);
                else if (mob.getType().equals(MobType.SKELETON)) {
                    mobView = new MobView(skeletonImage, mob);
                } else mobView = new MobView(slimeImage, mob);
                pane.getChildren().add(mobView);

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
        coinImageInit();
        minimizeButtonInit();
        closeButtonInit();
        setTowerBarBinding();

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

    private String getTowerImagePath(String id, String suffix) {
        return "image/tower/" + id + "-" + suffix + ".png";
    }


    private void setTowerBarBinding() {
        for (Node node : towerBar.getChildren()) {
            ImageView towerView = (ImageView) node;
            towerView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    selectedTower = event.getPickResult().getIntersectedNode();
                    Image image = new Image(getTowerImagePath(selectedTower.getId(), "selected"));
                    towerView.setImage(image);
                    pane.setCursor(Cursor.NONE);
                }
            });
            towerView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (selectedTower == null){
                        Image image = new Image(getTowerImagePath(towerView.getId(), "exited"));
                        towerView.setImage(image);
                    }
                }
            });
            towerView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (selectedTower == null){
                        Image image = new Image(getTowerImagePath(towerView.getId(), "entered"));
                        towerView.setImage(image);
                    }
                }
            });
        }
    }

    public void dragTower(MouseEvent event) {
        towerCursor.setImage(new Image(getTowerImagePath(selectedTower.getId(),"cursor-enabled")));
        towerCursor.setVisible(true);
        towerCursor.toFront();
        towerCursor.setX(event.getSceneX() - towerCursor.getFitWidth() / 2);
        towerCursor.setY(event.getSceneY() - towerCursor.getFitHeight() / 2);
    }

    public void addTower(MouseEvent event) {
        String imagePath = getTowerImagePath(selectedTower.getId(),"tower");
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setX(event.getSceneX() - imageView.getFitWidth() / 2);
        imageView.setY(event.getSceneY() - imageView.getFitHeight() / 2);
        pane.getChildren().add(imageView);
        towerCursor.setVisible(false);
        pane.setCursor(new ImageCursor(new Image("/image/cursor.png"), 100, 100));
        ((ImageView)selectedTower).setImage(new Image(getTowerImagePath(selectedTower.getId(),"exited")));
        selectedTower = null;
        Tower tower = new Tower(TowerType.ARROW, new Position(imageView.getX(), imageView.getY()), game.getMap());
//        tower.setCallback(p -> );
        game.getMap().addTower(tower);

    }

    public void addTower1(MouseEvent event) {
        Position startPositon = new Position(500, 500);
        Position endPositon = new Position(700, 700);
        double controlDeltaX = 50;
        double controlDeltaY = 50;
        Image image = new Image("image/arrow.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(11);
        imageView.setFitWidth(35);
        imageView.setX(startPositon.getX());
        imageView.setX(startPositon.getY());
        pane.getChildren().add(imageView);

        javafx.scene.shape.Path path = new javafx.scene.shape.Path();

        MoveTo moveTo = new MoveTo(startPositon.getX(), startPositon.getY());

        CubicCurveTo cubicCurveTo = new CubicCurveTo(startPositon.getX() - controlDeltaX,
                startPositon.getY() - controlDeltaY, endPositon.getX() + controlDeltaX,
                endPositon.getY() - controlDeltaY, endPositon.getX(), endPositon.getY());
        path.getElements().add(moveTo);
        path.getElements().add(cubicCurveTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }

//    public void onFire(Projectile projectile) {
//        Image image = new Image("image/arrow.png");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(11);
//        imageView.setFitWidth(35);
//        imageView.setX(startPositon.getX());
//        imageView.setX(startPositon.getY());
//        pane.getChildren().add(imageView);
//
//        javafx.scene.shape.Path path = new javafx.scene.shape.Path();
//
//        MoveTo moveTo = new MoveTo(startPositon.getX(), startPositon.getY());
//
//        CubicCurveTo cubicCurveTo = new CubicCurveTo(startPositon.getX() - controlDeltaX,
//                startPositon.getY() - controlDeltaY, endPositon.getX() + controlDeltaX,
//                endPositon.getY() - controlDeltaY, endPositon.getX(), endPositon.getY());
//        path.getElements().add(moveTo);
//        path.getElements().add(cubicCurveTo);
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(1000));
//        pathTransition.setNode(imageView);
//        pathTransition.setPath(path);
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        pathTransition.setCycleCount(1);
//        pathTransition.setAutoReverse(false);
//        pathTransition.play();
//    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Game getGame() {
        return game;
    }
}
