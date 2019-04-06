public class RwayTrieTester
{
    public static void main(String[] args) 
    {
        RwayTrie<Integer> trie = new RwayTrie<>();
        trie.put("hello", 0);
        System.out.println(trie.get("hello"));
        trie.delete("hello");
        System.out.println(trie.get("hello"));
    }
}