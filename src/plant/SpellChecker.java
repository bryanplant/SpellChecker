package plant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
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
			System.err.println("Check filepaths and text files");
		}

		try{
			sp.read(args[1]);
		}
		catch(IOException e){
			System.err.println("Check filepaths and text files");
		}


		sp.keyboardDistance('b', 'g');
		sp.keyboardDistance('b', 'j');
	}

	public SpellChecker(){
		tst = new TST<Integer>();
		keyboard = new char[][]{{'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'}, {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', '\''}, {'z', 'x', 'c', 'v', 'b', 'n', 'm', ' ', ' ', ' '}};
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
		br.close();
	}

	public void read(String filename) throws IOException{
		Scanner sc = null;
		try{
			sc = new Scanner(new FileReader(filename));
		} catch(FileNotFoundException e){
			throw new IOException();
		}



		String corrected = "";
		while(sc.hasNextLine()){
			Scanner sc2 = new Scanner(sc.nextLine());
			Scanner in = new Scanner(System.in);
			String word;
			while(sc2.hasNext()){
				word = sc2.next();

				ArrayList<String> list = checkWord(word);
				if (list == null){}
				else{
					int i = 0;
					System.out.println(word + ": Did you mean");
					for(i = 0; i < list.size(); i++){
						System.out.println("\t" + (i+1) + ": " + list.get(i));
					}
					System.out.println("\t" + (i+1) + ": " + "somthing else");
					boolean valid = false;
					while(!valid){
						try{
							word = list.get(Integer.parseInt(in.next())-1);
							valid = true;
						}
						catch(Exception e){
							System.out.println("Enter a number between 1 & " + (list.size()+1));
						}
					}
				}
				corrected = corrected.concat(word + " ");
			}
		}

		BufferedWriter bw = null;
		FileWriter fw = null;

		try{
			fw = new FileWriter("mydoc-checked.txt");
			bw = new BufferedWriter(fw);
		}
		catch(IOException e){
			sc.close();
			throw new IOException();
		}
		bw.write(corrected);
		bw.close();

		sc.close();
	}

	public ArrayList<String> checkWord(String word){
		word = word.toLowerCase();
		StringBuilder sb = new StringBuilder(word);
		if(sb.indexOf(".") != -1)
			sb.deleteCharAt(sb.indexOf("."));
		word = sb.toString();

		if(tst.contains(word)){
			return null;
		}

		ArrayList<String> list = new ArrayList<>();
		for(int i = 0; i < word.length(); i++){
			char[] letters = word.toCharArray();
			letters[i] = '.';
			String newWord = new String(letters);
			Queue<String> queue = (Queue<String>) tst.keysThatMatch(newWord);
			for(String s : queue){
				//double distance = keyboardDistance(newWord.charAt(i), word.charAt(i));
				list.add(s);
			}
		}
		return list;
	}

	public double keyboardDistance(char char1, char char2){
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		for(int i = 0; i < keyboard.length; i++){
			for(int j = 0; j < keyboard[0].length; j++){
				if(keyboard[i][j] == char1){
					x1 = i;
					y1 = j;
				}
			}
		}
		for(int i = 0; i < keyboard.length; i++){
			for(int j = 0; j < keyboard[0].length; j++){
				if(keyboard[i][j] == char2){
					x2 = i;
					y2 = j;
				}
			}
		}
		double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		return distance;
	}
}
