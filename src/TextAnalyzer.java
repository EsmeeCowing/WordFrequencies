import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextAnalyzer {
    private WordTable baseTable;
    List<String> textAsList;
    int numBranches;

    public  TextAnalyzer(String fileName, int numBranches){
        this.baseTable = new WordTable();
        this.textAsList = new ArrayList<String>(); // is there a more efficient way to do this?
        this.numBranches = numBranches;

        try {
            FileReader file = new FileReader("Texts/"+fileName);
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null) {
                for (String word : line.split(" ")) {
                    word = word.trim();
                    if (word.length() > 0) {
                        textAsList.add(word);
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException ioexception) {
            System.out.println("Oops! Your code asked to do something that wasn't possible: " + ioexception.getMessage());
        }

        enterDataIntoTables();
    }

    public void enterDataIntoTables(){
        for (int i=0 ;i < textAsList.size()-1; i++){
            WordTable currentTable = (baseTable.recordWord((textAsList.get(i)))).followingWords;
            for (int i2=1 ;i2 <= this.numBranches; i2++) {
                if (i+i2 < textAsList.size()) {
                    currentTable = currentTable.recordWord(textAsList.get(i + i2)).followingWords;
                }
            }
        }
        baseTable.processEntries(this.numBranches);
    }

    public void generateStats(int length){
        baseTable.displayCommonWords(length, this.numBranches, "");
    }

    public void generateImitationText(int textLength){
        String finalText = "";
        int wordCount = 0;
        String lastWord = "";
        int tableDeepness = this.numBranches; //this must be variable because, for the text to sound more continuous it must only return to the word table of the word that was just used and not make a random selection from the base table

        while (wordCount < textLength){
            WordTable currentTable = this.baseTable;

            if (wordCount > 0 && this.numBranches > 1){
                currentTable = this.baseTable.findEntry(lastWord).followingWords;
                tableDeepness = this.numBranches-1;
            }

            for (int i = 0; i < tableDeepness; i++) {
                String newWord = currentTable.randomEntry().word;
                finalText += newWord + " ";

                currentTable = currentTable.findEntry(newWord).followingWords;
                lastWord = newWord;
                wordCount ++;

                if (wordCount % 20 == 0) {
                    finalText += "\n";
                }
            }
        }
        System.out.println(finalText);
    }
}
