package cracker.logic;

public class Projectile {
    private final Position startPosition;
    private final Position endPosition;
    private final long duration;
    private final ProjectileType projectileType;
    private final Mob targetMob;

    public Projectile(Position startPosition, Position endPosition, long duration, ProjectileType projectileType, Mob targetMob) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.duration = duration;
        this.projectileType = projectileType;
        this.targetMob = targetMob;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public long getDuration() {
        return duration;
    }

    public Mob getTargetMob() {
        return targetMob;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }
}
