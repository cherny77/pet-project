package cracker.model;

public enum MobType {

	GHOST("Ghost", 450, 0.06, 1, 30),
	SKELETON("Skeleton", 150, 0.15, 1, 10),
	SLIME("Slime", 1000, 0.03, 2, 50);

	private final String name;
	private final double health;
	private final double speed;
	private final int weight;
	private final int cost;

	MobType(String name, double health, double speed, int weight, int cost) {
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.weight = weight;
		this.cost = cost;
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

	public int getCost() {
		return cost;
	}
}
