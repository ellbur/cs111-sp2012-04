
public class Bug {
    public static void main(String[] args) {
        String [shoppingList] = { "Pile", "of", "skulls" };
        String [biggerList] = shoppingList.twice();
        
        System.out.println(shoppingList[0]);
    }
    
    public static void twice(String [biggerList]) {
        for (i=0; i<biggerList.length; i++) {
            biggerList[i] = shoppingList[i] + shoppingList[i];
        }
        return biggerList;
    }
}

