package cracker.level;

import cracker.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractLevel {

	private static final long PERIOD = 30L;

	protected GameMap map = new GameMap();
	private ScheduledFuture<?> future;
	private long startTime;
	private Runnable winCallback;
	private Runnable moveCallback;
	private boolean isWin;

	public abstract void init();

	public GameMap getMap() {
		return map;
	}

	public void start() {
		startTime = Instant.now().toEpochMilli();
		future = map.getExecutor().scheduleAtFixedRate(() -> move(), 0, PERIOD, TimeUnit.MILLISECONDS);
	}

	public void move() {
		try {
			long gameTime = Instant.now().toEpochMilli() - startTime;
			map.move(gameTime);
			map.fire(gameTime);
			if (map.isWin()) {
				onWin();
				stop();
			} else if (map.isLoose()) {
				onLoose();
				stop();
			}
			if (moveCallback != null) {
				moveCallback.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMoveCallback(Runnable moveCallback) {
		this.moveCallback = moveCallback;
	}

	public void stop() {
		if (future != null)
			future.cancel(true);
	}

	public void onWin() {
		isWin = true;
		System.out.println(winCallback);
		if (winCallback != null) {
			winCallback.run();
		}

		System.out.println("You win!");
	}

	public void onLoose() {
		isWin = false;

		if (winCallback != null) {
			winCallback.run();
		}

		System.out.println("You loose!");
	}

	protected void addWave(long start, long duration, MobType mobType, int size, Path path) {
		ArrayList<Mob> mobs = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Mob mob = new Mob(mobType, path);
			mobs.add(mob);
		}
		Wave wave = new Wave(mobs, duration, start);
		map.addWave(wave);
	}

	public boolean isWin() {
		return isWin;
	}

	public boolean isRunning() {
		return future != null && !future.isDone();
	}

	public ScheduledExecutorService getExecutor() {
		return map.getExecutor();
	}

	public void setWinCallback(Runnable winCallback) {
		this.winCallback = winCallback;
	}
}
