public class PercolationStats {
	private int size;
	private int repetition;
	private double[] results;

	public PercolationStats(int n, int t) {
		size = n;
		repetition = t;
		results = new double[repetition];
	}

	public static void main(String[] args) {
		PercolationStats percolationStats = new PercolationStats(20, 20);
		System.out.println(percolationStats.mean());
	}

	public double mean() {
		double sum = 0;
		for (int i = 0; i < repetition; i++) {
			Percolation percolation = new Percolation(size);
			results[i] = percolation.run();
			sum += results[i];
		}
		return sum / repetition;
	}

	public double stddev() {
		double mean = mean();
		double sum = 0;
		for (double d : results) {
			sum += Math.pow(d - mean, 2);
		}
		return sum / repetition;
	}

}
