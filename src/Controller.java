

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import gnu.prolog.demo.mentalarithmetic.NoAnswerException;
import gnu.prolog.vm.PrologException;
import net.sf.mpxj.ProjectFile;

enum Language{FR,EN};
enum View{Welcome, Question, Result, Resources};
enum Resource{Schedule, Chart, ReqList, ReqModel, ProcModel};

public class Controller {
	int NUMBER_OF_RESOURCES = 5;

	/* * * * * A T T R I B U T E S * * * * */
		
	//The list of all available questions
	private HashMap<String, Question> questions;
	//All the answers provided until now, the key is the key of the question that they correspond to
	//private HashMap<String, String> answers;
	private String keyCurrentQuestion;
	private int numCurrentQuestion = 1;
	//Contains the representation of the resources to be displayed on resultView
	//To be changed when the format of the resources is settled
	private String[] resources;
	private ProjectFile schedule;
	private ExpertSystem expertSystem;
	private WelcomeView welcomeView;
	private QuestionView questionView;
	private ResultView resultView;
	private ResourcesView resourcesView;
	private Model model;
	private View currentView;
	
	private int scenario;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public Controller() {
		expertSystem = new ExpertSystem();
		welcomeView = new WelcomeView(this);
		model = new Model();
		//answers = new HashMap<String,String>();
		resources = new String[NUMBER_OF_RESOURCES];
		questions = Question.getQuestions();
		keyCurrentQuestion = null;
		welcomeView.startWelcomeView();
		currentView = View.Welcome;
	}
	
	/* * * * * M E T H O D S * * * * */
	
	//Used to start the questionnaire from the welcome view
	public void startQuestionnaire() {
		Question firstQuestion = null;
		try {
			//Gets the first question (root of the questionnaire)
			String result = expertSystem.reason();
			questionView = new QuestionView(this);
			numCurrentQuestion = 1;
			firstQuestion = questions.get(result);
			firstQuestion.setNum(numCurrentQuestion);
			keyCurrentQuestion = result;
			//Closes the welcome view and replaces it with the question view displaying the first question
			welcomeView.closeWelcomeView();
			questionView.startQuestionView(firstQuestion, null);
			currentView = View.Question;
		} catch (PrologException | NoAnswerException e) {
			e.printStackTrace();
		}
	}
	
	//Used by the questionnaire to get the next question and refresh the view to display it
	public void nextQuestion(String stringAnswer) {
		Question nextQuestion = null;
		try {
			String keyAnswer = getKeyWithString(stringAnswer);
			expertSystem.setKnowledge(keyCurrentQuestion, keyAnswer);
			String result = expertSystem.reason();
			//System.out.println(result);
			boolean scenarioFound = result.matches("\\d+");
			//When the questionnaire is finished
			if(scenarioFound) {
				scenario = Integer.parseInt(result);
				System.out.println("[DEBUG] scenario = " + scenario);
				//Retrieving the resources
				//Can be displayed using :
				//http://www.mpxj.org/apidocs/net/sf/mpxj/explorer/ProjectFilePanel.html
				//schedule = Model.getSchedule(scenario);
				//Displaying resultView
				questionView.closeQuestionView();
				resultView = new ResultView(this);
				resultView.startResultView(getStrings());
				currentView = View.Result;
			//Otherwise, we proceed to next question
			} else {
				questionView.closeQuestionView();
				nextQuestion = questions.get(result);
				numCurrentQuestion++;
				nextQuestion.setNum(numCurrentQuestion);
				keyCurrentQuestion = result;
				questionView = new QuestionView(this);
				questionView.startQuestionView(nextQuestion, getStrings());
			}
		} catch (PrologException | NoAnswerException e) {
			e.printStackTrace();
		}
	}
	
	//Used by the questionnaire to get the previous question and refresh the view to display it
	public void previousQuestion() {
		Question previousQuestion = null;
		try {
			questionView.closeQuestionView();
			questions.get(keyCurrentQuestion).setNum(0);
			numCurrentQuestion--;
			
			String keyPreviousQuestion = Question.getQuestionByNum(numCurrentQuestion);	
			expertSystem.setKnowledge(keyPreviousQuestion, "_");	
			String result = expertSystem.reason();
			
			keyCurrentQuestion = result;
			
			questionView = new QuestionView(this);
			previousQuestion = questions.get(result);
			
			questionView.startQuestionView(previousQuestion, getStrings());
			
		} catch (PrologException | NoAnswerException e) {
			e.printStackTrace();
		}
	}
	
