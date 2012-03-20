
public class Qux {
	public static void main(String[] args) {
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8 };
		
		int j = x.length-1;
		for (int i=0; i<x.length/2; i++) {		
			int f = x[i];
			x[i] = x[j];
			x[j] = f;
			System.out.println(java.util.Arrays.toString(x));
			j--;
		}
	}
}
