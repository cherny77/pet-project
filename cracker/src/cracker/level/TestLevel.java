package cracker.level;

import cracker.model.*;

import java.util.ArrayList;

public class TestLevel extends AbstractLevel {
	@Override
	public void init() {
		map.setLives(10);
		map.setMoney(150);
		Path path = new Path(new Position(0, 445), new Position(255, 445), new Position(255, 120), new Position(505,
				120),
				new Position(505, 625), new Position(775, 625), new Position(775, 280),
				new Position(1024, 280));
		map.addPath(path);


		addWave(2000, 1000000, MobType.SLIME, 5, path);
	}
	@Override
	public TestLevel getLevel(){
		this.map.getWaves().clear();
		this.map.getTowers().clear();
		return new TestLevel();
	}

}
