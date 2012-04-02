
public class Three {
	
	public static void main(String[] args) {
		int[] hand = { 3, 7, 3, 3, 5 };
		
		boolean hasTripple = false;
		
		for (int i=0; i<hand.length; i++) {
			for (int j=0; j<hand.length; j++) {
				if (hand[i] == hand[j] && i != j) {
					for (int k=0; k<hand.length; k++) {
						if (hand[i] == hand[k] && i != k && j != k) {
							hasTripple = true;
						}
					}
				}
			}
		}
		
		System.out.println(hasTripple);
	}
}
