package spell;

public class Node implements INode{

    private int value = 0;
    private Node [] nodes = new Node[26];

    Node() {}

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incrementValue() {
        value++;
    }

    @Override
    public Node[] getChildren() {
        return nodes;
    }
}
