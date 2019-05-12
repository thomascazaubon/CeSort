

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;

import gnu.prolog.demo.mentalarithmetic.NoAnswerException;
import gnu.prolog.vm.PrologException;

enum Language{FR,EN};
enum View{Welcome, Question, Result, Resources};
enum Resource{Schedule, Chart, ReqList, ReqModel, ProcModel};

public class Controller {
	int NUMBER_OF_RESOURCES = 5;

	/* * * * * A T T R I B U T E S * * * * */
		
	//The list of all available questions : <keyQuestion, Question>
	private HashMap<String, Question> questions;
	//All the answers provided until now, the key is the key of the question that they correspond to
	private String keyCurrentQuestion;
	private int numCurrentQuestion = 1;
	private ExpertSystem expertSystem;
	private WelcomeView welcomeView;
	private QuestionView questionView;
	private ResultView resultView;
	private ResourcesView resourcesView;
	private View currentView;
	
	private int scenario;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public Controller() {
		expertSystem = new ExpertSystem();
		welcomeView = new WelcomeView(this);
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
			System.out.println("[DEBUG] Question : " + keyCurrentQuestion + "     Answer : " + keyAnswer + " = " + stringAnswer);
			expertSystem.setKnowledge(keyCurrentQuestion, keyAnswer);
			String result = expertSystem.reason();
			boolean scenarioFound = result.matches("\\d+");
			questionView.closeQuestionView();
			//When the questionnaire is finished
			if(scenarioFound) {
				scenario = Integer.parseInt(result);
				System.out.println("[DEBUG] scenario = " + scenario);
				//Displaying resultView
				resultView = new ResultView(this);
				resultView.startResultView(getStrings());
				//Update the current view
				currentView = View.Result;
			//Otherwise, we proceed to the next question
			} else {
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
	//[optional]
	public Question editAnswer(String keyQuestion) {
		keyCurrentQuestion = keyQuestion;
		return questions.get(keyQuestion);
	}
	
	//[optional]
	public void changeLanguage(Language L) {
		//TODO
	}
	
	//Used when clicking save on the results view, 
	//allows the user to specify a place where the project has to be saved (path).
	//Creates a specific file that contains the data of the ExpertSystem : knowledge.pl.
	public void saveResults(String path) {
		//To use the "cesort" extension
		String pathSave = path.split("//.")[0] + ".cesort";
		expertSystem.writeKnowledge(pathSave);
	}
	
	//Used when clicking load on the welcome view, allows the user to load a previous project
	//Return the HashMap<stringQuestion, stringAnswer>
	public void loadResults(String path) {
		expertSystem.readKnowledge(path);
		// strings = <stringQuestion, stringAnswer>
		HashMap<String,String> strings = new HashMap<String, String>();
		// keys = <keyQuestion, keyAnswer>
		HashMap<String, String> keys = expertSystem.getKeyAnswers();
        for (Map.Entry<String, String> mapEntry : keys.entrySet()) {
	        	String keyQuestion = mapEntry.getKey();
	        	String keyAnswer = mapEntry.getValue();
	        	Question question = questions.get(keyQuestion);
	        	String answer = question.getAnswers().get(keyAnswer);
	        	strings.put(question.getTitle(), answer);
        }
        try {
			scenario = Integer.parseInt(expertSystem.reason());
		} catch (NumberFormatException | PrologException | NoAnswerException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
        welcomeView.closeWelcomeView();
        resultView = new ResultView(this);
        resultView.startResultView(strings);
	}
	
	//Used when clicking on download on the results view
	//Downloads all resources in a zip archive
	public void downloadResources(String str) {
		String path = str.split("\\.")[0] + ".zip";
		try {
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(path));
			for (Resource r : Resource.values()) {
				copyFileInZip(r,zip);
			}
			zip.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//Used by result view when choosing to display one of the resources
	public void displayResource(Resource r) {
		String ret = "";
		ImageIcon img = null;
		switch (r) {
		case Schedule:
			ret = "Schedule";
			img = Model.getSchedulePreview(scenario);
			break;
		case Chart:
			ret = "Organization chart";
			img = Model.getOrgChartPreview(scenario);
			break;
		case ReqList:
			ret = "Requirements list";
			img = Model.getReqListPreview(scenario);
			break;
		case ReqModel:
			ret = "Requirements model";
			img = Model.getReqModelPreview(scenario);
			break;
		case ProcModel:
			ret = "Processes model";
			img = Model.getProcModelPreview(scenario);
			break;
		}
		resourcesView = new ResourcesView(this);
		resourcesView.startResourcesView(ret, img);
		resultView.closeResultView();
		currentView = View.Resources;
	}
	
	//Used by ResourcesView when the user asks to modify a file
	public void modifyResource(String res) {
		File file = null;
		switch (res) {
		case "Schedule":
			file = Model.getSchedule(scenario);
			break;
		case "Organization chart":
			file = Model.getOrgChart(scenario);
			break;
		case "Requirements list":
			file = Model.getReqList(scenario);
			break;
		case "Requirements model":
			file = Model.getReqModel(scenario);
			break;
		case "Processes model":
			file = Model.getProcModel(scenario);
			break;
		}
		
		if (file == null) {
			System.out.println("[ERROR] The file to modify doesn't exist.");
			resourcesView.displayError("The file to modify doesn't exist.");
		}
		
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
			System.out.println("[DEBUG] Desktop is supported.");
			desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				System.out.println("[DEBUG] Desktop.Action.open is supported.");
				try {
					desktop.open(file);
				} catch (IOException e) {
					System.out.println("[ERROR] "); e.printStackTrace();
				} catch (IllegalArgumentException e) {
					switch (res) {
					case "Schedule":
						System.out.println("[ERROR] You need an application like MSProject or GanttProject to modify this file.");
						resourcesView.displayError("You need an application like MSProject or GanttProject to modify this file.");
						break;
					case "Organization chart":
						System.out.println("[ERROR] You need an application like PowerPoint or LibreOffice Impress to modify this file.");
						resourcesView.displayError("You need an application like PowerPoint or LibreOffice Impress to modify this file.");
						break;
					case "Requirements list":
						System.out.println("[ERROR] You need an application like Excel or LibreOffice Calc to modify this file.");
						resourcesView.displayError("You need an application like Excel or LibreOffice Calc to modify this file.");
						break;
					case "Requirements model":
						System.out.println("[ERROR] You need the TTool application to modify this file.");
						resourcesView.displayError("You need the TTool application to modify this file.");
						break;
					case "Processes model":
						System.out.println("[ERROR] You need the TTool application to modify this file.");
						resourcesView.displayError("You need the TTool application to modify this file.");
						break;
					}
					System.out.println("[ERROR] "); e.printStackTrace();
				}
			}
		}
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
	
	private void copyFileInZip(Resource res, ZipOutputStream zos) {
		int len;
		byte[] buffer = new byte[1024];
		File f = null;
		switch(res) {
		case Schedule:
			f = Model.getSchedule(scenario);
			break;
		case Chart:
			f = Model.getOrgChart(scenario);
			break;
		case ReqList:
			f = Model.getReqList(scenario);
			break;
		case ReqModel:
			f = Model.getReqModel(scenario);
			break;
		case ProcModel:
			f = Model.getProcModel(scenario);
			break;
		}
		try {
			FileInputStream in = new FileInputStream(f);
			ZipEntry ze = new ZipEntry(f.getName());
			zos.putNextEntry(ze);
			while((len = in.read(buffer)) > 0) {
				zos.write(buffer,0,len);
			}
			in.close();
			zos.closeEntry();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
}























