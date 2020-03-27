import java.util.*;

public class WordTable {
    public HashMap<String, WordEntry> wordToEntry = new HashMap<String, WordEntry>();
    public List<WordEntry> wordEntriesSortedByFrequency = new ArrayList<WordEntry>();
    private static Random randomizer = new Random();

    public WordEntry recordWord(String word){
        if (this.wordToEntry.get(word) == null) {
            this.wordToEntry.put(word, new WordEntry(word));
        }
        else{
            this.wordToEntry.get(word).frequency ++;
        }
        return this.wordToEntry.get(word);
    }

    public WordEntry findEntry(String word){
        return this.wordToEntry.get(word);
    }

    public void processEntries(int n){
        for (String word : this.wordToEntry.keySet()){
            wordEntriesSortedByFrequency.add(findEntry(word));
        }

       wordEntriesSortedByFrequency = Sort.frequencySort(wordEntriesSortedByFrequency);

       double totalFrequency = 0;
       for (WordEntry wordEntry : wordEntriesSortedByFrequency){
           totalFrequency += wordEntry.frequency;
       }

       double previousCumulativeProbability = 0;
       for (int i = 0; i < wordEntriesSortedByFrequency.size(); i++){
           wordEntriesSortedByFrequency.get(i).computedProb = wordEntriesSortedByFrequency.get(i).frequency/totalFrequency;
           wordEntriesSortedByFrequency.get(i).cumulativeProb = wordEntriesSortedByFrequency.get(i).computedProb + previousCumulativeProbability;
           if (i == wordEntriesSortedByFrequency.size()-1){
               wordEntriesSortedByFrequency.get(i).cumulativeProb = 1;
           }
           previousCumulativeProbability = wordEntriesSortedByFrequency.get(i).cumulativeProb;
       }

       if (n > 1) {
           for (String word : this.wordToEntry.keySet()) {
               findEntry(word).followingWords.processEntries(n-1);
           }
       }
    }

    public WordEntry randomEntry(){
        double randomVal = randomizer.nextDouble();
        for (WordEntry wordEntry : wordEntriesSortedByFrequency){
            if (randomVal <= wordEntry.cumulativeProb){
                return wordEntry;
            }
        }
        throw new IllegalStateException("There was no word entry with the cumulative probablility that was just generated.");
    }

    public void displayCommonWords(int length, int n, String indent){
        for (int i = 0; i < length; i++){
            if (i < wordEntriesSortedByFrequency.size()) {
                System.out.println(indent+wordEntriesSortedByFrequency.get(i));
                if (n > 1 && wordEntriesSortedByFrequency.get(i).followingWords != null){
                    wordEntriesSortedByFrequency.get(i).followingWords.displayCommonWords(length,n-1, indent+"    ");
                }
            }
        }
    }

}
