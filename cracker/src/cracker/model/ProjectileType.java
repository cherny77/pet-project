package cracker.model;

public enum ProjectileType {
	ARROW(0.3, 10, 0),
	BOMB(0.2, 200, 70),
	MAGIC(0.2, 10, 20);

	private final double speed;
	private final double plunging;
	private final double splash;

	ProjectileType(double speed, double plunging, double splash) {
		this.speed = speed;
		this.plunging = plunging;
		this.splash = splash;
	}

	public double getSpeed() {
		return speed;
	}

	public double getPlunging() {
		return plunging;
	}

	public double getSplash() {
		return splash;
	}
}
