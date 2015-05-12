import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SnarkyHangman extends HangmanGame {
	public static char BLANK = '_';
	protected String mySecret;
	protected StringBuilder myDisplay;
	protected boolean[] myLettersGuessed;
	protected int myMissCount;
	protected int myHangCount;
	protected ArrayList<String> myData;
	
	public SnarkyHangman(String secret, int misses) {
		super(secret, misses);
		mySecret = secret;
		myLettersGuessed = new boolean[26];
		myHangCount = misses;
		myMissCount = 0;
		myDisplay = new StringBuilder(secret.length());
		myData = HangmanFileLoader.getAllWords(secret.length());

		for(int k=0; k < secret.length(); k++){
			myDisplay.append(BLANK);
		}
	}

	/**
	 * Construct a Hangman game with a random word chosen from list returned via 
	 * HangmanFileLoader. The game will be played using some kind of IHangGuesser.
	 * @param wordlength is length of secret word that will be guessed, chosen at random
	 * @param misses is the number of missed-guesses until hung
	 */
	public SnarkyHangman(int wordlength, int misses) {
		this(HangmanFileLoader.getRandomWord(wordlength),misses);
	}
	public boolean gameOverHung() {
		return myMissCount >= myHangCount;
	}
	public boolean gameOverGuessed(){
		for(int k=0; k < myDisplay.length(); k++){
			if (myDisplay.charAt(k) == BLANK){
				return false;
			}
		}
		return true;
	}
	public String getSecret(){
		if (gameOverHung() || gameOverGuessed()){
			return mySecret;
		}
		return "game is not over, you know "+myDisplay;
	}
	public String getDisplay(){
		return myDisplay.toString();
	}
	public String[] processLetter(String secret, char letter, String[] blanks){
		for(int j = 0;j<secret.length();j++){
			if(secret.charAt(j) == letter){
				blanks[j] = Character.toString(letter);

			}
		}
		return blanks;
	}
	// show the word: the letters if it was guessed and a "_" if it was not
	public String showBlanks(String[] blanks){
		String retval = "";
		for(int i = 0;i < blanks.length;i++){
			String str = blanks[i] + " ";
			retval += str;
		}
		return retval;
	}
	public ArrayList<String> makeDict(String n,ArrayList<String> data){
		HashMap<String,ArrayList<String>> hmap = new HashMap<String,ArrayList<String>>();
		for(String s: data){
			String[] blank1 = new String[s.length()];
			for(int i =0;i<s.length();i++){
				blank1[i]="_";
			}
			String[] pattern = processLetter(s,n.charAt(0),blank1);
			String shown = showBlanks(pattern);
			if(!hmap.containsKey(shown)){
				hmap.put(shown, new ArrayList<String>());
			}
			hmap.get(shown).add(s);
		}
		int maximum = 0;
		ArrayList<String> maxlist = new ArrayList<String>();
		for(String key : hmap.keySet()){
			if(hmap.get(key).size()>maximum){
				maximum = hmap.get(key).size();
				maxlist = hmap.get(key);
			}

		}
		data = maxlist;
		return data;
	}
	public boolean makeGuess(char ch){
		int count = 0;
		if (myLettersGuessed[ch-'a']){
			return false;
		}
		myLettersGuessed[ch-'a'] = true;
		myData = makeDict(Character.toString(ch),myData);
		Collections.shuffle(myData);
		mySecret = myData.get(0);
//		System.out.println(mySecret);
//		System.out.println(myData.size());
		for(int k=0; k < mySecret.length(); k++){
			if (mySecret.charAt(k) == ch){
				count++;
				myDisplay.setCharAt(k,ch);
			}
		}
		if (count == 0){
			myMissCount++;
		}
		return count == 0;
	}
	public int missesLeft(){
		return myHangCount - myMissCount;
	}


}
