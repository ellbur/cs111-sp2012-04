
public class BigO5 {
    public static void main(String[] args) {
        int N = 120;
        
        boolean square = isPerfectSquare(N);
        
        System.out.println(square);
    }
    
    static boolean isPerfectSquare(int N) {
        int a = 0;
        int b = N;
        
        while (a <= b) {
            int c = (a + b) / 2;
            
            if (c*c == N) {
                return true;
            }
            else if (c*c < N) {
                a = c + 1;
            }
            else {
                b = c - 1;
            }
        }
        return false;
    }
}

