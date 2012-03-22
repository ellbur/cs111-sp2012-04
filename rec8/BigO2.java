
public class BigO2 {
    public static void main(String[] args) {
        int[] array = { 0, 0, 1, 0, 1, 1, 0, 1 };
        int N = array.length;
        
        int longest = longestOneSequence(array);
        
        System.out.println(longest);
    }
    
    static int longestOneSequence(int[] array) {
        int maxCount = 0;
        
        for (int i=0; i<array.length; i++) {
            int count = 0;
            for (int j=i; j<array.length; j++) {
                if (array[j] == 1) {
                    count++;
                }
                else {
                    break;
                }
            }
            
            if (count > maxCount) {
                maxCount = count;
            }
        }
        
        return maxCount;
    }
}

