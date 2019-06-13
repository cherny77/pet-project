package cracker.model;

import java.util.ArrayList;
import java.util.List;
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
		if (mob == null)
			return;
		double splash = type.getProjectileType().getSplash();
		double derivation = splash * map.getRng().nextDouble();
		double angle = 2 * Math.PI * map.getRng().nextDouble();
		double derivationX = derivation * Math.sin(angle);
		double derivationY = derivation * Math.cos(angle);
		Position futureMobPosition = getMobFuturePosition(mob);
		Position strikePosition =
				new Position(futureMobPosition.getX() + derivationX, futureMobPosition.getY() + derivationY);
		double projectileDistance = Position.getDistance(strikePosition, position);
		long projectileTime = (long) (projectileDistance / type.getProjectileType().getSpeed());
		Projectile projectile = new Projectile(position, strikePosition, projectileTime, type.getProjectileType());
		if (callback != null) {
			callback.accept(projectile);
		}
		map.getExecutor().schedule(() -> {
			if (type.getProjectileType().getSplash() == 0) {
				mob.doDamage(type.getDamage());
			} else {
				List<Mob> splashedMobs = getSplashedMobs(strikePosition);
				for (Mob m : splashedMobs) {
					m.doDamage(type.getDamage() * (splash - Position.getDistance(m.getPosition(), strikePosition)) / splash);
				}
			}

		}, projectileTime, TimeUnit.MILLISECONDS); lastShot = time;
	}

	private Position getMobFuturePositionIteration(Mob mob, Position startPosition) {
		double projectileDistance = Position.getDistance(startPosition, position);
		long projectileTime = (long) (projectileDistance / type.getProjectileType().getSpeed());
		double mobDistance = mob.getType().getSpeed() * projectileTime;
		Position endPosition = mob.getFuturePosition(mobDistance);
//        System.out.println("-------------------------------");
//        System.out.println("time=" + projectileTime);
//        System.out.println("towerPosition=" + position);
//        System.out.println("startPosition=" + startPosition);
//        System.out.println("endPosition=" + endPosition);
//        System.out.println("-------------------------------");
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
			if (counter > 100)
				break;
		} while (Position.getDistance(futurePosition, nextFuturePosition) > THRESHOLD);
		return nextFuturePosition;
	}

	private Mob getTargetMob() {
		for (Mob mob : map.getMobs()) {
			if (!mob.isKilled() && Position.getDistance(mob.getPosition(), position) <= type.getRange()) {
				return mob;
			}
		}
		return null;
	}

	private List<Mob> getSplashedMobs(Position strikePosition) {
		ArrayList<Mob> splashedMobs = new ArrayList<>();
		for (Mob mob : map.getMobs()) {
			if (!mob.isKilled() &&
					Position.getDistance(mob.getPosition(), strikePosition) <= type.getProjectileType().getSplash()) {
				splashedMobs.add(mob);
			}
		}
		return splashedMobs;
	}

	public TowerType getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	public GameMap getMap() {
		return map;
	}

	public Consumer<Projectile> getCallback() {
		return callback;
	}

	public void setCallback(Consumer<Projectile> callback) {
		this.callback = callback;
	}

	public long getLastShot() {
		return lastShot;
	}
}
