public class BSTSymbolTable<Key extends Comparable<Key>, Value>
{
    private Node root;
    private int size;

    public class Node<Key extends Comparable<Key>, Value>
    {
        public Key key;
        public Value value;
        public Node left, right;
        
        public Node(Key aKey, Value aValue)
        {
            key = aKey;
            value = value;
        }
    }

    public BSTSymbolTable()
    {
        root = null;
        size = 0;
    }

    public void put(Key key, Value value)
    {
        put(root, key, value);
    }

    private Node put(Node x, Key key, Value value)
    {
        if (x == null)
        {
            size++;
            return new Node(key, value);
        }
        int cmd = key.compareTo((Key) x.key);

        if (cmd > 0)
            x.right = put(x.right, key, value);
        else if (cmd < 0)
            x.left = put(x.left, key, value);
        else
            x.value = value;

        return x;
    }

    public Value get(Key key)
    {
        Node node = root;

        while (node != null)
        {
            int cmd = key.compareTo((Key) node.key);

            if (cmd > 0)
                node = node.right;
            else if (cmd < 0)
                node = node.left;
            else
                return (Value) node.value;
        }

        return null;
    }

    public boolean contains(Key key)
    {
        return get(key) != null;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public int size()
    {
        return size;
    }
}