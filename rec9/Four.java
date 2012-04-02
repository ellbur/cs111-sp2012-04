
public class Four {
	
	public static void main(String[] args) {
		int[] hand = { 3, 7, 3, 3, 3 };
		int N;
		
		boolean hasFour = false;
		
		for (int i=0; i<hand.length; i++) {
			for (int j=0; j<hand.length; j++) {
				if (hand[i] == hand[j] && i != j) {
					for (int k=0; k<hand.length; k++) {
						if (hand[i] == hand[k] && i != k && j != k) {
							for (int l=0; l<hand.length; l++) {
								if (hand[i]==hand[l] && i!=l && j!=l && k!=l) {
									hasFour = true;
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(hasFour);
	}
}
