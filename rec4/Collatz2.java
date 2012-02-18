
public class Collatz2 {
	public static void main(String[] args) {
		//System.out.println("Enter the starting number:");
		//int x = IO.readInt();
		//if (x <= 0) {
		//	System.out.println("No good");
		//	return;
		//}
		
		int count = 1;
		int x;
		while (count < 1000) {
			x = count;
			count = count + 1;
			while (x != 1) {
				System.out.println(x);
				if (x % 2 == 0) {
					x = x / 2;
				}
				else {
					x = 3*x + 1;
				}
			}
			System.out.println();
			System.out.println(x);
		}
	}
}

