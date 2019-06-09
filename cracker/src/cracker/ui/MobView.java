package cracker.ui;

import cracker.logic.Mob;
import cracker.logic.Position;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MobView extends ImageView {
    private Mob mob;

    public MobView(Mob mob) {
        this.mob = mob;
    }

    public MobView(Image image, Mob mob) {
        super(image);
        this.mob = mob;
        mob.setCallback(() -> move());
        this.setFitHeight(48);
        this.setFitWidth(48);
        this.setVisible(false);
    }

    public void move(){
        Platform.runLater(() -> {
            this.setVisible(true);
            Position position = mob.getPosition();
            this.setX(position.getX());
            this.setY(position.getY());
            if (mob.isKilled()) {
                setVisible(false);
            }
        });
    }

    public Mob getMob() {
        return mob;
    }
}
