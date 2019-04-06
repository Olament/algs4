public class RwayTrie<Value>
{
    //private final int radix = 256;
    private Node root;

    private static class Node
    {
        private Object value;
        private Node[] next = new Node[256];
    }

    public void put(String string, Value value)
    {
        root = put(root, string, value, 0);
    }

    private Node put(Node node, String string, Value value, int index)
    {
        if (node == null) 
            node = new Node();
        if (string.length() == index)
        {
            node.value = value;
            return node;
        }
        char c = string.charAt(index);
        node.next[c] = put(node.next[c], string, value, index + 1);
        return node;
    }

    public Value get(String string)
    {
        Node node = get(root, string, 0);
        if (node == null)
            return null;
        return (Value) node.value;
    }

    private Node get(Node node, String string, int index)
    {
        if (node == null)
            return null;
        if (index == string.length())
            return node;
        return get(node.next[string.charAt(index)], string, index + 1);
    }

    public void delete(String string)
    {
        delete(root, string, 0);
    }

    private Node delete(Node node, String string, int index)
    {
        if (node == null)
            return null;

        if (string.length() == index)
            node.value = null;
        else
            node.next[string.charAt(index)] = 
                delete(node.next[string.charAt(index)], string, index + 1);

        if (node.value != null)
            return node;
        for (int i = 0; i < 256; i++)
            if (node.next[i] != null)
                return node;
            
        return null;
    }
}