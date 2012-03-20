
public class Bug {
    public static void main(String[] args) {
        String[] shoppingList = { "Pile", "of", "skulls" };
        String[] biggerList = foo(shoppingList);
        
        System.out.println(shoppingList[0]);
    }
    
    public static String[] foo(String[] x) {
        for (int i=0; i<x.length; i++) {
            x[i] = x[i] + x[i];
        }
        return x;
    }
}

