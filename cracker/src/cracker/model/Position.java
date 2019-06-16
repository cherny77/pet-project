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

	public static double getDistanceToSegment(Position p, Position p0, Position p1) {
		Position v = p1.minus(p0);
		Position w = p.minus(p0);
		double c1;
		if ((c1 = v.scalarProduct(w)) <= 0)
			return getDistance(p, p0);
		double c2;
		if ((c2 = v.scalarProduct(v)) <= c1)
			return getDistance(p, p1);
		double b = c1 / c2;
		Position Pb = p0.plus(v.multiply(b));
		return getDistance(p, Pb);
	}

	public Position plus(Position p) {
		return new Position(getX() + p.getX(), getY() + p.getY());
	}

	public Position minus(Position p) {
		return new Position(getX() - p.getX(), getY() - p.getY());
	}

	public Position multiply(double b) {
		return new Position(getX() * b, getY() * b);
	}

	public double scalarProduct(Position p) {
		return p.getX() * getX() + p.getY() * getY();
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
