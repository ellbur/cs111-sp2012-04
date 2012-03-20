
public class Baz {
	public static void main(String[] args) {
		int[] x = { 1, 2, 3 };
		
		int[] y = new int[x.length];
		
		int len = x.length;
		int j = 0;
		for (int i=len-1; i>=0; i--) {
			y[j] = x[i];
			System.out.println(java.util.Arrays.toString(y));
			j++;
		}
		System.out.println(java.util.Arrays.toString(x));
	}
}
