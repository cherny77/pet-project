import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TTVWorld {

	private int rabbitsNumber = 1;
	private int monthsNumber;
	private int eatenRabbitsForMonth;

	public TTVWorld(int monthsNumber, int eatenRabbitsForMonth) {
		this.monthsNumber = monthsNumber;
		this.eatenRabbitsForMonth = eatenRabbitsForMonth;
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			try {
				int n = Integer.parseInt(reader.readLine());
				int k = Integer.parseInt(reader.readLine());

				TTVWorld ttvWorld = new TTVWorld(n, k);
				ttvWorld.sentRabbit();
				System.out.println(ttvWorld.getRabbitsNumber());

			} catch (Exception e) {
				return;
			}

	}

	public void sentRabbit() {
		for (int i = 1; i <= monthsNumber; i++) {
			if (rabbitsNumber > eatenRabbitsForMonth) {
				eat();
			}
			breed();

		}
	}

	private void eat() {
		rabbitsNumber -= eatenRabbitsForMonth;
	}

	private void breed() {
		rabbitsNumber *= 2;
	}

	public int getRabbitsNumber() {
		return rabbitsNumber;
	}

}
