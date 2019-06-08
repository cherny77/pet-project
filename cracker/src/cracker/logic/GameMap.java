package cracker.logic;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static final double SIZE = 100;
    private final List<Tower> towers = new ArrayList<>();
    private final List<Path> paths = new ArrayList<>();
    private final List<Wave> waves = new ArrayList<>();
    private int lives;

    public static double getSIZE() {
        return SIZE;
    }

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

    public boolean isWin() {
        return isFinished() && getRemainedLives() > 0;
    }

    public boolean isLoose(){
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

}
