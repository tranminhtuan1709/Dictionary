public class TrieNode {
    private String meaning = null;
    public TrieNode[] next = new TrieNode[26];

    /**
     * Default constructor.
     */
    public TrieNode() {

    }

    /**
     * Constructor copy, deep copy another node to this node.
     * @param node other node
     */
    public TrieNode(TrieNode node) {
        this.meaning = node.meaning;
        this.next = node.next;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isEmpty() {
        return true;
    }
}
