
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.reader.UniversalProjectReader;

public class Model {
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public Model() {
		
	}
	
	/* * * * * M E T H O D S - T E M P O R A R Y * * * * */
	
	//TODO: Delete when it will be interfaced with Excel, PPT, MSProject & TTool.
	
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
	
	/* * * * * M E T H O D S * * * * */
	
	/**
	 * Return a ProjectFile corresponding to the scenario
	 */
	public static ProjectFile getSchedule(int scenario) {
		UniversalProjectReader reader = new UniversalProjectReader();
		ProjectFile schedule = null;
		try {
			schedule = reader.read("Resources/" + scenario + "/schedule.mpp");
		} catch (MPXJException e) {
			e.printStackTrace();
		}
		return schedule;
	}
	
}
