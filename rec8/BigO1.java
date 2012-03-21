
public class BigO1 {
    public static void main(String[] args) {
        int[] array = { 3, 9, 8, 7, 1, 2, 6, 10, 4, 5 };
        int M = array.length;
        int N = 100;
        
        repeatedlySort(array, N);
        
        System.out.println(java.util.Arrays.toString(array));
    }
    
    static void repeatedlySort(int[] array, int N) {
        for (int i=0; i<N; i++) {
            sort(array);
        }
    }
    
    static void sort(int[] array) {
        for (int j=0; j<array.length; j++) {
            for (int k=j+1; k<array.length; k++) {
                if (array[k] < array[j]) {
                    int temp = array[j];
                    array[j] = array[k];
                    array[k] = temp;
                }
            }
        }
    }
}

