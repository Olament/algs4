public class SymbolTableTester
{
    public static void main(String[] args)
    {
        BSTSymbolTable bst = new BSTSymbolTable();

        bst.put(1, "Nihao");
        bst.put(2, "Zaijian");

        System.out.println(bst.size());
        System.out.println(bst.get("nihao"));
    }
}