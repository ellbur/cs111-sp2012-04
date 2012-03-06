public class Strings2 {
	public static void main(String[] args) {
		String[] a = { "a", "aa", "aaa" };
		//String[] b = { "b", "bb", "bbb" };
		
		String x = "asdfjeoi";
		boolean in = false;
		for (int i=0; i<a.length; i++) {
			String y = a[i];
			if (x.equals(y)) {
				in = true;
				break;
			}
		}
		if (in == true) {
			System.out.println("x is in a");
		}
		else {
			System.out.println("x is NOT in a");
		}
	}
}
