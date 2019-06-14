package cracker.ui;

import cracker.model.Mob;
import cracker.model.Position;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MobView extends ImageView {
	public final static double MOB_SIZE = 48;

	private Mob mob;

	public MobView(Image image, Mob mob) {
		super(image);
		this.mob = mob;
		if (!mob.isKilled())
		mob.setMoveCallback(() -> move());
		mob.setDamageCallback(() -> doDamage());
		this.setFitHeight(MOB_SIZE);
		this.setFitWidth(MOB_SIZE);
		this.setVisible(false);
	}

	private String getMobImagePath(String id, String suffix) {
		return "image/" + id + "-" + suffix + ".gif";
	}

	public void move() {
		Platform.runLater(() -> {
			if (mob.isKilled()) {
				setImage(new Image(getMobImagePath(mob.getType().toString().toLowerCase(), "dead")));
			}
			else {
				this.setVisible(true);
				Position position = mob.getPosition();
				this.setX(position.getX());
				this.setY(position.getY());
			}
		});
	}

	public void doDamage() {
//		Platform.runLater(() -> {
//			this.setOpacity(mob.getHealth() / mob.getType().getHealth());
//		});
	}

}
