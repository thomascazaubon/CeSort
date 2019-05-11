

import java.awt.EventQueue;


public class Questions {
	
	final int SIZE = 11;	//The nb of questions
	//The content of each question
	final String qu1 = "[Question 1 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu2 = "[Question 2 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu3 = "[Question 3 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu4 = "[Question 4 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu5 = "[Question 5 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu6 = "[Question 6 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu7 = "[Question 7 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu8 = "[Question 8 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu9 = "[Question 9 Content xxxxxxxxxxxxxxxxxx]" ;
	final String qu10 = "[Question 10 Content xxxxxxxxxxxxxxxxxx]" ;

	public String tab_questions [] = new String [SIZE] ;	//The question table
	public String tab_answers [] = new String [SIZE] ;  	//The answer table
	//TO CHECK: tab_answers will not necessery only contain String


	
	public Questions() {
		tab_questions[1] = qu1 ;
		tab_questions[2] = qu2 ;
		tab_questions[3] = qu3 ;
		tab_questions[4] = qu4 ;
		tab_questions[5] = qu5 ;
		tab_questions[6] = qu6 ;
		tab_questions[7] = qu7 ;
		tab_questions[8] = qu8 ;
		tab_questions[9] = qu9 ;
		tab_questions[10] = qu10 ;

	}

	//Get a question content from the question index
	public String get_question(int index) {
		return tab_questions[index] ;
	}
	
	//Get an answer from the question index
		public String get_answer(int index) {
			return tab_answers[index] ;
		}
	
	//Get the number of questions
	public int get_nb_questions() {
		return SIZE ;
	}
	
	//Set an answer from the question index
	public void set_answer(int index, String answer) {
		tab_answers[index] = answer ;

	}
		
		
	public static void main(String[] args) {
		
	}

	
	
}

