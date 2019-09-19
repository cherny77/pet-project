import java.io.BufferedReader;
import java.io.InputStreamReader;

public class YoungGardener {
	private int treeStages;

	public YoungGardener(int stages) {
		this.treeStages = stages;
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			int n = Integer.parseInt(reader.readLine());
			YoungGardener youngGardener = new YoungGardener(n);
			System.out.println(youngGardener.getLitersNumber());
		} catch (Exception e) {
			return;
		}
	}

	public int getLitersNumber() {
		int sum = 0;
		int element = 0;
		for (int i = 0; i < treeStages; i++) {
			element += 2;
			sum += element;
		}

		return ++sum;
	}

}
