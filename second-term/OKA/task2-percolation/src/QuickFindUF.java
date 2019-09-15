public class QuickFindUF {
	private int[] id;
	private int count;

	/*
	 * Конструктор, що ініціює масив,
	 * а потім присвоює кожному об'єкту окрему групу
	 */
	public QuickFindUF(int n) {
		id = new int[n + 2];
		count = n;
		for (int i = 0; i < n + 2; i++)
			id[i] = i;
	}

	/*
	 * метод, що перевіряє чи знаходяться p та q в одному компоненті
	 */
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	/*
	 * метод, що робить два об'єкти p та q пов'язаними
	 */
	public int find(int p) {

		return id[p];
	}

	public void union(int p, int q) {
		int pid = find(p);
		int qid = find(q);
		if (pid == qid)
			return;
		for (int i = 0; i < id.length; i++)
			if (id[i] == pid)
				id[i] = qid;
		count--;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i : id) {
			str.append(i);
			str.append(" ");
		}
		return str.toString();
	}
}
