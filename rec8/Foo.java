
public class Foo {
	public static void main(String[] args) {
		int[] x = new int[1];
		while (true) {
			int y = x.length;
			y = y * 2;
			System.out.println(y);
			x = new int[y];
		}
	}
}

