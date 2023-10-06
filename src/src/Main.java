import java.util.List;

public class Main {
    public static void main(String[] args) {
        Trie t = new Trie();
        t.add("Hello", "Xin chao");
        t.add("School", "Truong hoc");
        t.add("Computer", "May tinh");
        t.add("Company", "Cong ty");
        try {
            t.add("Company", "Nha may");
            System.out.println(t.searchMeaningWord("company"));
            System.out.println(t.searchWord("co"));
            //t.exportToFile();
            //t.importToFile();

            t.showAllWords();
        } catch (Exception exception) {
            System.out.print(exception);
        }
    }
}
