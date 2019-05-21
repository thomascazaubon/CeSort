
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Model {
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public Model() {
		
	}
	
	/* * * * * M E T H O D S - P R E V I E W * * * * */
		
	/**
	 * Return an ImageIcon representing the corresponding requirements list
	 */
	public static ImageIcon getReqListPreview(int scenario) {
		ImageIcon reqList = null;
		reqList = new ImageIcon("Resources/" + scenario + "/reqList.png");
		return reqList;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding requirements model
	 */
	public static ImageIcon getReqModelPreview(int scenario) {
		ImageIcon reqModel = null;
		reqModel = new ImageIcon("Resources/" + scenario + "/reqModel.png");
		return reqModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding structural model
	 */
	public static ImageIcon getStrucModelPreview(int scenario) {
		ImageIcon procModel = new ImageIcon("Resources/" + scenario + "/strucModel.png");
		return procModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding behavioural model
	 */
	public static ImageIcon getBehavModelPreview(int scenario) {
		ImageIcon procModel = new ImageIcon("Resources/" + scenario + "/behavModel.png");
		return procModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getSchedulePreview(int scenario) {
		ImageIcon schedule = null;
		schedule = new ImageIcon("Resources/" + scenario + "/schedule.png");
		return schedule;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getOrgChartPreview(int scenario) {
		ImageIcon orgChart = null;
		orgChart = new ImageIcon("Resources/" + scenario + "/orgChart.png");
		return orgChart;
	}
	
	/**
	 * Return all ImageIcon corresponding to the scenario
	 */
	public static ArrayList<ImageIcon> getPreviews(int scenario) {
		ArrayList<ImageIcon> previews = new ArrayList<ImageIcon>(5);
		previews.add(getReqListPreview(scenario));
		previews.add(getReqModelPreview(scenario));
		previews.add(getStrucModelPreview(scenario));
		previews.add(getBehavModelPreview(scenario));
		previews.add(getSchedulePreview(scenario));
		previews.add(getOrgChartPreview(scenario));
		return previews;
	}
	
	/* * * * * M E T H O D S - F I L E * * * * */
	
	public static File getResource(Resource res, int scenario) {
		File file = null;
		switch(res) {
		case List:
			file = new File("Resources/" + scenario + "/reqList.xlsx");
			break;
		case Model:
			file = new File("Resources/" + scenario + "/reqModel.xml");
			break;
		case StrucModel:
			file = new File("Resources/" + scenario + "/strucModel.xml");
			break;
		case BehavModel:
			file = new File("Resources/" + scenario + "/behavModel.xml");
			break;
		case Schedule:
			file = new File("Resources/" + scenario + "/schedule.xlsx");
			break;
		case OrgChart:
			file = new File("Resources/" + scenario + "/orgChart.pptx");
			break;
		}
		return file;
	}
}
