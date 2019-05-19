import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;


public class ResourcesView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	private JLabel error;
	
	private ArrayList<ImageIcon> listImageIcon; 
	private ArrayList<String> listRes;
	private int currentRes;
	
	private JPanel imgPanel;
	//private BoxLayout imgLayout;
	private JScrollPane imgScroll;
	
	private Controller controller;
	private JLabel imageRes;
	private JButton btnModels;
	private JButton btnSchedule;
	private JButton btnOrgChart;
	private JButton btnProcess;
	private JButton btnRequirementsModels;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public ResourcesView(Controller c) {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null,
			            "Do you want to Exit ?", "Confirm exit",
			            JOptionPane.YES_NO_OPTION);
			        if (result == JOptionPane.YES_OPTION)
			        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        else if (result == JOptionPane.NO_OPTION)
			        	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
		
		this.controller = c;
		
		panel = new JPanel();
		layout = new SpringLayout();
		
		this.setSize(600,600);
		//setUpFrame();

	}
	private void setUpFrame() {
		this.getContentPane().removeAll();
		/* General things to do. */
		setContentPane(panel);
		// Set the pan with its layout
		panel.setLayout(layout);
		panel.removeAll();
		
		String res = listRes.get(currentRes);
		ImageIcon imageIcon = listImageIcon.get(currentRes);

		
		//Return button 
		JButton btnResults = new JButton("Results");
		layout.putConstraint(SpringLayout.NORTH, btnResults, -59, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, btnResults, 65, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnResults, -29, SpringLayout.SOUTH, panel);
		btnResults.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnResults.setForeground(new Color(0, 139, 139));
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Back to the results menu.
				controller.backToResults();
			}
		});
		btnResults.setBackground(new Color(224, 255, 255));
		panel.add(btnResults);
				
		
		JLabel lblResource = new JLabel(res);
		lblResource.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		layout.putConstraint(SpringLayout.NORTH, lblResource, 26, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, lblResource, 27, SpringLayout.WEST, panel);
		panel.add(lblResource);
		
		imgPanel = new JPanel();
		imgScroll = new JScrollPane(imgPanel);
		layout.putConstraint(SpringLayout.NORTH, imgScroll, 63, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, imgScroll, -26, SpringLayout.NORTH, btnResults);
		layout.putConstraint(SpringLayout.WEST, imgScroll, 189, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, imgScroll, -24, SpringLayout.EAST, panel);
		//imgLayout = new BoxLayout(imgScroll, BoxLayout.Y_AXIS);
		imgScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		imgScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//imgPanel.setLayout(imgLayout);
		panel.add(imgScroll);
		
		imageRes = new JLabel();
		//layout.putConstraint(SpringLayout.NORTH, imageRes, 20, SpringLayout.NORTH, lblResource);
		//layout.putConstraint(SpringLayout.WEST, imageRes, 25, SpringLayout.WEST, panel);
		//layout.putConstraint(SpringLayout.SOUTH, imageRes, -30, SpringLayout.NORTH, btnResults);
		//layout.putConstraint(SpringLayout.EAST, imageRes, -33, SpringLayout.EAST, panel);
		//Image imgScaled = imageIcon.getImage().getScaledInstance(540, 350, Image.);
		//imageIcon = new ImageIcon(imgScaled);
		imageRes.setIcon(imageIcon);
		imageRes.setOpaque(false);
	    imgPanel.add(imageRes);
	        
	    //Download
	    JButton btnNewButton = new JButton("Open to modify");
	    layout.putConstraint(SpringLayout.WEST, btnNewButton, 319, SpringLayout.WEST, panel);
	    layout.putConstraint(SpringLayout.EAST, btnResults, -110, SpringLayout.WEST, btnNewButton);
	    layout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, btnResults);
	    layout.putConstraint(SpringLayout.SOUTH, btnNewButton, -29, SpringLayout.SOUTH, panel);
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser = new JFileChooser();
	    		FileNameExtensionFilter ff = null;

	    		if(!controller.isPathSet(res)) {
		    		switch (res) {
					case "Schedule":
						chooser.setSelectedFile(new File("schedule.xlsx"));
						ff = new FileNameExtensionFilter("Excel", "xlsx");
						break;
					case "Organization chart":
						chooser.setSelectedFile(new File("orgChart.pptx"));
						ff = new FileNameExtensionFilter("PowerPoint", "pptx");
						break;
					case "Requirements list":
						chooser.setSelectedFile(new File("reqList.xlsx"));
						ff = new FileNameExtensionFilter("Excel", "xlsx");
						break;
					case "Requirements model":
						chooser.setSelectedFile(new File("reqModel.xml"));
						ff = new FileNameExtensionFilter("XML", "xml");
						break;
					case "Process models":
						chooser.setSelectedFile(new File("procModel.xml"));
						ff = new FileNameExtensionFilter("XML", "xml");
						break;
					}
		    		
					chooser.addChoosableFileFilter(ff);
					chooser.setFileFilter(ff);
					chooser.setDialogTitle("Open to mdify");
					chooser.showSaveDialog(panel);
					
					if(chooser.getSelectedFile() != null) {
						controller.modifyResource(res, chooser.getSelectedFile().getAbsolutePath());
					}
	    		} else {
	    			controller.modifyResource(res, null);
	    		}
				}});
	    panel.add(btnNewButton);
	    
	    // Tabs
	    
	    JButton btnReq = new JButton("Requirements");
	    btnReq.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 0;
	    		setUpFrame();
	    	}
	    });
	    layout.putConstraint(SpringLayout.NORTH, btnReq, 60, SpringLayout.SOUTH, lblResource);
	    layout.putConstraint(SpringLayout.WEST, btnReq, 26, SpringLayout.WEST, panel);
	    panel.add(btnReq);
	    
	    btnModels = new JButton("Models");
	    layout.putConstraint(SpringLayout.NORTH, btnModels, 6, SpringLayout.SOUTH, btnReq);
	    layout.putConstraint(SpringLayout.WEST, btnModels, 0, SpringLayout.WEST, lblResource);
	    btnModels.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 1;
	    		setUpFrame();
	    	}
	    });
	    panel.add(btnModels);
	    
	    btnSchedule = new JButton("Schedule");
	    layout.putConstraint(SpringLayout.NORTH, btnSchedule, 6, SpringLayout.SOUTH, btnModels);
	    layout.putConstraint(SpringLayout.WEST, btnSchedule, 0, SpringLayout.WEST, lblResource);
	    btnSchedule.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 3;
	    		setUpFrame();
	    	}
	    });
	    panel.add(btnSchedule);
	    
	    btnOrgChart = new JButton("Org. chart");
	    layout.putConstraint(SpringLayout.NORTH, btnOrgChart, 6, SpringLayout.SOUTH, btnSchedule);
	    layout.putConstraint(SpringLayout.WEST, btnOrgChart, 0, SpringLayout.WEST, lblResource);
	    btnOrgChart.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 4;
	    		setUpFrame();
	    	}
	    });
	    panel.add(btnOrgChart);
	    
	    
	    if(currentRes == 1 || currentRes == 2) {		    
		    btnRequirementsModels = new JButton("Process models");
		    btnRequirementsModels.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		currentRes = 2;
		    		setUpFrame();
		    	}
		    });
		    layout.putConstraint(SpringLayout.NORTH, btnRequirementsModels, 0, SpringLayout.NORTH, lblResource);
		    layout.putConstraint(SpringLayout.EAST, btnRequirementsModels, 0, SpringLayout.EAST, imgScroll);
		    panel.add(btnRequirementsModels);
		    
    			btnProcess = new JButton("Requirements models");
    			layout.putConstraint(SpringLayout.NORTH, btnProcess, 0, SpringLayout.NORTH, lblResource);
    			layout.putConstraint(SpringLayout.EAST, btnProcess, -30, SpringLayout.WEST, btnRequirementsModels);
    			btnProcess.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				currentRes = 1;
    				setUpFrame();
    			}
    			});
    			panel.add(btnProcess);
	    }
	    
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void displayError(String txtError) {
		System.out.println(txtError);
		error.setText(txtError);
	}
	
	// ReqList, ReqModel, ProcModel, Schedule, OrgChart
	public void startResourcesView(int num, ArrayList<String> r, ArrayList<ImageIcon> img) {
		this.currentRes = num;
		this.listImageIcon = img;
		this.listRes = r;
		setUpFrame();
		this.setVisible(true);
	}
	
	public void closeResourcesView() {
		// Other things to be done ?
		this.setVisible(false);
	}
}