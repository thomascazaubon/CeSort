

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
//import java.awt.Image;

public class ResourcesView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	private JLabel error;
	
	private String res;
	private ImageIcon imageIcon; 
	private JPanel imgPanel;
	//private BoxLayout imgLayout;
	private JScrollPane imgScroll;

	private Controller controller;
	private JLabel imageRes;
	
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
		// setUpFrame();

	}
	private void setUpFrame() {
		/* General things to do. */
		setContentPane(panel);
		// Set the pan with its layout
		panel.setLayout(layout);
		
		JButton btnResults = new JButton("Results");
		layout.putConstraint(SpringLayout.WEST, btnResults, 78, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, btnResults, -402, SpringLayout.EAST, panel);
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
		lblResource.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		layout.putConstraint(SpringLayout.NORTH, lblResource, 26, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, lblResource, 27, SpringLayout.WEST, panel);
		panel.add(lblResource);

		
		imgPanel = new JPanel();
		imgScroll = new JScrollPane(imgPanel);
		layout.putConstraint(SpringLayout.NORTH, imgScroll, 20, SpringLayout.NORTH, lblResource);
		layout.putConstraint(SpringLayout.WEST, imgScroll, 25, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, imgScroll, -30, SpringLayout.NORTH, btnResults);
		layout.putConstraint(SpringLayout.EAST, imgScroll, -33, SpringLayout.EAST, panel);
		//imgLayout = new BoxLayout(imgScroll, BoxLayout.Y_AXIS);
		imgScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		imgScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//imgPanel.setLayout(imgLayout);
		panel.add(imgScroll);
		
		imageRes = new JLabel();
		layout.putConstraint(SpringLayout.SOUTH, btnResults, -73, SpringLayout.SOUTH, panel);
		//layout.putConstraint(SpringLayout.NORTH, imageRes, 20, SpringLayout.NORTH, lblResource);
		//layout.putConstraint(SpringLayout.WEST, imageRes, 25, SpringLayout.WEST, panel);
		//layout.putConstraint(SpringLayout.SOUTH, imageRes, -30, SpringLayout.NORTH, btnResults);
		//layout.putConstraint(SpringLayout.EAST, imageRes, -33, SpringLayout.EAST, panel);
		//Image imgScaled = imageIcon.getImage().getScaledInstance(540, 350, Image.);
		//imageIcon = new ImageIcon(imgScaled);
		imageRes.setIcon(imageIcon);
		imageRes.setOpaque(false);
	    imgPanel.add(imageRes);
	    
	    JLabel error = new JLabel("");
	    layout.putConstraint(SpringLayout.NORTH, error, 16, SpringLayout.SOUTH, btnResults);
	    layout.putConstraint(SpringLayout.WEST, error, 57, SpringLayout.WEST, panel);
	    layout.putConstraint(SpringLayout.SOUTH, error, -10, SpringLayout.SOUTH, panel);
	    layout.putConstraint(SpringLayout.EAST, error, 536, SpringLayout.WEST, panel);
	    panel.add(error);
	    
	    JButton btnNewButton = new JButton("Open to modify");
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
					case "Processes model":
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
	    layout.putConstraint(SpringLayout.NORTH, btnNewButton, 1, SpringLayout.NORTH, btnResults);
	    layout.putConstraint(SpringLayout.WEST, btnNewButton, 124, SpringLayout.EAST, btnResults);
	    layout.putConstraint(SpringLayout.SOUTH, btnNewButton, -73, SpringLayout.SOUTH, panel);
	    layout.putConstraint(SpringLayout.EAST, btnNewButton, -151, SpringLayout.EAST, panel);
	    panel.add(btnNewButton);
	    
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void displayError(String txtError) {
		System.out.println(txtError);
		error.setText(txtError);
	}
	
	
	public void startResourcesView(String r, ImageIcon img) {
		this.imageIcon = img;
		this.res = r;
		setUpFrame();
		this.setVisible(true);
	}
	
	public void closeResourcesView() {
		// Other things to be done ?
		this.setVisible(false);
	}
}