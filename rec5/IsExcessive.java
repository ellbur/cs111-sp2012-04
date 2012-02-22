
public class IsExcessive {
    public static boolean isExcessive(int n) {
        int sum = 0;
        for (int i=1; i<=n; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        
        if (sum > i) {
            return true;
        }
        else {
            return false;
        }
    }
}

