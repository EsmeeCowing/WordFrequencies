public class WordEntry implements Comparable {
    public String word = null;
    public int frequency;
    public double computedProb;
    public double cumulativeProb;
    public WordTable followingWords = new WordTable();

    public WordEntry(String word){
        this.word = word;
        this.frequency = 1;
    }

    public String toString(){
      return ("Word: "+ this.word +" Frequency: "+ this.frequency +" Computed Probability: "+ this.computedProb +" Cumulative Probability: "+ this.cumulativeProb);
    }

    //makes it so that bigger frequencies come first so that its easier to find the most used words
    @Override
    public int compareTo(Object otherWordEntry) {
        return ((WordEntry)otherWordEntry).frequency-this.frequency;
    }
}
