public class Node {
	private boolean isOpen;
	private int position;

	public Node(int position) {
		this.position = position;
	}

	public Node(boolean isFree, int position) {
		this.isOpen = isFree;
		this.position = position;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public int getPosition() {
		return position;
	}

	public void open() {
		isOpen = true;
	}

}
