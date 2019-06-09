package cracker.controller;

import cracker.logic.*;
import cracker.ui.AddTowerButton;
import cracker.ui.MobView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class GameController {

    AddTowerButton addBowTowerButton;
    AddTowerButton addMagicTowerButton;
    AddTowerButton addBombTowerButton;
    @FXML
    private AnchorPane pane;
    private Game game;
    private ImageView towerTemplate = new ImageView(new Image("/image/green-tower.png"));
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

    public void init() {
        ImageView imageView = new ImageView(new Image("/image/background.png"));
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(768);
        imageView.setFitWidth(1024);
        pane.getChildren().add(imageView);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void init(Game game) {
        addBowTowerButton = new AddTowerButton(new Image("image/tower-icon.png"), new Image("image/tower-icon-mousemoved.png"),
                new Image("image/tower-icon-selected.png"), this, 924, 770, 78, 74);
        addMagicTowerButton = new AddTowerButton(new Image("image/magic-tower-icon.png"), new Image("image/magic-tower-icon-mousemoved.png"),
                new Image("image/magic-tower-icon-selected.png"), this, 852, 772, 78, 74);
        addBombTowerButton = new AddTowerButton(new Image("image/tower-bomb-icon.png"), new Image("image/tower-bomb-icon-mousemoved.png"),
                new Image("image/tower-bomb-icon-selected.png"), this, 780, 772, 78, 74);

        towerTemplate.setFitHeight(70);
        towerTemplate.setFitWidth(70);
        pane.getChildren().add(towerTemplate);
        towerTemplate.setVisible(false);
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

    public void onTower1() {
        if (addBowTowerButton.isSelected()) {
            pane.setCursor(Cursor.NONE);
        }
    }

    public void setBinding() {
        coinImageInit();
        minimizeButtonInit();
        closeButtonInit();


        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                heartLabel.setText(String.valueOf(game.getMap().getRemainedLives()));
                if (addBowTowerButton.isSelected()) {
                    dragTower(event);
                }
            }
        });

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (addBowTowerButton.isSelected() && event.getPickResult().getIntersectedNode() != addBowTowerButton) {
                    addTower(event);
                }
            }
        });
    }

    public void dragTower(MouseEvent event) {
        towerTemplate.setVisible(true);
        towerTemplate.toFront();
        towerTemplate.setX(event.getSceneX() - towerTemplate.getFitWidth() / 2);
        towerTemplate.setY(event.getSceneY() - towerTemplate.getFitHeight() / 2);
    }

    public void addTower(MouseEvent event) {
        ImageView imageView = new ImageView(new Image("/image/tower.png"));
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setX(event.getSceneX() - imageView.getFitWidth() / 2);
        imageView.setY(event.getSceneY() - imageView.getFitHeight() / 2);
        pane.getChildren().add(imageView);
        towerTemplate.setVisible(false);
        Image image = new Image("/image/cursor.png");
        pane.setCursor(new ImageCursor(image, 100, 100));
        addBowTowerButton.setSelected(false);
        Tower tower = new Tower(TowerType.Type1, new Position(imageView.getX(), imageView.getY()));
        game.getMap().addTower(tower);

    }

    public Stage getStage() {
        return stage;
    }

    public Game getGame() {
        return game;
    }
}
