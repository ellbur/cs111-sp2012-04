
public class PRNGTest {
	
	static Random random;
	
	public static void main(String[] args) {
		random = new Random();
		test();
	}
	
	static void test() {
		double sum = 0.0;
		int n = 0;
		
		for (int k=1; k<=8; k++) {
			double cor = test(k);
			sum += cor;
			n++;
			
			System.out.printf("Correlation at %d: %.3f%%\n", k, cor*100);
		}
		
		System.out.printf("Average: %.3f%%\n", sum/n*100);
	}
	
	static double test(int k) {
		int n = 1;
		for (int i=0; i<k; i++) {
			n *= 2;
		}
		
		int N = 10000 * n;
		
		int[] counts = new int[n];
		
		for (int i=0; i<N; i++) {
			
			int x = 0;
			
			for (int j=0; j<k; j++) {
				x |= (random.next()&1) << j;
			}
			
			counts[x]++;
		}
		
		double s = 0.0;
		
		for (int i=0; i<n; i++) {
			double p = (double) counts[i] / N;
			s += esup(p);
		}
		
		double sPerfect = Math.log(n);
		double cor = (sPerfect - s) / sPerfect;
		
		return cor;
	}
	
	static double esup(double p) {
		if (p < 1e-16) return 0;
		return -p*Math.log(p);
	}
}

