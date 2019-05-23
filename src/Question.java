

import java.util.HashMap;
import java.util.Map;

public class Question {
	
	private static boolean firstTime = true;
	private static HashMap<String, Question> questions; 
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private String title;
	private int num;
	private HashMap<String, String> answers;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	private Question (String pTitle, HashMap<String, String> pAnswers) {
		this.title = pTitle;
		this.answers = pAnswers;
		this.num = 0;
	}
	
	/* * * * * M E T H O D S * * * * */
	
	/**
	 * Create the questions that can be asked to the user
	 */
	private static void createQuestions() {
		questions = new HashMap<String, Question>();

		
		// Question 1
		HashMap<String, String> answers1 = new HashMap<String, String>();
		answers1.put("oem", "An OEM");
		answers1.put("motorist", "A Motorist");
		answers1.put("tier", "A Tier-1, 2 or 3");
		questions.put("kindOfOrganisation", new Question("What kind of organisation is concerned by the Certification Process?", answers1));
		
		// Question 2
		HashMap<String, String> answers2 = new HashMap<String, String>();
		answers2.put("aircraft", "An Aircraft");
		answers2.put("motorOrPropulsionSystem", "A Motor or Propulsion system");
		answers2.put("appliance", "An Appliance");
		answers2.put("part", "A Part");
		questions.put("kindOfProduct", new Question("What kind of product is concerned by the Certification Process?", answers2));
		
		// Question 3
		HashMap<String, String> answers3 = new HashMap<String, String>();
		answers3.put("yes", "Yes");
		answers3.put("no", "No");
		questions.put("headOfficeInEU", new Question("Is the head office of the organisation in the EU or in one of these countries : Iceland, Liechtenstein, Norway or Swiss?", answers3));
		
		// Question 4
		HashMap<String, String> answers4 = new HashMap<String, String>();
		answers4.put("moreThan2T", "More than 2T");
		answers4.put("lessThan2T", "Less than 2T");
		questions.put("weight", new Question("What is the weight of the aircraft concerned by the Certification Process?", answers4));
		
		// Question 5
		HashMap<String, String> answers5 = new HashMap<String, String>();
		answers5.clear();
		answers5.put("yes", "Yes");
		answers5.put("no", "No");
		questions.put("standardPart", new Question("Is it a standard part?", answers5));
		
		// Question 6
		HashMap<String, String> answers6 = new HashMap<String, String>();
		answers6.put("design", "Design");
		answers6.put("production", "Production");
		answers6.put("both", "Both");
		questions.put("mainResponsabilities", new Question("What will be the main responsabilities of the organisation?", answers6));
		
		// There is no question 7
		
		// Question 8
		HashMap<String, String> answers8 = new HashMap<String, String>();
		answers8.put("yes", "Yes");
		answers8.put("no", "No");
		questions.put("typeCertificate", new Question("Does the organisation hold already a type certificate?", answers8));
		
		// Question 9
		HashMap<String, String> answers9 = new HashMap<String, String>();
		answers9.put("newProgram", "New program");
		answers9.put("modificationRequest", "Modification request");
		questions.put("modificationRequestOrNewProgram", new Question("Does the organisation want to request a modification for an already held TC or does the certification process concern a nex program?", answers9));
		
		// Question 10
		HashMap<String, String> answers10 = new HashMap<String, String>();
		answers10.put("yes", "Yes");
		answers10.put("no", "No");
		questions.put("requestModification", new Question("Does the organisation want to request a modification for the current certification process?", answers10));
		
		// Question 11
		HashMap<String, String> answers11 = new HashMap<String, String>();
		answers11.put("en9100", "EN 9100");
		answers11.put("designApproval", "Design approval");
		answers11.put("productionApproval", "Production approval");
		answers11.put("tcAndCoA", "TC and CoA");
		questions.put("nextTarget", new Question("What is the next target for the Organisation?", answers11));
		
		// Question 12
		HashMap<String, String> answers12 = new HashMap<String, String>();
		answers12.put("yes", "Yes");
		answers12.put("no", "No");
		questions.put("privilegeFromEASA", new Question("Does the organisation have any privilege from EASA?", answers12));
		
		// Question 13
		HashMap<String, String> answers13 = new HashMap<String, String>();
		answers13.put("yes", "Yes");
		answers13.put("no", "No");
		questions.put("operationalConditionsMightBeRestricted", new Question("Do you think the operational conditions might be restricted?", answers13));	
		
	}
	
	/* * * * * G E T T E R S  &  S E T T E R S * * * * */
	
	public static HashMap<String, Question> getQuestions() {
		if (firstTime) 
		{
			firstTime = false;
			createQuestions();
		}
		return questions;
	}

	public String getTitle() {
		return title;
	}

	public HashMap<String, String> getAnswers() {
		return answers;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public static String getQuestionByNum(int num) {
		String keyQuest = null;
		for (Map.Entry<String, Question> mapEntry : questions.entrySet()) {
			String keyQuestion = mapEntry.getKey();
	        	Question loopQuestion = mapEntry.getValue();
			if (loopQuestion.getNum() == num) {
	        		keyQuest = keyQuestion;
			} 
		}
		return keyQuest;
	}
}
