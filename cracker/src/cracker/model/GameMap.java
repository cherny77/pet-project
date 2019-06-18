package cracker.model;

import cracker.CaughtExceptionsThreadFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameMap {
	private final List<Tower> towers = new CopyOnWriteArrayList<>();
	private final List<Path> paths = new CopyOnWriteArrayList<>();
	private final List<Wave> waves = new CopyOnWriteArrayList<>();
	private final ScheduledExecutorService executor =
			Executors.newSingleThreadScheduledExecutor(new CaughtExceptionsThreadFactory());
	private final Random rng = new Random();
	private int lives;
	private int money;
	private int waveNumber;

	public int getWaveNumber() {
		return waveNumber;
	}



	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean addTower(Tower tower) {
		towers.add(tower);
		return true;
	}

	public boolean addWave(Wave wave) {
		waves.add(wave);
		return true;
	}

	public boolean addPath(Path path) {
		paths.add(path);
		return true;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public List<Wave> getWaves() {
		return waves;
	}

	public void move(long gameTime) {
		for (Wave wave : waves) {
			wave.move(gameTime);
		}
	}

	public void fire(long gameTime) {
		for (Tower tower : towers) {
			tower.fire(gameTime);
		}
	}

	public boolean isWin() {
		return isFinished() && getRemainedLives() > 0;
	}

	public boolean isLoose() {
		return getRemainedLives() <= 0;
	}

	public int getWastedLives() {
		int counter = 0;
		for (Wave wave : waves) {
			counter += wave.getWastedLives();
		}
		return counter;
	}

	public int getAddMoney() {
		int addMoney = money;
		for (Wave wave : waves) {
			for (Mob mob : wave.getMobs()) {
				if (mob.isKilled())
					addMoney += mob.getType().getCost();
			}
		}
		for (Tower tower : towers) {
			addMoney -= Mods.getInstance().getTowerCostMode().mode() * tower.getType().getCost();
		}
		return addMoney;
	}

	public void setWaveNumber(int waveNumber) {
		this.waveNumber = waveNumber;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getRemainedLives() {
		return lives - getWastedLives() < 0 ? 0 : lives - getWastedLives();
	}

	public boolean isFinished() {
		for (Wave wave : waves) {
			if (!wave.isFinished())
				return false;
		}
		return true;
	}

	public List<Mob> getMobs() {
		List<Mob> mobs = new ArrayList<>();
		for (Wave wave : waves) {
			for (Mob mob : wave.getMobs()) {
				mobs.add(mob);
			}
		}
		Collections.sort(mobs, Comparator.comparing(Mob::getProgress).reversed());
		return mobs;
	}

	public ScheduledExecutorService getExecutor() {
		return executor;
	}

	public Random getRng() {
		return rng;
	}
}
