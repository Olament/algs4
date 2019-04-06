public class Trie
{
    public Node root;

    public void put(String string)
    {
        root = put(root, string, 0);
    }

    private Node put(Node node, String string, int index)
    {
        if (node == null)
            node = new Node();
        if (string.length() == index)
        {
            node.isEndOfWord = true;
            return node;
        }
        int p = charToIndex(string.charAt(index));
        node.next[p] = put(node.next[p], string, index + 1);
        return node;
    }

    private int charToIndex(char c)
    {
        return (int) c - 65;
    }

    public Node next(Node node, char c)
    {
        return node.next[charToIndex(c)];
    }

    public Node getRoot()
    {
        return root;
    }

    public boolean contains(String string)
    {
        Node node = get(root, string, 0);
        if (node == null || !node.isEndOfWord)
            return false;
        return true;
    }

    private Node get(Node node, String string, int index)
    {
        if (node == null)
            return null;
        if (index == string.length())
            return node;
        return get(node.next[charToIndex(string.charAt(index))], string, index + 1);
    }
}