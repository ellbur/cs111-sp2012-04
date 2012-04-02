
public class BigO7 {
    public static void main(String[] args) {
        int N = 5;
        
        int f = factorial(N);
        
        System.out.println(f);
    }
    
    static int factorial(int N) {
        int p = 1;
        for (int i=1; i<=N; i++) {
            p = p * i;
        }
        return p;
    }
}

