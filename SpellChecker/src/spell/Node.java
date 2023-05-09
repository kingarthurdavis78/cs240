package spell;

import java.util.List;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Node their_node = (Node) obj;
        if (their_node.getValue() != this.getValue()) {
            return false;
        }

        Node[] my_children = this.getChildren();
        Node[] their_children = their_node.getChildren();
        for (int i = 0; i < 26; i++) {
            if (my_children[i] == null) {
                if (their_children[i] != null) {
                    return false;
                }
            } else if (!my_children[i].equals(their_children[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = count + 1;
        Node[] kids = this.getChildren();
        for (int i = 0; i < 26; i++) {
            if (kids[i] != null) {
                code *= i + 1;
            }
        }
        return code;
    }

    public String make_words(String word_so_far) {
        StringBuilder result = new StringBuilder();
        Node[] nodes = this.getChildren();
        for (int i = 0; i < 26; i++) {
            if (nodes[i] == null) {
                continue;
            }
            char letter = (char)('a' + i);
            String updated_word = word_so_far + letter;
            if (nodes[i].getValue() > 0) {
                result.append(updated_word).append("\n").append(nodes[i].make_words(updated_word));
            }
            else {
                result.append(nodes[i].make_words(updated_word));
            }
        }
        return result.toString();
    }
}
