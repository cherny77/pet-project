import java.util.Random;

public class Percolation {
	private Node[] matrix;
	private int rowSize;
	private QuickFindUF quickFindUF;

	public Percolation(int n) {

		matrix = new Node[n * n];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new Node(i);
		}
		rowSize = n;
		quickFindUF = new QuickFindUF(n * n);
	}

	public double run() {
		Random random = new Random();
		do {
			int rPosition = random.nextInt(matrix.length - 1);
			open(rPosition / rowSize, rPosition % rowSize);
		} while (!percolates());

		System.out.println(getOpenedCount() + " / " + matrix.length);

		return (double) getOpenedCount() / (double) matrix.length;
	}

	public int getOpenedCount() {
		int counter = 0;
		for (Node node : matrix) {
			if (node.isOpen())
				counter++;
		}
		return counter;
	}

	public void open(int row, int column) {
		matrix[row * rowSize + column].open();
	}

	public boolean isOpened(int row, int column) {
		return matrix[row * rowSize + column].isOpen();
	}

	private Node getNode(int row, int column) {
		return matrix[row * rowSize + column];
	}

	public boolean percolates() {
		Node top = new Node(true, matrix.length + 1);
		Node bottom = new Node(true, matrix.length);
		// connect top and bottom nodes with the highest and lowest nodes
		for (int i = 0; i < rowSize; i++) {
			quickFindUF.union(matrix[i].getPosition(), top.getPosition());
		}
		for (int i = (rowSize - 1) * rowSize; i < matrix.length; i++) {
			quickFindUF.union(matrix[i].getPosition(), bottom.getPosition());
		}

		for (int i = 0; i < matrix.length; i++) {
			int row = i / rowSize;
			int column = i % rowSize;
			if (isOpened(row, column)) {
				if (i + rowSize < matrix.length && isOpened(row + 1, column)) {
					quickFindUF.union(getNode(row + 1, column).getPosition(), getNode(row, column).getPosition());
				}
				if (i - rowSize >= 0 && isOpened(row - 1, column)) {
					quickFindUF.union(getNode(row - 1, column).getPosition(), getNode(row, column).getPosition());
				}
				if (i + 1 < matrix.length && isOpened(row, column + 1)) {
					quickFindUF.union(getNode(row, column + 1).getPosition(), getNode(row, column).getPosition());
				}
				if (i - 1 >= 0 && isOpened(row, column - 1)) {
					quickFindUF.union(getNode(row, column - 1).getPosition(), getNode(row, column).getPosition());
				}

			}
		}

		return quickFindUF.connected(top.getPosition(), bottom.getPosition());
	}

}
