
public class BigO3 {
    public static void main(String[] args) {
        int N = 20;
        
        boolean prime = isPrime(N);
        
        System.out.println(prime);
    }
    
    static boolean isPrime(int N) {
        for (int k=2; k<N; k++) {
            if (N % k == 0) {
                return false;
            }
        }
        return true;
    }
}

