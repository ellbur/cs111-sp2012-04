
public class Strings {
	
	public static void main(String[] args) {
		String x = "abcdefgh";
		
		// x.substring(0, 1)
		String y = x.substring(0, 1).toUpperCase();
		// String y = (x.charAt(0)+"").toUpperCase();
		String z = x.substring(1, x.length());
		System.out.println(y + z);
	}
	
}
