import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.Exception;

public class Trie {
    private TrieNode root = new TrieNode();
    private Map<String, List<String>> wordToMeanings = new HashMap<>();
    private static final String fileIN = "src/Dictionaries.txt";
    /**
     * Default constructor.
     */
    public Trie() {

    }

    /**
     * Add an english word and its vietnamese meaning to Trie.
     * @param wordTarget given english word
     * @param wordExplain given vietnamese meaning
     */
    public void add(String wordTarget, String wordExplain) {
        wordTarget = wordTarget.toLowerCase();
        wordExplain = wordExplain.toLowerCase();

        if(!wordToMeanings.containsKey(wordTarget)) {
            wordToMeanings.put(wordTarget, new ArrayList<>());
        }
        wordToMeanings.get(wordTarget).add(wordExplain);
        List<String> meanings = wordToMeanings.get(wordTarget);
        if (!meanings.contains(wordExplain)) {
            meanings.add(wordExplain);
        }

        TrieNode current = new TrieNode(root);
        for (int i = 0; i < wordTarget.length(); i++) {
            int crt = wordTarget.charAt(i) - 97;
            if (current.next[crt] == null) {
                current.next[crt] = new TrieNode();
            }
            current = current.next[crt];
        }

        if(current.getMeaning() == null) {
            current.setMeaning(wordExplain);
        }
    }

    /**
     * Find all words whose prefix string is the string being searched by the search method.
     * @param node the node representing to the word is being searched
     * @param prefixString the word is being searched
     * @param res result list
     */
    public void matchingResult(TrieNode node, String prefixString, List<String> res) {
        if (node.getMeaning() != null) {
            res.add(prefixString);
        }
        for (int i = 0; i < 25; i++) {
            if (node.next[i] != null) {
                matchingResult(node.next[i], prefixString + (char)(i + 97), res);
            }
        }
    }

    /**
     * Search the given english word in Trie.
     * @param word given english word
     * @return list of all words whose prefix string is given word
     * @throws Exception if the given cannot be found
     */
    public List<String> searchWord(String word) throws Exception {
        word = word.toLowerCase();
        boolean result = true;
        TrieNode current = new TrieNode(root);
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            int crt = word.charAt(i) - 97;
            if (current.next[crt] != null) {
                current = current.next[crt];
            } else {
                result = false;
                break;
            }
        }
        if (!result) {
            throw new Exception("No results found for the word " + word + "!");
        } else {
            matchingResult(current, word, res);
            return res;
        }
    }
    /**
     * Search for the given English word in the Trie and return all its Vietnamese meanings.
     * @param word given English word
     * @return list of all Vietnamese meanings associated with the word
     * @throws Exception if the given word cannot be found
     */
    public List<String> searchMeaningWord(String word) throws Exception {
        word = word.toLowerCase();
        if (!wordToMeanings.containsKey(word)) {
            throw new Exception("No results found for the word " + word + "!");
        }
        return wordToMeanings.get(word);
    }

    /**
     * Delete an English word from the Trie.
     * @param wordTarget The English word to be deleted.
     * @throws Exception if the word cannot be found in the Trie.
     */
