package plant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.TST;

public class SpellChecker {
	TST<Integer> tst;
	char[][] keyboard;

	public static void main(String[] args){
		if(args.length !=2){
			System.err.println("Invalid command line, 2 arguments required");
			System.exit(1);
		}

		SpellChecker sp = new SpellChecker();

		try{
			sp.getDictionary(args[0]);
		}
		catch(IOException e){
			System.err.println("Check filepath and text file");
		}

		String word = "doog";
		ArrayList<String> list = sp.checkWord(word);
		if(list != null){
			System.out.println(word + ": Did you mean");
			for(int i = 0; i < list.size(); i++){
				System.out.println("\t" + (i+1) + ": " + list.get(i));
			}
		}
	}

	public SpellChecker(){
		tst = new TST<Integer>();
		keyboard = new char[][]{{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'}, {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', '\''}, {'z', 'x', 'c', 'v', 'b', 'n', 'm'}};
	}

	public void getDictionary(String filename) throws IOException{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(filename));
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}

		String word;
		int i = 0;
		while ((word = br.readLine()) != null) {
			if(word.length() >= 1){
				tst.put(word, i);
				i++;
			}
		}
	}

	public ArrayList<String> checkWord(String word){
		word = word.toLowerCase();

		if(tst.contains(word)){
			return null;
		}

		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < word.length(); i++){
			char[] letters = word.toCharArray();
			letters[i] = '.';
			String newWord = new String(letters);
			Queue<String> queue = (Queue<String>) tst.keysThatMatch(newWord);
			for(String s : queue){
				keyboardDistance(newWord.charAt(i), word.charAt(i));
				list.add(s);
			}
		}
		return list;
	}

	public void keyboardDistance(char char1, char char2){
		int x1, y1, x2, y2;
		for(x1 = 0; x1 < keyboard.length; x1++){
			for(y1 = 0; y1 < keyboard[0].length; y1++){
				if(keyboard[x1][y1] == char1)
					break;
			}
		}
	}
}
