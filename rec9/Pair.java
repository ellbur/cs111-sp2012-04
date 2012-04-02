
public class Pair {
	
	public static void main(String[] args) {
		int[] hand = { 9, 7, 3, 3, 5 };
		
		boolean hasPair = false;
		
		for (int i=0; i<hand.length; i++) {
			for (int j=0; j<hand.length; j++) {
				if (hand[i] == hand[j] && i != j) {
					System.out.println(hand[i] + " == " + hand[j]);
					hasPair = true;
				}
			}
		}
		
		System.out.println(hasPair);
	}
}
