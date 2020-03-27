import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Sort {
    public static void quickSort(List data){
        quickSort(data, 0, data.size()-1);
    }

    public static void quickSort(List data, int lowerBound, int upperBound) {
        int smallerThanTracker = lowerBound;
        int biggerThanTracker = upperBound;
        if (upperBound-lowerBound >= 1) {
            int pivot = ((lowerBound+ upperBound) / 2);
            Comparable pivotValue = (Comparable) data.get(pivot);
            int upperBoundForLowerList = moveTrackers(data, smallerThanTracker, biggerThanTracker, pivotValue);
            quickSort(data, lowerBound, upperBoundForLowerList);
            quickSort(data, upperBoundForLowerList+1, upperBound);
        }
    }

    private static int moveTrackers(List data, int smallerThanTracker, int biggerThanTracker, Comparable pivotValue){
        while (true) {
            while (((Comparable) data.get(biggerThanTracker)).compareTo(pivotValue) > 0) {
                biggerThanTracker--;
            }
            while (((Comparable) data.get(smallerThanTracker)).compareTo(pivotValue) < 0) {
                smallerThanTracker++;
            }
            if (biggerThanTracker > smallerThanTracker) {
                Comparable oldBigVal = (Comparable) data.get(biggerThanTracker);
                data.set(biggerThanTracker, data.get(smallerThanTracker));
                data.set(smallerThanTracker, oldBigVal);
                biggerThanTracker--;
                smallerThanTracker++;
            }
            else{
                return biggerThanTracker;
            }
        }
    }

    public static ArrayList<WordEntry> frequencySort(List<WordEntry> wordEntries){
        int maxFrequency = 0;
        for (WordEntry wordEntry : wordEntries){
            if (wordEntry.frequency > maxFrequency){
                maxFrequency = wordEntry.frequency;
            }
        }
        LinkedList<WordEntry>[] sortingArray = new LinkedList[maxFrequency];
        for (int i = 0; i < sortingArray.length; i++){
            sortingArray[i] = new LinkedList<WordEntry>();

        }
        for (WordEntry wordEntry : wordEntries){
            sortingArray[wordEntry.frequency-1].add(wordEntry); //there will never be a word with a 0 frequency, so we can subtract 1
        }
        ArrayList returnList = new ArrayList();
        for (int i = maxFrequency-1; i>=0; i--){
            returnList.addAll(sortingArray[i]);
        }
        return returnList;
    }
}
