package cracker.ui;


import cracker.controller.GameController;
import cracker.logic.TowerType;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AddTowerButton extends ImageView {
    TowerType type;
    private Image originImage;
    private Image enteredView;
    private Image selectedView;
    private boolean isSelected = false;
    private GameController controller;

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
        if(!isSelected){
            setImage(enteredView);
        }
    }

    public boolean isSelected(){
        return isSelected;
    }

    public AddTowerButton(Image image, Image enteredView, Image selectedView, GameController controller, int x, int y, int height, int width) {
        super(image);
        this.originImage = image;
        this.enteredView = enteredView;
        this.selectedView = selectedView;
        this.controller = controller;
        this.setFitHeight(height);
        this.setFitWidth(width);
        this.setX(x);
        this.setY(y);
        setVisible(true);
        controller.getPane().getChildren().add(this);
        init();
    }



    private void init(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("kkee");
                setSelected(true);
                setImage(selectedView);
                controller.onTower1();


            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("kek");
                if (!isSelected)
                setImage(originImage);

            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isSelected)
                setImage(enteredView);
            }
        });


    }

    }

