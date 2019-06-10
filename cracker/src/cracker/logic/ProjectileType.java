package cracker.logic;

public enum ProjectileType {
    ARROW(0.3, 0,0);

    private final double speed;
    private final double controlDeltaX;
    private final double controlDeltaY;

    ProjectileType(double speed, double controlDeltaX, double controlDeltaY) {
        this.speed = speed;
        this.controlDeltaX = controlDeltaX;
        this.controlDeltaY = controlDeltaY;
    }

    public double getSpeed() {
        return speed;
    }

    public double getControlDeltaX() {
        return controlDeltaX;
    }

    public double getControlDeltaY() {
        return controlDeltaY;
    }
}
