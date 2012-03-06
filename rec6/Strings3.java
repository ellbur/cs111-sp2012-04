
public class Strings3 {
	public static void main(String[] args) {
		String x = " words in a sentence ";
		
		for (int i=0; i<x.length(); i++) {
			
			if (x.charAt(i) == ' ') {
				for (int j=i+1; j<x.length(); j++) {
					// [until] [there's another space]
					if (x.charAt(j) == ' ') {
						// HERE
						int length = j - i - 1;
						String word = x.substring(i, j);
						System.out.println(word);
						System.out.println(length);
						break;
					}
				}
			}
		}
	}
}
