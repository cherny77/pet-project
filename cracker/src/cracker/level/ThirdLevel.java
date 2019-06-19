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
		addWave(2000, 10000, MobType.SKELETON, 8, path1);
		addWave(13000, 10000, MobType.SLIME, 4, path1);
		addWave(24000, 10000, MobType.GHOST, 10, path1);
		addWave(35000, 10000, MobType.SKELETON, 20, path1);
		addWave(46000, 20000, MobType.SLIME, 10, path1);
		addWave(57000, 10000, MobType.SKELETON, 40, path1);
		addWave(57000, 10000, MobType.GHOST, 15, path1);
	}

	@Override
	public ThirdLevel getLevel() {
		return new ThirdLevel();
	}

}
