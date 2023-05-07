package spell;

public class Node implements INode{

    private int count = 0;
    private Node [] nodes = new Node[26];

    Node() {}

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return nodes;
    }
}
