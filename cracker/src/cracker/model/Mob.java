package cracker.model;

import cracker.level.AbstractLevel;

import java.util.HashSet;
import java.util.Set;

public class Mob {
	private final MobType type;
	private final Path path;
	private PathPosition pathPosition;
	private long currentTime;
	private double health;
	private Runnable moveCallback;
	private Runnable damageCallback;
	private Runnable finishCallback;
	private Set<Runnable> killCallbacks;

	public Mob(MobType type, Path path) {
		this.type = type;
		this.path = path;
		pathPosition = new PathPosition(path);
		health = type.getHealth();
		killCallbacks = new HashSet<>();
	}

	public void setMoveCallback(Runnable moveCallback) {
		this.moveCallback = moveCallback;
	}

	public void setFinishCallback(Runnable finishCallback) {
		this.finishCallback = finishCallback;
	}

	public void addKillCallback(Runnable killCallback) {
		killCallbacks.add(killCallback);
	}

	public void move(long time) {
		if (isFinished() || isKilled())
			return;
		double distance = (time - currentTime) * type.getSpeed();
		pathPosition.move(distance);
		currentTime = time;
//        System.out.println(getPosition());
		if (moveCallback != null) {
			moveCallback.run();
		}
		if (isFinished() && !isKilled()) {
			onFinished();
		}

	}

	public void doDamage(double damage) {
		if (isKilled() || isFinished())
			return;
		health -= damage;
		if (damageCallback != null) {
			damageCallback.run();
		}
		if (isKilled())
			onKilled();
	}

	public MobType getType() {
		return type;
	}

	public Position getPosition() {
		return pathPosition.getPosition();
	}

	public double getProgress() {
		return pathPosition.getTotalProgress();
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isKilled() {
		return health <= 0;
	}

	public boolean isFinished() {
		return pathPosition.isFinished();
	}

	public void onFinished() {
		if (finishCallback != null) {
			finishCallback.run();
		}
	}

	public void onKilled() {
		for (Runnable killCallback : killCallbacks) {
			killCallback.run();
		}

	}

	public Position getFuturePosition(double distance) {
		return pathPosition.getFuturePosition(distance);
	}

	public void setDamageCallback(Runnable damageCallback) {
		this.damageCallback = damageCallback;
	}

	public double getHealth() {
		return health;
	}

}
