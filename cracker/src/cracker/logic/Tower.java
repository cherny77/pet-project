package cracker.logic;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Tower {
    private final TowerType type;
    private final Position position;
    private final GameMap map;
    private  Consumer<Projectile> callback;
    private long lastShot;

    public Tower(TowerType type, Position position, GameMap map) {
        this.type = type;
        this.position = position;
        this.map = map;
    }

    public void fire(long time) {
        if (time - lastShot < type.getReload()) {
            return;
        }
        Mob mob = getTargetMob();
        if (mob == null) return;
        double projectileDistance = Position.getDistance(mob.getPosition(),position);
        long projectileTime = (long) (projectileDistance / type.getProjectileType().getSpeed());
        double mobDistance = mob.getType().getSpeed() * projectileTime;
        Position endPosition =  mob.getFuturePosition(mobDistance);
        projectileTime = (long) (Position.getDistance(position, endPosition) / type.getProjectileType().getSpeed());
        Projectile projectile = new Projectile(position, endPosition, projectileTime, type.getProjectileType());
        if (callback != null) {
            callback.accept(projectile);
        }
        map.getExecutor().schedule(() -> mob.doDamage(type.getDamage()), projectileTime, TimeUnit.MILLISECONDS);
        lastShot = time;
    }

    private Mob getTargetMob() {
        for (Mob mob : map.getMobs()) {
            if (Position.getDistance(mob.getPosition(), position) <= type.getRange()) {
                return mob;
            }
        }
        return null;
    }

    public void setCallback(Consumer<Projectile> callback) {
        this.callback = callback;
    }
}
