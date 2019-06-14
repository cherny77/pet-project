package cracker.model;

public enum MobType {

	GHOST("Ghost", 100, 0.1, 1, 30),
	SKELETON("Skeleton", 200, 0.13, 1, 20),
	SLIME("Slime", 200, 0.05, 2, 25);

	private final String name;
	private final double health;
	private final double speed;
	private final int weight;
	private final int cost;


	MobType(String name, double health, double speed, int weight,int cost) {
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
