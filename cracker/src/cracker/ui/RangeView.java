package cracker.ui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RangeView {
    private double x;
    private double y;
    private double radius;
    private boolean isVisible;
    private Circle circle;

    public RangeView(double x, double y, double radius, AnchorPane gamePane) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setStroke(new Color(0.9,0,0,0.5));
        circle.setStrokeWidth(5);

        circle.setFill(new Color(0,0,0,0));
        gamePane.getChildren().add(circle);
        circle.setMouseTransparent(true);
        circle.setVisible(false);

    }

    public void setVisible(boolean visible){
        circle.setVisible(visible);
        if (visible) circle.setOpacity(10);
        circle.toFront();
        isVisible = visible;
    }

    public boolean isVisible(){
        return isVisible;
    }



}
