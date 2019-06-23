package cracker.level;

import cracker.model.MobType;
import cracker.model.Path;
import cracker.model.Position;

public class ThirdLevel extends AbstractLevel {
	@Override
	public void init() {
		map.setLives(10);
		map.setMoney(200);
		Path path =
				new Path(new Position(0, 650), new Position(150, 650), new Position(155, 610), new Position(450, 610),
						new Position(450, 650), new Position(735, 650), new Position(740, 355),
						new Position(1024, 355));
		map.addPath(path);

		Path path1 =
				new Path(new Position(0, 650), new Position(150, 650), new Position(155, 610), new Position(450, 610),
						new Position(450, 460), new Position(45, 460), new Position(45, 355), new Position(440, 355),
						new Position(440, 250), new Position(220, 250), new Position(220, 145), new Position(765, 145),
						new Position(770, 190), new Position(1024, 190));
		map.addPath(path1);

		addWave(2000, 10000, MobType.SKELETON, 5, path);
		addWave(13000, 10000, MobType.GHOST, 4, path);
		addWave(24000, 10000, MobType.SKELETON, 5, path);
		addWave(35000, 10000, MobType.SLIME, 1, path);
		addWave(46000, 20000, MobType.SKELETON, 8, path);
		addWave(57000, 10000, MobType.GHOST, 6, path);
		addWave(57000, 10000, MobType.SLIME, 3, path);

		addWave(1000, 10000, MobType.SKELETON, 5, path1);
		addWave(14000, 10000, MobType.GHOST, 6, path1);
		addWave(26000, 10000, MobType.SKELETON, 8, path1);
		addWave(38000, 10000, MobType.SLIME, 2, path1);
		addWave(49000, 20000, MobType.SKELETON, 10, path1);
		addWave(60000, 10000, MobType.GHOST, 8, path1);
		addWave(63000, 10000, MobType.SLIME, 6, path1);
	}

	@Override
	public ThirdLevel getLevel() {
		this.map.getWaves().clear();
		this.map.getTowers().clear();
		return new ThirdLevel();
	}

}
