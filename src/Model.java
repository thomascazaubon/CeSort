
import java.io.File;

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
		//System.out.println(Model.class.getResource("/..").getFile());
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
	 * Return an ImageIcon representing the corresponding process model
	 */
	public static ImageIcon getProcModelPreview(int scenario) {
		ImageIcon procModel = new ImageIcon("Resources/" + scenario + "/procModel.png");
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
	
	/* * * * * M E T H O D S - F I L E * * * * */
		
	/**
	 * Return a File representing the corresponding requirements list
	 */
	public static File getReqList(int scenario) {
		File reqList = null;
		reqList = new File("Resources/" + scenario + "/reqList.xlsx");
		return reqList;
	}
	
	/**
	 * Return a File representing the corresponding requirements model
	 */
	public static File getReqModel(int scenario) {
		File reqModel = null;
		reqModel = new File("Resources/" + scenario + "/reqModel.xml");
		return reqModel;
	}
	
	/**
	 * Return a File representing the corresponding process model
	 */
	public static File getProcModel(int scenario) {
		File procModel = null;
		procModel = new File("Resources/" + scenario + "/procModel.xml");
		return procModel;
	}
	
	/**
	 * Return a File representing the corresponding schedule
	 */
	public static File getSchedule(int scenario) {
		File schedule = null;
		schedule = new File("Resources/" + scenario + "/schedule.xlsx");
		return schedule;
	}
	
	/**
	 * Return a File representing the corresponding schedule
	 */
	public static File getOrgChart(int scenario) {
		File orgChart = null;
		orgChart = new File("Resources/" + scenario + "/orgChart.pptx");
		return orgChart;
	}
	
}
