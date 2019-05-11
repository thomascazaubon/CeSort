
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
		//System.out.println(Model.class.getResource("/..").getFile());
		reqList = new ImageIcon("Resources/" + scenario + "/reqList.png");
		return reqList;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding requirements model
	 */
	public static ImageIcon getReqModelTemporary(int scenario) {
		ImageIcon reqModel = null;
		reqModel = new ImageIcon("Resources/" + scenario + "/reqModel.png");
		return reqModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding process model
	 */
	public static ImageIcon getProcModelTemporary(int scenario) {
		ImageIcon procModel = new ImageIcon("Resources/" + scenario + "/procModel.png");
		return procModel;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getScheduleTemporary(int scenario) {
		ImageIcon schedule = null;
		schedule = new ImageIcon("Resources/" + scenario + "/schedule.png");
		return schedule;
	}
	
	/**
	 * Return an ImageIcon representing the corresponding schedule
	 */
	public static ImageIcon getOrgChartTemporary(int scenario) {
		ImageIcon orgChart = null;
		orgChart = new ImageIcon("Resources/" + scenario + "/orgChart.png");
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
