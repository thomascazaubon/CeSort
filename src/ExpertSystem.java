

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import gnu.prolog.database.PrologTextLoaderError;
import gnu.prolog.demo.mentalarithmetic.NoAnswerException;
import gnu.prolog.term.Term;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;

public class ExpertSystem {
	
	final static private int NBCRITERIA = 12; 
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private String[][] knowledge;
	private Environment env;
	private Interpreter interpreter;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public ExpertSystem() {
		this.knowledge = new String[][]{{
			"kindOfOrganisation", 
			"kindOfProduct", 
			"headOfficeInEU", 
			"weight", 
			"standardPart", 
			"mainResponsabilities", 
			"typeCertificate", 
			"requestModification", 
			"nextTarget", 
			"modificationRequestOrNexProgram",
			"privilegeFromEASA",
			"operationalConditionsMightBeRestricted"},
			{"_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_"}};
	}
	
	/* * * * * G E T T E R S  &  S E T T E R S * * * * */
	
	/**
	 * Modify the knowledge according to the parameters
	 */
    public void setKnowledge(String name, String value) 
	{ 
    	for(int i = 0; i < NBCRITERIA; i++) {
    		if(this.knowledge[0][i].equals(name)) 
    		{
    			this.knowledge[1][i] = value;
    		}
    	 }
 	}
 
    public void resetKnowledge() {
    	for(int i = 0; i < NBCRITERIA; i++) {
    		this.knowledge[1][i] = "_";
    	 }
    }
    
    public HashMap<String, String> getKeyAnswers() {
    	HashMap<String, String> keyAnswers = new HashMap<String, String>();
    	for(int i = 0; i < NBCRITERIA; i++) {
    		if(!knowledge[1][i].equals("_")) {
    			keyAnswers.put(knowledge[0][i], knowledge[1][i]);
    		}
    	}
    	return keyAnswers;
    }
    
	/* * * * * M E T H O D S * * * * */
	
	/**
	 * Write the knowledge in a Prolog file.
	 */
    private void writeKnowledge() 
	{ 
    	System.out.println("PATH : " + ExpertSystem.class.getResource("./").getFile());
		//String file = ExpertSystem.class.getResource("knowledge.pl").getFile(); 
		try { 
		   	FileWriter fw = new FileWriter("Lib/knowledge.pl", false); 
		  	BufferedWriter output = new BufferedWriter(fw); 
		  	//System.out.println("[DEBUG] Writing in knowledge.pl : ");
		  	for(int i = 0; i < NBCRITERIA; i++) {
		  		output.write(this.knowledge[0][i] + "(" + this.knowledge[1][i] + ").\n");
		  		//System.out.println("\t" + this.knowledge[0][i] + "(" + this.knowledge[1][i] + ").");
	    	 }
		  	output.flush(); 
		  	output.close(); 
	  	} catch(IOException ioe)
		{ 
			System.out.print("[ERROR] "); ioe.printStackTrace();
		} 
 	}
    
    /**
	 * Read the knowledge from knowledge.pl.
	 */
    public void readKnowledge() 
	{ 
		//String file = ExpertSystem.class.getResource("knowledge.pl").getFile(); 
		try { 
		   	FileReader fr = new FileReader("Lib/knowledge.pl"); 
		  	BufferedReader input = new BufferedReader(fr);
		  	String line;
		  	while ((line = input.readLine()) != null) {
		  	   String keyQuestion = line.split("\\(")[0];
		  	   String keyAnswer = line.split("\\(")[1].split("\\)")[0];
		  	   setKnowledge(keyQuestion, keyAnswer);
		  	}
		  	input.close(); 
	  	} catch(IOException ioe)
		{ 
			System.out.print("[ERROR] "); ioe.printStackTrace();
		} 
 	}
    
    /**
	 * Print the content of knowledge[][]
	 */
    public void printKnowledge() 
	{ 
    	System.out.println("[DEBUG] Content of knowledge[][] : ");
    	for(int i = 0; i < NBCRITERIA; i++) {
			System.out.println(knowledge[0][i] + "(" + knowledge[1][i] + ").");
		}
 	}

	/**
	 * Ensure that we have an environment and have loaded the prolog code and have
	 * an interpreter to use.
	 */
	private synchronized void setup()
	{
		// Construct the environment
		this.env = new Environment();

		// Get the prolog files to use
		/*
		this.env.ensureLoaded(AtomTerm.get(ExpertSystem.class.getResource("knowledge.pl").getFile()));
		this.env.ensureLoaded(AtomTerm.get(ExpertSystem.class.getResource("knowledge.pl").getFile()));
		 */
		this.env.ensureLoaded(AtomTerm.get("Lib/knowledge.pl"));
		this.env.ensureLoaded(AtomTerm.get("Lib/rules.pl"));
		// Get the interpreter.
		
		this.interpreter = this.env.createInterpreter();
		// Run the initialization
		this.env.runInitialization(this.interpreter);
	}
	
	/**
	 * Collect debugging information if something has gone wrong
	 */
	private void debug()
	{
		List<PrologTextLoaderError> errors = this.env.getLoadingErrors();
		for (PrologTextLoaderError error : errors)
		{
			error.printStackTrace();
		}
	}
	
	/**
	 * Return the first answer of the predicate.
	 */
	public String reason() throws PrologException, NoAnswerException
	{
		this.writeKnowledge();
		this.setup();
		// // Construct the question.
		// Create variable terms so that we can pull the answers out later
		VariableTerm answerTerm = new VariableTerm("Answer");
		// Create the arguments to the compound term which is the question
		Term[] args = { answerTerm };
		// Construct the question
		CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("reasoner"), args);
		// Create the answer
		String answer = null;

		synchronized (interpreter)// so that this class is thread safe.
		{
			// Print out any errors
			this.debug();
			
			// Execute the goal and return the answer.
			Goal goal = interpreter.prepareGoal(goalTerm);
			int rc = interpreter.execute(goal);

			// If it succeeded.
			if (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST)
			{
				// Get hold of the actual Terms which the variable terms point to
				Term value = answerTerm.dereference();
				if (value != null)
				{
					answer = value.toString();
				}
				if (rc == PrologCode.SUCCESS)
				{
					interpreter.stop(goal);
				}
			} else 
			{
				throw new NoAnswerException("[ERROR] reason() - Problem in tree architecture.");
			}
		}
		System.out.println("[DEBUG] reason() - Result : " + answer);
		return answer;
	}

	/* * * * * M A I N * * * * */
	
	// Test the ExpertSystem class with an example
	public static void main(String[] args)
	{
		ExpertSystem expertSystem = new ExpertSystem();
		HashMap<String, Question> questions = Question.getQuestions();
		expertSystem.printKnowledge();
		expertSystem.readKnowledge();
		expertSystem.printKnowledge();
	}
}
