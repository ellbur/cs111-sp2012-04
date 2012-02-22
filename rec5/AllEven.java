
public class AllEven {
    public static void main(String[] args) {
        System.out.println("How many numbers in the list?");
        int numToRead = IO.readInt();
        
        for (int c=0; c<numToRead; c++) {
            int next = IO.readInt();
            
            if (next % 2 != 0) {
                boolean allEven = false;
            }
            else {
                boolean allEven = true;
            }
        }
        
        System.out.println("All even? " + allEven);
    }
}

