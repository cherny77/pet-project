package cracker.model;

public class Position {
	private final double x;
	private final double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static double getDistance(Position firstNode, Position secondNode) {
		return Math.sqrt(Math.pow(secondNode.getX() - firstNode.getX(), 2) +
				Math.pow(secondNode.getY() - firstNode.getY(), 2));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Position{" + "x=" + x + ", y=" + y + '}';
	}
}
