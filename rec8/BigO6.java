
public class BigO6 {
    public static void main(String[] args) {
        int N = 169;
        
        boolean square = isPerfectSquare(N);
        
        System.out.println(square);
    }
    
    static boolean isPerfectSquare(int N) {
        for (int i=0; i*i<=N; i++) {
            if (i*i == N) {
                return true;
            }
        }
        return false;
    }
}

