package cracker.logic;

import cracker.CaughtExceptionsThreadFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameMap {
    private final List<Tower> towers = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private final List<Wave> waves = new ArrayList<>();
    private int lives;
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new CaughtExceptionsThreadFactory());

    public void setLives(int lives) {
        this.lives = lives;
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

    public int getRemainedLives() {
        return lives - getWastedLives();
    }

    public boolean isFinished() {
        for (Wave wave : waves) {
            if (!wave.isFinished()) return false;
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
}
