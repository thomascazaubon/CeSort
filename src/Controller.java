

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	//The list of paths associated to the resources
	private HashMap<Resource, String> paths;
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
		paths = new HashMap<Resource,String>();
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
	
	//Used when clicking save on the results view, 
	//allows the user to specify a place where the project has to be saved (path).
	//Creates a specific file that contains the data of the ExpertSystem : knowledge.pl.
	public void saveResults(String path) {
		//To use the "cesort" extension
		String pathSave = path.split("\\.")[0] + ".cesort";
		expertSystem.writeKnowledge(pathSave);
	}
	
	//Used when clicking load on the welcome view, allows the user to load a previous project
	//Return the HashMap<stringQuestion, stringAnswer>
	public void loadResults(String path) {
		String extension = path.split("\\.")[1];
		if(extension.equals("cesort")) {
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
		} else {}
	}
	
	//Used when clicking on download on the results view
	//Downloads all resources in a zip archive
	public void downloadResources(String str) {
		//Correcting the input extension
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
		//String ret = "";
		//ImageIcon img = null;
		int num = 0;
		switch (r) {
		case Schedule:
			num = 3;
			//ret = "Schedule";
			//img = Model.getSchedulePreview(scenario);
			break;
		case Chart:
			num = 4;
			//ret = "Organization chart";
			//img = Model.getOrgChartPreview(scenario);
			break;
		case ReqList:
			num = 0;
			//ret = "Requirements list";
			//img = Model.getReqListPreview(scenario);
			break;
		case ReqModel:
			num = 1; 
			//ret = "Requirements model";
			//img = Model.getReqModelPreview(scenario);
			break;
		case ProcModel:
			num = 2;
			//ret = "Process models";
			//img = Model.getProcModelPreview(scenario);
			break;
		}
		
		resourcesView = new ResourcesView(this);
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("Requirements list");
		titles.add("Requirements model");
		titles.add("Process models");
		titles.add("Schedule");
		titles.add("Organizational chart");
		ArrayList<ImageIcon> previews = Model.getPreviews(scenario);
		resourcesView.startResourcesView(num, titles, previews);
		//resourcesView.startResourcesView(ret, img);
		resultView.closeResultView();
		currentView = View.Resources;
	}
	
	//Used by ResourcesView when the user asks to modify a file
	public void modifyResource(String res, String path) {
		Resource resource = null;
		File file = null;
		switch (res) {
		case "Schedule":
			resource = Resource.Schedule;
			break;
		case "Organization chart":
			resource = Resource.Chart;
			break;
		case "Requirements list":
			resource = Resource.ReqList;
			break;
		case "Requirements model":
			resource = Resource.ReqModel;
			break;
		case "Process models":
			resource = Resource.ProcModel;
			break;
		}
		//If the resource has already been modified
		if (paths.get(resource) != null) {
			path = paths.get(resource);
		//Else the resource is copied to the provided path
		} else {
			path = copyFile(resource, path);
			paths.put(resource, path);
		}
		//If the resource exists
		if (path != null) {
			file = new File(path);
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
						case "Process models":
							System.out.println("[ERROR] You need the TTool application to modify this file.");
							resourcesView.displayError("You need the TTool application to modify this file.");
							break;
						}
						System.out.println("[ERROR] "); e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("[ERROR] File does not exists !");
		}
	}
	
	//To return to the result view from the resource view
	public void backToResults() {
		resourcesView.closeResourcesView();
		resultView = new ResultView(this);
		resultView.startResultView(getStrings());
		currentView = View.Result;
	}
	
	//To get both the questions and answers in natural languages (without their associated keys)
	private HashMap<String,String> getStrings(){
        HashMap<String,String> keys = expertSystem.getKeyAnswers();
        //questions.get(keyQuestion) = Question
        HashMap<String,String> strings = new HashMap<String,String>();
        for (Map.Entry<String, String> keyEntry : keys.entrySet()) {
            String keyQuestion = keyEntry.getKey();
            String keyAnswer = keyEntry.getValue();
            strings.put(questions.get(keyQuestion).getTitle(),
            questions.get(keyQuestion).getAnswers().get(keyAnswer));
        }
        return strings;
    }

	//To get the key associated to an answer
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
	
	//Adds the specified resource to the provided zip
	private void copyFileInZip(Resource res, ZipOutputStream zos) {
		int len;
		byte[] buffer = new byte[1024];
		File f = null;
		if (paths.get(res) != null) {
			f = new File(paths.get(res));
		} else {
			f = Model.getResource(res, scenario);
		}
		try {
			//Creating a file input input stream from file
			FileInputStream in = new FileInputStream(f);
			//Adds an entry to the zip
			ZipEntry ze = new ZipEntry(f.getName());
			zos.putNextEntry(ze);
			//Copy the file into the entry
			while((len = in.read(buffer)) > 0) {
				zos.write(buffer,0,len);
			}
			//Close both the input stream and the zip entry to proceed to the next one
			in.close();
			zos.closeEntry();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	private String copyFile(Resource res, String path) {
		byte[] buffer = new byte[1024];
		int length;
		String correctedPath = null;
		File file = Model.getResource(res, scenario);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			//Replacing path with the correct extension
			correctedPath = path.split("\\.")[0] + "." + file.getPath().split("\\.")[1];
			FileOutputStream fos = new FileOutputStream(correctedPath);
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			fis.close();
			fos.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return correctedPath;
	}
	
	//Tells the resultView if a resource has already been modified
	public boolean isPathSet(String res) {
		Resource resource = null;
		boolean set = false;
		switch(res) {
		case "Schedule":
			resource = Resource.Schedule;
			break;
		case "Organization chart":
			resource = Resource.Chart;
			break;
		case "Requirements list":
			resource = Resource.ReqList;
			break;
		case "Requirements model":
			resource = Resource.ReqModel;
			break;
		case "Process models":
			resource = Resource.ProcModel;
			break;
		}
		if (paths.get(resource) != null) {
			set = true;
		}
		return set;
	}
}























