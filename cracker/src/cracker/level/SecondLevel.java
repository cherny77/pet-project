package cracker.level;

import cracker.model.MobType;
import cracker.model.Path;
import cracker.model.Position;

public class SecondLevel extends AbstractLevel {
	@Override
	public void init() {
		map.setLives(10);
		map.setMoney(10000);
		Path path =
				new Path(new Position(0, 365), new Position(50, 390), new Position(505, 390),
						new Position(505, 430), new Position(815, 430), new Position(815, 470),
						new Position(850, 480), new Position(860, 510), new Position(890, 530),
						new Position(890, 560), new Position(1024, 560)) ;
		map.addPath(path);

		Path path1 =
				new Path(new Position(0, 720), new Position(130, 720), new Position(140, 680),
						new Position(290, 680), new Position(310, 640), new Position(440, 640),
						new Position(450, 600), new Position(600, 600), new Position(610, 560),
						new Position(890, 560), new Position(1024, 560)) ;
		map.addPath(path1);

		Path path2 =
				new Path(new Position(650, 0), new Position(650, 300), new Position(680, 300),
						new Position(690, 345), new Position(730, 345), new Position(740, 380),
						new Position(850, 480), new Position(860, 510), new Position(890, 530),
						new Position(890, 560), new Position(1024, 560)) ;
		map.addPath(path2);


		addWave(2000, 10000, MobType.SKELETON, 3, path);
		addWave(13000, 10000, MobType.SLIME, 4, path);
		addWave(24000, 10000, MobType.GHOST, 10, path);
		addWave(35000, 10000, MobType.SKELETON, 20, path);
		addWave(46000, 20000, MobType.SLIME, 10, path);
		addWave(57000, 10000, MobType.SKELETON, 40, path);
		addWave(57000, 10000, MobType.GHOST, 15, path);


		addWave(2000, 10000, MobType.SKELETON, 3, path1);
		addWave(13000, 10000, MobType.SLIME, 4, path1);
		addWave(24000, 10000, MobType.GHOST, 10, path1);
		addWave(35000, 10000, MobType.SKELETON, 20, path1);
		addWave(46000, 20000, MobType.SLIME, 10, path1);
		addWave(57000, 10000, MobType.SKELETON, 40, path1);
		addWave(57000, 10000, MobType.GHOST, 15, path1);



		addWave(2000, 10000, MobType.SKELETON, 3, path2);
		addWave(13000, 10000, MobType.SLIME, 4, path2);
		addWave(24000, 10000, MobType.GHOST, 10, path2);
		addWave(35000, 10000, MobType.SKELETON, 20, path2);
		addWave(46000, 20000, MobType.SLIME, 10, path2);
		addWave(57000, 10000, MobType.SKELETON, 40, path2);
		addWave(57000, 10000, MobType.GHOST, 15, path2);
	}


}
