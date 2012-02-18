
public class Timing {
	public static void main(String[] args) {
		
		// take time at start
		long start = System.currentTimeMillis();
		// do something 1000 times
		int count = 1;
		while (count <= 1000) {
			System.out.println("Hi");
			count = count + 1;
		}
		// take time at end
		long end = System.currentTimeMillis();
		// subtract them
		System.out.println((end - start)*1000000.0);
	}
}

