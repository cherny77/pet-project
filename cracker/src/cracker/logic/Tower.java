package cracker.logic;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Tower {
    private final TowerType type;
    private final Position position;
    private final GameMap map;
    private Consumer<Projectile> callback;
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
        if (mob == null || mob.isKilled()) return;
        Position endPosition = getMobFuturePosition(mob);
        double projectileDistance = Position.getDistance(getMobFuturePosition(mob), position);
        long projectileTime = (long) (projectileDistance / type.getProjectileType().getSpeed());
        Projectile projectile = new Projectile(position, endPosition, projectileTime, type.getProjectileType(), mob);
        if (callback != null) {
            callback.accept(projectile);
        }
        map.getExecutor().schedule(() -> mob.doDamage(type.getDamage()), projectileTime, TimeUnit.MILLISECONDS);
        lastShot = time;
    }

    private Position getMobFuturePositionIteration(Mob mob, Position mobStartPostion) {
        double projectileDistance = Position.getDistance(mobStartPostion, position);
        long projectileTime = (long) (projectileDistance / type.getProjectileType().getSpeed());
        double mobDistance = mob.getType().getSpeed() * projectileTime;
        Position endPosition = mob.getFuturePosition(mobDistance);
        return endPosition;
    }

    private Position getMobFuturePosition(Mob mob) {
        final double THRESHOLD = 5;
        int counter = 0;
        Position futurePosition;
        Position nextFuturePosition = getMobFuturePositionIteration(mob, mob.getPosition());
        do {
            futurePosition = nextFuturePosition;
            nextFuturePosition = getMobFuturePositionIteration(mob, futurePosition);
            counter++;
            if (counter > 10) System.out.println(counter);
        }
        while (Position.getDistance(futurePosition, nextFuturePosition) > THRESHOLD);
        return nextFuturePosition;
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
