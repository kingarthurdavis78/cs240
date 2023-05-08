package spell;

import java.util.List;

public class Trie implements ITrie {
    Node root = new Node();
    int word_count = 0;
    int num_nodes = 1;
    public Trie() {}

    @Override
    public void add(String word) {
        Node current_node = root;
        for (int i=0; i<word.length(); i++) {
            char letter = word.charAt(i);
            int pos =  letter - 'a';
            Node [] nodes =  current_node.getChildren();
            if (nodes[pos] == null) {
                nodes[pos] = new Node();
                num_nodes++;
            }
            current_node = nodes[pos];
        }
        current_node.incrementValue();
        if (current_node.getValue() == 1) {
            word_count++;
        }
    }

    @Override
    public Node find(String word) {
        Node current_node = root;
        for (int i=0; i<word.length(); i++) {
            char letter = word.charAt(i);
            int pos =  letter - 'a';
            Node [] nodes =  current_node.getChildren();
            if (nodes[pos] == null) {
                return null;
            }
            current_node = nodes[pos];
        }
        if (current_node.getValue() > 0) {
            return current_node;
        }
        return null;
    }


    @Override
    public int getWordCount() {
        return word_count;
    }

    @Override
    public int getNodeCount() {
        return num_nodes;
    }

    @Override
    public int hashCode() {
        return root.hashCode() * num_nodes * word_count;
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

        Trie t = (Trie)obj;

        if (t.word_count == this.word_count && t.num_nodes == this.num_nodes) {
            return this.root.equals(((Trie) obj).root);
        }

        return false;


    }

    @Override
    public String toString() {
        return root.make_words("");
    }
}
