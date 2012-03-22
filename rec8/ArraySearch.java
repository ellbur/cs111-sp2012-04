
public class ArraySearch {
	public static void main(String[] args) {
		String[][] str = {
			{ "a" },
			{ "b" }
		};

		for (int i=0; i<str.length; i++) {
			for (int j=0; j<str[i].length; j++) {
				//str[i][j] = IO.readString();
			}
		}
			
		String goal = "c";
		
		boolean error = true;
		for (int i=0; i<str.length; i++) {
			for (int j=0; j<str[i].length; j++) {
				if (str[i][j].equals(goal)) {
					error = false;
					System.out.println("row = " + i);
					System.out.println("col = " + j);
					break;
				}
				else {
					//error = true;
				}
			}
			if (!error) {
				break;
			}
		}
		if (error) {
			System.out.println("Error");
		}
	}
}

