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
		mob.setMoveCallback(this::move);
		mob.setDamageCallback(this::doDamage);
		this.setFitHeight(MOB_SIZE);
		this.setFitWidth(MOB_SIZE);
		this.setVisible(false);
		mob.addKillCallback(this::onKill);


	}

	private String getMobImagePath(String id, String suffix) {
		return "image/mob/" + id + "-" + suffix + ".gif";
	}

	public void move() {
		Platform.runLater(() -> {
			this.setVisible(true);
			Position position = mob.getPosition();
			this.setX(position.getX() - MOB_SIZE / 2);
			this.setY(position.getY() - MOB_SIZE);
			if (mob.getPathPosition().isForward()){
				setScaleX(1);
			}
			else setScaleX(-1);

		});

	}

	public void doDamage() {
//		Platform.runLater(() -> {
//			this.setOpacity(mob.getHealth() / mob.getType().getHealth());
//		});
	}

	public void onKill() {
		Platform.runLater(() -> setImage(new Image(getMobImagePath(mob.getType().toString().toLowerCase(), "dead"))));
	}

}
