
public class Depth {
	public static void main(String[] args) {
		foo(0);
	}
	
	static void foo(int counter) {
		System.out.println(counter);
		foo(counter + 1);
	}
}
