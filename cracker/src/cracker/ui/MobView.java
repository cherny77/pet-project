package cracker.ui;

import cracker.logic.Mob;
import cracker.logic.Position;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MobView extends ImageView {
	public final static double MOB_SIZE = 48;

	private Mob mob;

	public MobView(Image image, Mob mob) {
		super(image);
		this.mob = mob;
		mob.setCallback(() -> move());
		this.setFitHeight(MOB_SIZE);
		this.setFitWidth(MOB_SIZE);
		this.setVisible(false);
	}

	public void move() {
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
