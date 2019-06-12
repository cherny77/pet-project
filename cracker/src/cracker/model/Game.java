package cracker.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Game {

	private static final long PERIOD = 30L;

	private GameMap map = new GameMap();
	private ScheduledFuture<?> future;
	private long startTime;
	private Runnable callback;
	private boolean isWin;

	public void init() {
		map.setLives(10);
		Path path = new Path(new Position(0, 404), new Position(230, 404), new Position(230, 80), new Position(480,
				80),
				new Position(480, 580), new Position(720, 580), new Position(755, 570), new Position(755, 240),
				new Position(1024, 240));
		map.addPath(path);
		ArrayList<Mob> mobs = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			Mob mob1 = new Mob(MobType.SKELETON, path);
			mobs.add(mob1);
//        Mob mob2 = new Mob(MobType.SKELETON, path);
//        Mob mob3 = new Mob(MobType.SLIME, path);
		}
		Wave wave = new Wave(mobs, 10000L, 0);
		map.addWave(wave);
	}

	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

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

	public ScheduledExecutorService getExecutor() {
		return map.getExecutor();
	}
}
