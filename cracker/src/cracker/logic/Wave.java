package cracker.logic;

import java.util.List;

public class Wave {
	List<Mob> mobs;
	long duration;
	long start;

	public Wave(List<Mob> mobs, long duration, long start) {
		this.mobs = mobs;
		this.duration = duration;
		this.start = start;
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).setCurrentTime(start + duration / mobs.size() * i);
		}
	}

	public List<? extends Mob> getMobs() {
		return mobs;
	}

	public long getDuration() {
		return duration;
	}

	public long getStart() {
		return start;
	}

	public void move(long gameTime) {
		for (int i = 0; i < mobs.size(); i++) {
			if (gameTime > start + i * duration / mobs.size() && !mobs.get(i).isFinished()) {
				mobs.get(i).move(gameTime);
			}
		}
	}

	public int getWastedLives() {
		int counter = 0;
		for (Mob mob : mobs) {
			if (mob.isFinished() && !mob.isKilled()) {
				counter += mob.getType().getWeight();
			}
		}
		return counter;
	}

	public boolean isFinished() {
		for (Mob mob : mobs) {
			if (!(mob.isFinished() || mob.isKilled()))
				return false;
		}
		return true;
	}

	public void addMob(Mob mob) {
		mobs.add(mob);
	}

}
