package cracker.level;

import cracker.model.GameMap;

import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractLevel {

	private static final long PERIOD = 30L;

	protected GameMap map = new GameMap();
	private ScheduledFuture<?> future;
	private long startTime;
	private Runnable callback;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (future != null)
			future.cancel(true);
	}

	public void onWin() {
		isWin = true;
		if (callback != null) {
			callback.run();
		}

		System.out.println("You win!");
	}

	public void onLoose() {
		isWin = false;
		if (callback != null) {
			callback.run();
		}

		System.out.println("You loose!");
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
}