//    public void deleteWord(String wordTarget) throws Exception {
//        wordTarget = wordTarget.toLowerCase();
//        TrieNode current = new TrieNode(root);
//        TrieNode[] path = new TrieNode[wordTarget.length()];
//
//        int i;
//        for (i = 0; i < wordTarget.length(); i++) {
//            int crt = wordTarget.charAt(i) - 97;
//            if (current.next[crt] != null) {
//                path[i] = current; // Lưu các node cần xóa.
//                current = current.next[crt];
//            } else {
//                throw new Exception("No results found for the word " + wordTarget + "!");
//            }
//        }
//        current.setMeaning(null);
//        for (i = wordTarget.length() - 1; i >= 0; i--) {
//            int crt = wordTarget.charAt(i) - 97;
//            if (current.isEmpty() && path[i] != null) {
//                path[i].next[crt] = null;
//                current = path[i];
//            } else {
//                break;
//            }
//        }
//    }
    public void deleteWord(String wordTarget) throws Exception {
        wordTarget = wordTarget.toLowerCase();
        TrieNode current = root;
        Stack<TrieNode> stack = new Stack<TrieNode>();
        boolean result = true;

        int i;
        for(i = 0; i < wordTarget.length(); i++) {
            int crt = wordTarget.charAt(i) - 97;
            if(current.next[crt] != null) {
                stack.push(current);
                current = current.next[crt];
            } else {
                result = false;
            }
        }
        if (!result) {
            throw new Exception("No results found for the word " + wordTarget + "!");
        } else {
            current.setMeaning(null);
            if (!current.isEmpty() && !stack.isEmpty()) {
                int crt = wordTarget.charAt(stack.size() - 1) - 97;
                stack.pop();
                stack.peek().next[crt] = null;
            }
        }
    }

//    /**
//     * Changes Vietnamese meanings.
//     * @param wordTarget given English word
//     * @param newWordMeaning given Vietnamese meaning
//     */
//    public void changeWord(String wordTarget, String newWordMeaning) {
//        wordTarget = wordTarget.toLowerCase();
//        newWordMeaning = newWordMeaning.toLowerCase();
//        TrieNode current = new TrieNode(root);
//        for (int i = 0; i < wordTarget.length(); i++) {
//            int crt = wordTarget.charAt(i) - 97;
//            current = current.next[crt];
//        }
//        current.setMeaning(newWordMeaning);
//    }

    /**
     * Changes Vietnamese meanings.
     * @param wordTarget given English Word
     * @param newWordExplain given new Vietnamese meaning
     * @throws Exception if the given word cannot be found
     */
    public void changeWord(String wordTarget, String newWordExplain) throws Exception {
        wordTarget = wordTarget.toLowerCase();
        newWordExplain = newWordExplain.toLowerCase();

        if (!wordToMeanings.containsKey(wordTarget)) {
            throw new Exception("No results found for the word!");
        }
        TrieNode current = new TrieNode(root);
        for (int i = 0; i < wordTarget.length(); i++) {
            int crt = wordTarget.charAt(i) - 97;
            current = current.next[crt];
        }

        List<String> meanings = wordToMeanings.get(wordTarget);
        meanings.remove(current.getMeaning());
        meanings.add(newWordExplain);
    }

    /**
     * Display all English words and their corresponding Vietnamese meanings in a formatted table.
     * @throws Exception if an error occurs
     */
    public void showAllWords() throws Exception {

        List<String> allWords = new ArrayList<>();

        matchingResult(root, "", allWords);

        System.out.printf("%-7s%-30s%-30s\n", "NO", "English", "Vietnamese");

        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);
            System.out.printf("%-7d%-30s", i + 1,word);
//            String meaning = searchMeaningWord(word).get(0);
//            System.out.printf("%-7d%-30s%-30s\n", i + 1, word, meaning);
            for (String meaning : searchMeaningWord(word)) {
                System.out.printf(meaning + " ");
            }
            System.out.println();
            ;        }
    }

    public void importToFile() {
        try {
            File file = new File(fileIN);
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length >= 2) {
                    String word = parts[0];
                    String meaning = parts[1];
                    Trie cur = new Trie();
                    cur.add(word, meaning);
                }
            }
            in.close();
        }
        catch (Exception e){
        }
    }
    public void exportToFile() {
        BufferedWriter file = null;
        try {
            file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileIN), StandardCharsets.UTF_8));
            List<String> allWords = new ArrayList<>();

            matchingResult(root, "", allWords);

            for (int i = 0; i < allWords.size(); i++) {
                String word = allWords.get(i);
                String meaning = searchMeaningWord(word).get(0);
                file.write(word + "\t" + meaning);
                file.newLine();
            }
            file.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}