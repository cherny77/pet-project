package cracker.model;

public class Mob {
	private final MobType type;
	private final Path path;
	private PathPosition pathPosition;
	private long currentTime;
	private double health;
	private Runnable moveCallback;
	private Runnable damageCallback;
	private Runnable finishCallback;

	public Mob(MobType type, Path path) {
		this.type = type;
		this.path = path;
		pathPosition = new PathPosition(path);
		health = type.getHealth();
	}

	public void setMoveCallback(Runnable moveCallback) {
		this.moveCallback = moveCallback;
	}

	public void setFinishCallback(Runnable finishCallback) {
		this.finishCallback = finishCallback;
	}

	public void move(long time) {
		if (isFinished())
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
		health -= damage;
		if (damageCallback != null) {
			damageCallback.run();
		}
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
