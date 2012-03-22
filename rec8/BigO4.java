
public class BigO4 {
    public static void main(String[] args) {
        int[][] array = {
            { 0, 0, 0, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 1 }
        };
        int N = array.length;
        int M = array[0].length;
        
        boolean ok = eachRowHasAOne(array);
        
        System.out.println(ok);
    }
    
    static boolean eachRowHasAOne(int[][] array) {
        for (int i=0; i<array.length; i++) {
            boolean ok = false;
            for (int j=0; j<array[i].length; j++) {
                if (array[i][j] == 1) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return false;
            }
        }
        return true;
    }
}

