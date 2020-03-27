public class Main {
    public static void main(String[] args) {
        TextAnalyzer testAnalyster = new TextAnalyzer("HarryPotter.txt", 2); //numBranches is the number grams
        testAnalyster.generateStats(3); //the length here is the length of the stats list, for example: top 10, top 5, or top 20.
        testAnalyster.generateImitationText(250);
    }
}
