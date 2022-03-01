import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import java.io.File;

public class exampleWithSets {
	
	public static void main (String[] args){
		
		HashSet<String> dict = new HashSet<>();
		HashSet<String> wordstoAdd = new HashSet<>();
		
		//Read the words dictionary and store it into a HashSet
		try {
			Scanner reader = new Scanner(new File("words.txt"));
			reader.useDelimiter("[^a-zA-Z]+");
			
			while (reader.hasNext()) {
				String word = reader.next().toLowerCase().strip();
				dict.add(word);
			}
			reader.close();
			
			System.out.println("Words in the dictionary: "+dict.size()); 
			
			
		}catch (IOException FNF) {
			System.out.println("File not found exception"); 
			FNF.printStackTrace();
		}
		
		// get user file and read contents comparing each word to the dictionary
		
		File doc = getInputFileNameFromUser();
		try {
			Scanner reader = new Scanner(doc);
			reader.useDelimiter("[^a-zA-Z]+");
			
			while (reader.hasNext()) {
				String newWord = reader.next().toLowerCase().strip();
				if ( !dict.contains(newWord)) {
					wordstoAdd.add(newWord);
				}	
			}
			reader.close();			
			
			System.out.println("Your document has "+wordstoAdd.size()+" not in the dictionary");
			System.out.println();
			System.out.println(wordstoAdd);
			
		}catch (IOException FNF) {
			System.out.println("File not found exception"); 
			FNF.printStackTrace();
		}

		
			//pass the missing words into the collection method for error checking and generating suggestions
		Iterator<String> iter = wordstoAdd.iterator();
		while(iter.hasNext()) {
			String wordtoCheck = iter.next().toLowerCase().strip();
			TreeSet<String> suggest = corrections(wordtoCheck, dict);
			if (suggest.isEmpty()){
				System.out.println("no suggestions for "+ wordtoCheck);
			}else {
				System.out.println("Suggestions for "+wordtoCheck+": "+suggest);
			}
		}
		
	}
	
	
	/**
     * Lets the user select an input file using a standard file
     * selection dialog box.  If the user cancels the dialog
     * without selecting a file, the return value is null.
     */
    static File getInputFileNameFromUser() {
       JFileChooser fileDialog = new JFileChooser();
       fileDialog.setDialogTitle("Select File for Input");
       int option = fileDialog.showOpenDialog(null);
       if (option != JFileChooser.APPROVE_OPTION)
          return null;
       else
          return fileDialog.getSelectedFile();
    }
    
    
    static TreeSet<String> corrections(String badWord, HashSet dictionary) {
    	
    	TreeSet<String> suggestion = new TreeSet<>();
    	
    	for (int i=0; i<=badWord.length()-1; i++) {
    		//split the word into two words using spaces then check whether both words are in the dictionary
    		String spaced1 = (badWord.substring(0,i) + badWord.substring(i+1)).trim();
    		String spaced2 = (badWord.replace(spaced1, "")).strip();
    		if(dictionary.contains(spaced1)) {
        		suggestion.add(spaced1);
        		}
    		if(dictionary.contains(spaced2)) {
        		suggestion.add(spaced2);
        		}
    		
    		//delete random chars from the word and check again
    		String substr = badWord.substring(i);
    		if(dictionary.contains(substr)) {
        		suggestion.add(substr);
        		}
    		String substr2 = badWord.replace(substr, "");
    		if(dictionary.contains(substr2)) {
        		suggestion.add(substr2);
        		}
    		
    		
    		//swap any char in the word and check again
    		for (int j=badWord.length()-1; j>i; j--) {
    			char currentChar = badWord.charAt(i);
    			char nextChar =badWord.charAt(j);
    			String mixedWord = badWord.replace(currentChar, nextChar);
    			String mixedWord2 = mixedWord.replace(nextChar, currentChar);
    			if(dictionary.contains(mixedWord2)) {
            		suggestion.add(mixedWord2);
            		}
    		}
    			
    		// add a character at any location of the word then check in the dict once more
    		for (char ch = 'a'; ch <= 'z'; ch++) {
        		String s = badWord.substring(0,i) + ch + badWord.substring(i+1);
        		s.toLowerCase().strip();
        		if(dictionary.contains(s)) {
        		suggestion.add(s);
        		}
        	}
    	}
    	return suggestion;
    }

}
