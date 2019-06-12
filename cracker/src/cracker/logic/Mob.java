package cracker.logic;

public class Mob {
	private final MobType type;
	private final Path path;
	private PathPosition pathPosition;
	private long currentTime;
	private double health;
	private Runnable callback;

	public Mob(MobType type, Path path) {
		this.type = type;
		this.path = path;
		pathPosition = new PathPosition(path);
		health = type.getHealth();
	}
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

	public void move(long time) {
		double distance = (time - currentTime) * type.getSpeed();
		pathPosition.move(distance);
		currentTime = time;
//        System.out.println(getPosition());
		if (callback != null) {
			callback.run();
		}
	}

	public void doDamage(double damage) {
		health -= damage;
	}

	public MobType getType() {
		return type;
	}

	public Path getPath() {
		return path;
	}

	public Position getPosition() {
		return pathPosition.getPosition();
	}

	public double getProgress() {
		return pathPosition.getTotalProgress();
	}

	public PathPosition getPathPosition() {
		return pathPosition;
	}

	public long getCurrentTime() {
		return currentTime;
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

	public Position getFuturePosition(double distance) {
		return pathPosition.getFuturePosition(distance);
	}
}
