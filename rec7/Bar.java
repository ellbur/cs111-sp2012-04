
class Bar {
	public static void main(String[] args) {
		int[] x = { 1, 1 };
		int[] y = x;
		
		y[0] = 3;
		
		System.out.println(x[0]);
	}
}
