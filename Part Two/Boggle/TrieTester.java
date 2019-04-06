import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TrieTester
{
    public static void main(String[] args) 
    {
        
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        Trie trie = new Trie();

        for (String word : dictionary)
            trie.put(word);

        Node node = trie.getRoot();
        node = trie.next(node, 'D');
        node = trie.next(node, 'I');
        node = trie.next(node, 'E');

        StdOut.println(node.isEndOfWord);
    }
}