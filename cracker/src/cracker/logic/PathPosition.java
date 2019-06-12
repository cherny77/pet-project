package cracker.logic;

import java.util.List;

public class PathPosition {
	private Path path;
	private int nodeIndex;
	private double progress;

	public PathPosition(Path path) {
		this.path = path;
		this.nodeIndex = 0;
		this.progress = 0;
	}

	public PathPosition clone() {
		PathPosition cloned = new PathPosition(path);
		cloned.nodeIndex = nodeIndex;
		cloned.progress = progress;
		return cloned;
	}

	public void move(double distance) {
		Position firstNode = path.getPositions().get(nodeIndex);
		if (nodeIndex == path.getPositions().size() - 1)
			return;
		Position secondNode = path.getPositions().get(nodeIndex + 1);
		double segment = getDistance(firstNode, secondNode);
		double currentPosition = segment * progress;
		progress += distance / segment;
		if (progress >= 1) {
			distance -= segment - currentPosition;
			nodeIndex++;
			progress = 0;
			move(distance);
		}
	}

	public boolean isFinished() {
		return nodeIndex == path.getPositions().size() - 1;
	}

	public Position getPosition() {
		Position firstNode = path.getPositions().get(nodeIndex);
		if (path.getPositions().size() <= nodeIndex + 1) {
			return firstNode;
		}
		Position secondNode = path.getPositions().get(nodeIndex + 1);
		double x = firstNode.getX() + (secondNode.getX() - firstNode.getX()) * progress;
		double y = firstNode.getY() + (secondNode.getY() - firstNode.getY()) * progress;

		return new Position(x, y);
	}

	private double getDistance(Position firstNode, Position secondNode) {
		return Math.sqrt(Math.pow(secondNode.getX() - firstNode.getX(), 2) +
				Math.pow(secondNode.getY() - firstNode.getY(), 2));
	}

	public Path getPath() {
		return path;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public double getProgress() {
		return progress;
	}

	public double getTotalProgress() {
		return getAccomplishedDistance() / getTotalDistance();
	}

	private double getTotalDistance() {
		double totalDistance = 0;
		List<Position> nodes = path.getPositions();
		for (int i = 1; i < nodes.size(); i++) {
			totalDistance += getDistance(nodes.get(i - 1), nodes.get(i));
		}

		return totalDistance;
	}

	private double getAccomplishedDistance() {
		double accomplishedDistance = 0;
		List<Position> nodes = path.getPositions();
		for (int i = 1; i <= nodeIndex; i++) {
			accomplishedDistance += getDistance(nodes.get(i - 1), nodes.get(i));
		}
		if (nodeIndex < nodes.size() - 1)
			accomplishedDistance += getDistance(nodes.get(nodeIndex), nodes.get(nodeIndex + 1)) * progress;
		return accomplishedDistance;
	}

	public Position getFuturePosition(double distance) {
		PathPosition cloned = clone();
		cloned.move(distance);
		return cloned.getPosition();
	}

}
