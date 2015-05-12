import java.util.Scanner;


/**
 * This class launches a Hangman game
 * and plays once, used to demonstrate the HangmanGame hierarchy and the
 * IHangGuesser interface hierarchy
 * @author Owen Astrachan
 *
 */
public class HangmanExecuter {
	static int i;
	static int j;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("How long should the word be: ");
		i = in.nextInt();
		System.out.println("How many misses do you get: ");
		j = in.nextInt();
		SnarkyHangman game = new SnarkyHangman(HangmanFileLoader.getRandomWord(i),j);
		IHangGuesser guesser = new HumanGuesser(); 
		guesser.gameSetup(game);
		while (true){
			
			if (guesser.nextGuess()){
				break;
			}

		}
		
		if (game.gameOverHung()) {
			System.out.println("you lose!");
		}
		else if (game.gameOverGuessed()) {
			System.out.println("you win!");
		}
		System.out.printf("secret word is %s\n",game.getSecret());
	}
}
