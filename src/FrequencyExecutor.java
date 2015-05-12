
public class FrequencyExecutor {
	public static void main(String[] args){

		for(int wlength=10; wlength <=10; wlength++){
			GuessExecutor ng = new GuessExecutor(wlength,10, new FrequencyGuesser());
			System.out.printf("word length: %d\n",wlength);
			boolean ignored = ng.stress();
		}
	}

}
