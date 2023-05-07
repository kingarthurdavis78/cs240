package spell;

public class Trie implements ITrie {
    Node root = new Node();
    int word_count = 0;
    int num_nodes = 0;
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
        word_count++;
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
        return current_node;
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
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {return false;}

    @Override
    public String toString() {
        return super.toString();
    }
}
