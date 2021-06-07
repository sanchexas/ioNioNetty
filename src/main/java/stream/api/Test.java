package stream.api;

public class Test {
    public static void main(String[] args) {
        CollectTextToMap map = new CollectTextToMap();
        map.getWordsMap("text.txt").entrySet().stream()
                .sorted((e1,e2)-> e2.getValue() - e1.getValue())
                .forEach(entry ->
                        System.out.println(entry.getKey() + " - " + entry.getValue()));
    }
}