	public void displayHome() {
		//Closes the current view
		switch (currentView) {
		case Question:
			questionView.closeQuestionView();
			break;
		case Result:
			resultView.closeResultView();
			break;
		case Resources:
			resourcesView.closeResourcesView();
			break;
		default:
			resultView.closeResultView();
			System.out.println("TEEEEST");
			break;
		}
		//Opens welcome view and resets questionnaire
		welcomeView.startWelcomeView();
		currentView = View.Welcome;
		expertSystem.resetKnowledge();
	}
	
	//Called upon clicking on an answer in the list of previous questions displayed during the questionnaire
	public Question editAnswer(String keyQuestion) {
		keyCurrentQuestion = keyQuestion;
		return questions.get(keyQuestion);
	}
	
	public void changeLanguage() {
		//TODO
	}
	
	//Used when clicking save on the results view, allows the user to specify
	//a place where the project has to be saved.
	//Creates a specific file that contains the data of the ExpertSystem
	public void saveResults() {
		//TODO
	}
	
	//Used when clicking load on the welcome view, allows the user to load a previous project
	//Return the HashMap<questionTitle, answerTitle>
	public HashMap<String, String> loadResults(String url) {
		//TODO : Copy the file at the given URL in knowledge.pl
		// answers = <questionTitle, answerTitle>
		HashMap<String,String> answers = new HashMap<String, String>();
		// keyAnswers = <keyQuestion, keyAnswer>
		HashMap<String, String> keyAnswers = expertSystem.getKeyAnswers();
        for (Map.Entry<String, String> mapEntry : keyAnswers.entrySet()) {
	        	String keyQuestion = mapEntry.getKey();
	        	String keyAnswer = mapEntry.getValue();
	        	Question question = questions.get(keyQuestion);
	        	String answer = question.getAnswers().get(keyAnswer);
	        	
	        	answers.put(question.getTitle(), answer);
        }
        return answers;
	}
	
	//Used when clicking on download on the results view
	//Downloads all resources in a zip archive
	public void downloadResources() {
		/*
		ProjectFilePanel panelSchedule = new ProjectFilePanel(schedule);
		panelSchedule.saveFile(schedule, "mpp");
		*/
	}
	
	//Used from the results view to redo the questionnaire
	public void redoQuestionnaire() {
		editAnswer("kindOfOrganisation");
	}
	
	//Used by result view when choosing to display one of the resources
	public String displayResource(Resource r) {
		String ret = "";
		ImageIcon img = null;
		switch (r) {
		case Schedule:
			ret = "Schedule";
			img = Model.getScheduleTemporary(scenario);
			break;
		case Chart:
			ret = "Organization chart";
			img = Model.getOrgChartTemporary(scenario);
			break;
		case ReqList:
			ret = "Requirements list";
			img = Model.getReqListTemporary(scenario);
			break;
		case ReqModel:
			ret = "Requirements model";
			img = Model.getReqModelTemporary(scenario);
			break;
		case ProcModel:
			ret = "Processes model";
			img = Model.getProcModelTemporary(scenario);
			break;
		}
		resourcesView = new ResourcesView(this);
		resourcesView.startResourcesView(ret, img);
		resultView.closeResultView();
		currentView = View.Resources;
		return ret;
	}
	
	public void backToResults() {
		resourcesView.closeResourcesView();
		
		resultView = new ResultView(this);
		resultView.startResultView(getStrings());
		currentView = View.Result;
	}
	
	private HashMap<String,String> getStrings(){
        HashMap<String,String> keys = expertSystem.getKeyAnswers();
        HashMap<String,String> strings = new HashMap<String,String>();
        for (Map.Entry<String, String> keyEntry : keys.entrySet()) {
            String keyQuestion = keyEntry.getKey();
            String keyAnswer = keyEntry.getValue();
            strings.put(questions.get(keyQuestion).getTitle(),
            questions.get(keyQuestion).getAnswers().get(keyAnswer));
        }
        return strings;
    }

	
	private String getKeyWithString(String answer) {
		String ret = "";
		Question current = questions.get(keyCurrentQuestion);
		for (Map.Entry<String, String> answerEntry : current.getAnswers().entrySet()) {
	        	if (answerEntry.getValue().equals(answer)) {
	        		ret = answerEntry.getKey();
	        	}
		}
		return ret;
	}
	
}























