package plant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.princeton.cs.algs4.TST;

public class SpellChecker {
	TST<String> tst;
	
	public static void main(String[] args){
		if(args.length !=2){
			System.err.println("Invalid command line, 2 arguments required");
			System.exit(1);
		}
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(args[0]));
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try {
			System.out.println(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpellChecker sp = new SpellChecker();
	}
	
	public SpellChecker(){
		tst = new TST<String>();
	}
	
	public void getWords(){

	}
}
