package cracker.model;

public enum MobType {

	GHOST("Ghost", 100, 0.1, 1),
	SKELETON("Skeleton", 200, 0.13, 1),
	SLIME("Slime", 200, 0.05, 2);

	private final String name;
	private final double health;
	private final double speed;
	private final int weight;

	MobType(String name, double health, double speed, int weight) {
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public double getHealth() {
		return health;
	}

	public double getSpeed() {
		return speed;
	}

	public int getWeight() {
		return weight;
	}
}
