import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;


public class FrequencyGuesser implements IHangGuesser {
	private HangmanGame myGame = null;
	ArrayList<String> wordlist;
	ArrayList<Character> alreadyguessed;
	@Override
	public void gameSetup(HangmanGame game) {
		wordlist = new ArrayList<String>(HangmanFileLoader.getAllWords(game.mySecret.length()));
		myGame = game;
		alreadyguessed = new ArrayList<Character>();
		
	}


	@Override
	public boolean nextGuess() {
		Map<Character,Integer> lettermap = new HashMap<Character,Integer>();
		for(String s:wordlist){
			ArrayList<Character> unique = new ArrayList<Character>(); 
			for(char c:s.toCharArray()){
				unique.add(c);
			}
			for (char c : unique){
				if(!lettermap.containsKey(c)){
					lettermap.put(c, 0);
				}
				lettermap.put(c, lettermap.get(c)+1);
			}
		}


		int maxcount = 0;
		char guess = '\0';
		for(char c:lettermap.keySet()){
			if(lettermap.get(c)>=maxcount && !alreadyguessed.contains(c)){
				maxcount = lettermap.get(c);
				guess= c;
			}
		}
		alreadyguessed.add(guess);
		boolean miss = myGame.makeGuess(guess);
		Iterator<String> it = wordlist.iterator();
		if(miss){
			while (it.hasNext()){
				String s = it.next();
				if (s.indexOf(guess)>-1) {
					it.remove();
				}
			}

		}
		if(!miss){
			while (it.hasNext()){
				String s = it.next();
				if (s.indexOf(guess)==-1) {
					it.remove();
				}
			}
		}
//		if(myGame.gameOverHung()){
//			System.out.println(myGame.mySecret);
//		}

		return myGame.gameOverHung() || myGame.gameOverGuessed();

	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

}
