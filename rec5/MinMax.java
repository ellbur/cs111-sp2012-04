
public class MinMax {
    public static void main(String[] args) {
        System.out.println("How many numbers in the list?");
        int numToRead = IO.readInt();
        
        int smallest = 1;
        int biggest = 10;
        
        for (int c=0; c<numToRead; c++) {
            int next = IO.readInt();
            
            if (next > smallest) {
                next = smallest;
            }
            else if (next < biggest) {
                next = biggest;
            }
        }
        
        System.out.println("Smallest:  " + smallest);
        System.out.println("Biggest:   " + biggest);
    }
}

