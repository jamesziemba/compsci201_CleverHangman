
public class MinExecutor {
public static void main(String[] args){

		
		for(int wlength=8; wlength <= 8; wlength++){int num = 1;boolean ignored = false;while(!ignored){
		    GuessExecutor ng = new GuessExecutor(wlength,num, new FrequencyGuesser());
		    ignored = ng.stress1();num++;}
		    if(ignored==true){num = num-1;System.out.println("It takes "+num+" guesses for words that are "+wlength+" letters long");}
		}
	}
		
		
}
