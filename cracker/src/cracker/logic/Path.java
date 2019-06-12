package cracker.logic;

import java.util.Arrays;
import java.util.List;

public class Path {
	private final List<Position> positions;

	public Path(Position... positions) {
		this.positions = Arrays.asList(positions);
	}

	public List<Position> getPositions() {
		return positions;
	}
}
