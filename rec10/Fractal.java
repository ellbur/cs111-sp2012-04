
public class Fractal {
	public static void main(String[] args) {
		x(10);
	}
	
	static void x(int n) {
		if (n == 0) {
		}
		else {
			x(n - 1);
			Turtle.turn(90);
			y(n - 1);
			Turtle.draw(10);
			Turtle.turn(90);
		}
	}
	
	static void y(int n) {
		if (n==0) { 
		}
		else {
			Turtle.turn(-90);
			Turtle.draw(10);
			x(n - 1);
			Turtle.turn(-90);
			y(n - 1);
		}
	}
}
