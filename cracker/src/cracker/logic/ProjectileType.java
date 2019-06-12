package cracker.logic;

public enum ProjectileType {
    ARROW(0.3, 10,0),
    BOMB (0.2,100,100),
    MAGIC_BALL(0.2, 40, 40);

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
