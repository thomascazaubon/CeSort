
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Model {
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public Model() {
		
	}
	
	/* * * * * M E T H O D S - P R E V I E W * * * * */
		
	/**
	 * Return an ImageIcon representing the corresponding requirements list
	 */
	public static ImageIcon getReqListTemporary(int scenario) {
		ImageIcon reqList = null;
		try {
			reqList = new ImageIcon(ImageIO.read(new FileInputStream("Resources/" + scenario + "/reqList.png")));
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
		return reqList;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding requirements model
	 */
	public static ImageIcon getReqModelTemporary(int scenario) {
		ImageIcon reqModel = null;
		try {
			reqModel = new ImageIcon(ImageIO.read(new FileInputStream("Resources/" + scenario + "/reqModel.png")));
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
		return reqModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding process model
	 */
	public static ImageIcon getProcModelTemporary(int scenario) {
		ImageIcon procModel = null;
		try {
			procModel = new ImageIcon(ImageIO.read(new FileInputStream("Resources/" + scenario + "/procModel.png")));
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
		return procModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getScheduleTemporary(int scenario) {
		ImageIcon schedule = null;
		FileInputStream fileS;
		try {
			fileS = new FileInputStream("Resources/" + scenario + "/schedule.png");
			schedule = new ImageIcon(ImageIO.read(fileS));
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
		return schedule;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getOrgChartTemporary(int scenario) {
		ImageIcon orgChart = null;
		try {
			orgChart = new ImageIcon(ImageIO.read(new FileInputStream("Resources/" + scenario + "/orgChart.png")));
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[ERROR] "); e.printStackTrace();
		}
		return orgChart;
	}
	
	/* * * * * M E T H O D S - F I L E * * * * */
	
	/**
	 * Return a File representing the corresponding requirements list
	 */
	public static File getReqList(int scenario) {
		File reqList = null;
		reqList = new File("Resources/" + scenario + "/reqList.xslx");
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
		schedule = new File("Resources/" + scenario + "/schedule.mpp");
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
