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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private JButton btnOpenToModify;
	private JLabel imageRes;
	private JButton btnProc;
	private JButton btnSchedule;
	private JButton btnOrgChart;
	private JButton btnList;
	private JButton btnModel;
	private JButton btnStrucModel;
	private JButton btnBehavModel;
	
	private ImageIcon logoCeSort;
	
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
		this.setTitle("CeSort");
		FileInputStream fileR;
		try {
			fileR = new FileInputStream("Images/requirements.png");
			logoCeSort = new ImageIcon(ImageIO.read(fileR));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.setIconImage(logoCeSort.getImage());
		
		panel = new JPanel();
		layout = new SpringLayout();
		btnOpenToModify = new JButton();
		
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
				
		String title = null;
		switch(res) {
		case "List":
			title = "Requirements";
			break;
		case "Model":
			title = "Requirements";
			break;
		case "Structural model":
			title = "Processes";
			break;
		case "Behavioural model":
			title = "Processes";
			break;
		case "Schedule":
			title = "Schedule";
			break;
		case "Organizational chart":
			title = "Organizational chart";
			break;
		}
		JLabel lblResource = new JLabel(title);
		
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
	    btnOpenToModify.setText("Open to modify");
	    layout.putConstraint(SpringLayout.WEST, btnOpenToModify, 319, SpringLayout.WEST, panel);
	    layout.putConstraint(SpringLayout.EAST, btnResults, -110, SpringLayout.WEST, btnOpenToModify);
	    layout.putConstraint(SpringLayout.NORTH, btnOpenToModify, 0, SpringLayout.NORTH, btnResults);
	    layout.putConstraint(SpringLayout.SOUTH, btnOpenToModify, -29, SpringLayout.SOUTH, panel);
	    btnOpenToModify.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter ff = null;
	    		if(!controller.isPathSet(res)) {
		    		switch (res) {
					case "Schedule":
						chooser.setSelectedFile(new File("schedule.xlsx"));
						ff = new FileNameExtensionFilter("Excel", "xlsx");
						break;
					case "Organizational chart":
						chooser.setSelectedFile(new File("orgChart.pptx"));
						ff = new FileNameExtensionFilter("PowerPoint", "pptx");
						break;
					case "List":
						chooser.setSelectedFile(new File("reqList.xlsx"));
						ff = new FileNameExtensionFilter("Excel", "xlsx");
						break;
					case "Model":
						chooser.setSelectedFile(new File("reqModel.xml"));
						ff = new FileNameExtensionFilter("XML", "xml");
						break;
					case "Structural model":
						chooser.setSelectedFile(new File("strucModel.xml"));
						ff = new FileNameExtensionFilter("XML", "xml");
						break;
					case "Behavioural model":
						chooser.setSelectedFile(new File("behavModel.xml"));
						ff = new FileNameExtensionFilter("XML", "xml");
						break;
					}
		    		
					chooser.addChoosableFileFilter(ff);
					chooser.setFileFilter(ff);
					chooser.setDialogTitle(btnOpenToModify.getText());
					chooser.showSaveDialog(panel);
					
					if(chooser.getSelectedFile() != null) {
						controller.modifyResource(res, chooser.getSelectedFile().getAbsolutePath());
					}
	    		} else {
	    			controller.modifyResource(res, null);
	    		}
				}});
	    panel.add(btnOpenToModify);
	    
	    // Tabs
	    
	    JButton btnReq = new JButton("Requirements");
    		btnReq.setOpaque(true);
	    btnReq.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 0;
	    		setUpFrame();
		    	btnReq.setBackground(new Color(153, 204, 255));
	    	}
	    });
	    layout.putConstraint(SpringLayout.NORTH, btnReq, 60, SpringLayout.SOUTH, lblResource);
	    layout.putConstraint(SpringLayout.WEST, btnReq, 26, SpringLayout.WEST, panel);
	    if (currentRes == 0 || currentRes == 1) {
	    	btnReq.setBackground(new Color(153, 204, 255));
	    }
	    panel.add(btnReq);
	    
	    btnProc = new JButton("Processes");
		btnProc.setOpaque(true);
	    layout.putConstraint(SpringLayout.NORTH, btnProc, 6, SpringLayout.SOUTH, btnReq);
	    layout.putConstraint(SpringLayout.WEST, btnProc, 0, SpringLayout.WEST, lblResource);
	    btnProc.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 2;
	    		setUpFrame();
	    	}
	    });
	    if (currentRes == 2 || currentRes == 3) {
	    	btnProc.setBackground(new Color(153, 204, 255));
	    }
	    panel.add(btnProc);
	    
	    btnSchedule = new JButton("Schedule");
		btnSchedule.setOpaque(true);
	    layout.putConstraint(SpringLayout.NORTH, btnSchedule, 6, SpringLayout.SOUTH, btnProc);
	    layout.putConstraint(SpringLayout.WEST, btnSchedule, 0, SpringLayout.WEST, lblResource);
	    btnSchedule.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 4;
	    		setUpFrame();
	    	}
	    });
	    if (currentRes == 4) {
	    	btnSchedule.setBackground(new Color(153, 204, 255));
	    }
	    panel.add(btnSchedule);
	    
	    btnOrgChart = new JButton("Org. chart");
		btnOrgChart.setOpaque(true);
	    layout.putConstraint(SpringLayout.NORTH, btnOrgChart, 6, SpringLayout.SOUTH, btnSchedule);
	    layout.putConstraint(SpringLayout.WEST, btnOrgChart, 0, SpringLayout.WEST, lblResource);
	    btnOrgChart.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentRes = 5;
	    		setUpFrame();
	    	}
	    });
	    if (currentRes == 5) {
	    	btnOrgChart.setOpaque(true);
	    	btnOrgChart.setBackground(new Color(153, 204, 255));
	    }
	    panel.add(btnOrgChart);
	    
	    
	    if(currentRes == 0 || currentRes == 1) {		    
		    btnList = new JButton("List");
    			btnList.setOpaque(true);
		    btnList.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		currentRes = 0;
		    		setUpFrame();
		    	}
		    });
		    layout.putConstraint(SpringLayout.NORTH, btnList, 0, SpringLayout.NORTH, lblResource);
		    layout.putConstraint(SpringLayout.EAST, btnList, 0, SpringLayout.EAST, imgScroll);
		    if (currentRes == 0) {
		    	btnList.setBackground(new Color(255, 204, 102));
		    }
		    panel.add(btnList);
		    
		    btnModel = new JButton("Model");    		
		    btnModel.setOpaque(true);
			layout.putConstraint(SpringLayout.NORTH, btnModel, 0, SpringLayout.NORTH, lblResource);
			layout.putConstraint(SpringLayout.EAST, btnModel, -30, SpringLayout.WEST, btnList);
			btnModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentRes = 1;
				setUpFrame();
			    btnOpenToModify.setText("Save");
			}
			});
			if (currentRes == 1) {
				btnModel.setBackground(new Color(255, 204, 102));
		    }
			panel.add(btnModel);
	    }
	    
	    if(currentRes == 2 || currentRes == 3) {		    
		    btnStrucModel = new JButton("Structural model");
    			btnStrucModel.setOpaque(true);
		    btnStrucModel.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		currentRes = 2;
		    		setUpFrame();
				btnOpenToModify.setText("Save");
		    	}
		    });
		    layout.putConstraint(SpringLayout.NORTH, btnStrucModel, 0, SpringLayout.NORTH, lblResource);
		    layout.putConstraint(SpringLayout.EAST, btnStrucModel, 0, SpringLayout.EAST, imgScroll);
		    if (currentRes == 2) {
		    	btnStrucModel.setBackground(new Color(255, 204, 102));
		    }
		    panel.add(btnStrucModel);
		    
			btnBehavModel = new JButton("Behavioural model");
			btnBehavModel.setOpaque(true);
			layout.putConstraint(SpringLayout.NORTH, btnBehavModel, 0, SpringLayout.NORTH, lblResource);
			layout.putConstraint(SpringLayout.EAST, btnBehavModel, -30, SpringLayout.WEST, btnStrucModel);
			
			btnBehavModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentRes = 3;
				setUpFrame();
			    btnOpenToModify.setText("Save");
			}
			});
			if (currentRes == 3) {
				btnBehavModel.setBackground(new Color(255, 204, 102));
		    }
			panel.add(btnBehavModel);
	    }
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void displayError(String txtError) {
		System.out.println(txtError);
		error.setText(txtError);
	}
	
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