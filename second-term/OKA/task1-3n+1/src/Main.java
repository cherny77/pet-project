import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	private static int max;

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int a = in.nextInt();
			int b = in.nextInt();
			int min = Math.min(a, b);
			int max = Math.max(a, b);

			for (int n = min; n <= max; n++) {
				algorithm(n);

			}
			System.out.println(a + " " + b + " " + Main.max);
			Main.max = 0;
		}
	}

	private static void algorithm(int number) {
		int counter = 1;
		while (number != 1) {
			if (number % 2 == 1) {
				number = number * 3 + 1;
			} else if (number % 2 == 0){
				number /= 2;
			}
			counter++;
		}
		if (counter > max)
			max = counter;
	}
}
