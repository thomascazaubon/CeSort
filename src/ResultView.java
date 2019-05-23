

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ResultView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	
	private Controller controller;
	private HashMap<String, String> listQA;
	
	private JPanel previousQuestionsPanel;
	private BoxLayout previousQuestionsLayout;
	private JScrollPane previousQuestionsScroll;
	
	private JLabel lblYourPreviousAnswers;
	private JLabel lblResults;
	private JButton btnHome;
	private JButton btnDownload;
	private JButton btnSave;
	private JButton btnModels;
	private JButton btnOrganizationnalChart;
	private JButton btnRequirements;
	
	private ImageIcon logoCeSort;

	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public ResultView(Controller c) {

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
		this.setTitle("CeSort");
		
		this.controller = c;
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
		
		previousQuestionsPanel = new JPanel();
		previousQuestionsPanel.setBackground(new Color(204, 204, 255));
		previousQuestionsScroll = new JScrollPane(previousQuestionsPanel);
		layout.putConstraint(SpringLayout.WEST, previousQuestionsScroll, 479, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, previousQuestionsScroll, -10, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, previousQuestionsScroll, -10, SpringLayout.EAST, panel);
		previousQuestionsLayout = new BoxLayout(previousQuestionsPanel, BoxLayout.Y_AXIS);
		previousQuestionsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		previousQuestionsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.setSize(1000,600);
		this.setResizable(false);

		//setUpFrame();
	}	
		
	private void setUpFrame() {
		/* General things to do. */
		setContentPane(panel);
		// Set the pan with its layout
		panel.setLayout(layout);
		previousQuestionsPanel.setLayout(previousQuestionsLayout);

		panel.add(previousQuestionsScroll);
						
		lblYourPreviousAnswers = new JLabel("All your answers :");
		lblYourPreviousAnswers.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		layout.putConstraint(SpringLayout.SOUTH, lblYourPreviousAnswers, -539, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.NORTH, previousQuestionsScroll, 6, SpringLayout.SOUTH, lblYourPreviousAnswers);
		layout.putConstraint(SpringLayout.WEST, lblYourPreviousAnswers, 0, SpringLayout.WEST, previousQuestionsScroll);
		panel.add(lblYourPreviousAnswers);
		
		lblResults = new JLabel("Results");
		lblResults.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		layout.putConstraint(SpringLayout.NORTH, lblResults, 0, SpringLayout.NORTH, lblYourPreviousAnswers);
		layout.putConstraint(SpringLayout.WEST, lblResults, 203, SpringLayout.WEST, panel);
		panel.add(lblResults);
		
		btnHome = new JButton("Home");
		btnHome.setForeground(new Color(70, 130, 180));
		layout.putConstraint(SpringLayout.WEST, btnHome, 33, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, btnHome, 136, SpringLayout.WEST, panel);
		btnHome.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnHome.setForeground(new Color(255, 80, 80));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.displayHome();
			}
		});
		panel.add(btnHome);
		
		btnDownload = new JButton("<html><center>Download<br>resources</center></html>");
		btnDownload.setForeground(new Color(34, 139, 34));
		layout.putConstraint(SpringLayout.NORTH, btnHome, 0, SpringLayout.NORTH, btnDownload);
		layout.putConstraint(SpringLayout.SOUTH, btnHome, 0, SpringLayout.SOUTH, btnDownload);
		layout.putConstraint(SpringLayout.NORTH, btnDownload, -75, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnDownload, -24, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, btnDownload, -45, SpringLayout.WEST, previousQuestionsScroll);
		btnDownload.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
	    			chooser.setSelectedFile(new File("Resources.zip"));
	    			FileNameExtensionFilter ff = new FileNameExtensionFilter("Zip", "zip");
	    			chooser.addChoosableFileFilter(ff);
	    			chooser.setFileFilter(ff);
				chooser.setDialogTitle("Download resources");
				chooser.showSaveDialog(panel);
				if(chooser.getSelectedFile() != null) {
					controller.downloadResources(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		panel.add(btnDownload);
		
		btnSave = new JButton("<html><center>Save<br>project</center></html>");
		btnSave.setForeground(new Color(0, 128, 0));
		layout.putConstraint(SpringLayout.NORTH, btnSave, -75, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, btnSave, 38, SpringLayout.EAST, btnHome);
		layout.putConstraint(SpringLayout.SOUTH, btnSave, -24, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, btnSave, -38, SpringLayout.WEST, btnDownload);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setSelectedFile(new File("CeSortProject.cesort"));
				FileNameExtensionFilter ff = new FileNameExtensionFilter("CeSort", "cesort");
				chooser.addChoosableFileFilter(ff);
				chooser.setFileFilter(ff);
				chooser.setDialogTitle("Save project");
				chooser.showSaveDialog(panel);
				if(chooser.getSelectedFile() != null) {
					controller.saveResults(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnSave.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel.add(btnSave);
		
		
		FileInputStream fileP;
		JLabel imageP = null;
		try {
			fileP = new FileInputStream("Images/planning.png");
			ImageIcon logoP = new ImageIcon(ImageIO.read(fileP));
			imageP = new JLabel(logoP);
			imageP.setBounds(200,100,50,50);
			imageP.setOpaque(false);
	        panel.add(imageP);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
		FileInputStream fileM;
		JLabel imageM = null;
		try {
			fileM = new FileInputStream("Images/models.png");
			ImageIcon logoM = new ImageIcon(ImageIO.read(fileM));
			imageM = new JLabel(logoM);
			imageM.setBounds(200,100,50,50);
			imageM.setOpaque(false);
	        panel.add(imageM);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		FileInputStream fileR;
		JLabel imageR = null;
		try {
			fileR = new FileInputStream("Images/requirements.png");
			ImageIcon logoR = new ImageIcon(ImageIO.read(fileR));
			imageR = new JLabel(logoR);
			imageR.setBounds(200,100,50,50);
			imageR.setOpaque(false);
	        panel.add(imageR);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		FileInputStream fileO;
		JLabel imageO = null;
		try {
			fileO = new FileInputStream("Images/orgChart.png");
			ImageIcon logoO = new ImageIcon(ImageIO.read(fileO));
			imageO = new JLabel(logoO);
			imageO.setBounds(200,100,50,50);
			imageO.setOpaque(false);
	        panel.add(imageO);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		JButton btnPlanning = new JButton("Schedule");
		layout.putConstraint(SpringLayout.NORTH, imageP, -90, SpringLayout.NORTH, btnPlanning);
		layout.putConstraint(SpringLayout.WEST, imageP, 0, SpringLayout.WEST, btnPlanning);
		layout.putConstraint(SpringLayout.SOUTH, imageP, -10, SpringLayout.NORTH, btnPlanning);
		layout.putConstraint(SpringLayout.EAST, imageP, 0, SpringLayout.EAST, btnPlanning);
		layout.putConstraint(SpringLayout.NORTH, btnPlanning, 164, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, btnPlanning, 0, SpringLayout.WEST, btnDownload);
		btnPlanning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.displayResource(Resource.Schedule);
				}
		});
		panel.add(btnPlanning);
		
		btnModels = new JButton("Processes");
		layout.putConstraint(SpringLayout.NORTH, imageM, -90, SpringLayout.NORTH, btnModels);
		layout.putConstraint(SpringLayout.WEST, imageM, 0, SpringLayout.WEST, btnModels);
		layout.putConstraint(SpringLayout.SOUTH, imageM, -10, SpringLayout.NORTH, btnModels);
		layout.putConstraint(SpringLayout.EAST, imageM, 0, SpringLayout.EAST, btnModels);
		layout.putConstraint(SpringLayout.NORTH, btnModels, 0, SpringLayout.NORTH, btnPlanning);
		layout.putConstraint(SpringLayout.WEST, btnModels, 74, SpringLayout.WEST, panel);
		btnModels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.displayResource(Resource.StrucModel); 
		}});
		panel.add(btnModels);
		
		btnOrganizationnalChart = new JButton("Organizational chart");
		layout.putConstraint(SpringLayout.NORTH, imageO, -90, SpringLayout.NORTH, btnOrganizationnalChart);
		layout.putConstraint(SpringLayout.WEST, imageO, 0, SpringLayout.WEST, btnOrganizationnalChart);
		layout.putConstraint(SpringLayout.SOUTH, imageO, -10, SpringLayout.NORTH, btnOrganizationnalChart);
		layout.putConstraint(SpringLayout.EAST, imageO, 0, SpringLayout.EAST, btnOrganizationnalChart);
		layout.putConstraint(SpringLayout.NORTH, btnOrganizationnalChart, 136, SpringLayout.SOUTH, btnPlanning);
		layout.putConstraint(SpringLayout.EAST, btnOrganizationnalChart, -27, SpringLayout.WEST, previousQuestionsScroll);
		btnOrganizationnalChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.displayResource(Resource.OrgChart);
			}
		});
		panel.add(btnOrganizationnalChart);
		
		btnRequirements = new JButton("Requirements");
		layout.putConstraint(SpringLayout.NORTH, imageR, -90, SpringLayout.NORTH, btnRequirements);
		layout.putConstraint(SpringLayout.WEST, imageR, 0, SpringLayout.WEST, btnRequirements);
		layout.putConstraint(SpringLayout.SOUTH, imageR, -10, SpringLayout.NORTH, btnRequirements);
		layout.putConstraint(SpringLayout.EAST, imageR, 0, SpringLayout.EAST, btnRequirements);
		layout.putConstraint(SpringLayout.NORTH, btnRequirements, 0, SpringLayout.NORTH, btnOrganizationnalChart);
		layout.putConstraint(SpringLayout.WEST, btnRequirements, 56, SpringLayout.WEST, panel);
		btnRequirements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.displayResource(Resource.List);
				}
		});
		panel.add(btnRequirements);
		
		// Creation of a list of previous Q & A.
		if(listQA.values() != null) {
			for(String question : listQA.keySet()) {
				JLabel newQuestion = new JLabel("<html>" + question + "<html>");
				newQuestion.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				JLabel newAnswer = new JLabel("<html> >>> " + listQA.get(question) + "<html>");
				newAnswer.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				JLabel br = new JLabel(" ");
				previousQuestionsPanel.add(newQuestion);
				previousQuestionsPanel.add(newAnswer);
				previousQuestionsPanel.add(br);
				previousQuestionsPanel.revalidate();
				previousQuestionsPanel.repaint();
			}
		}
		
		panel.repaint();
		panel.revalidate();
	}
	
	// Tests for drawing a line
    public void paintComponents(Graphics g) {
        super.paintComponents(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(100, 100, 250, 260);
        g2.draw(lin);
    }
	
	public void startResultView(HashMap<String, String> listQA) {
		this.listQA = listQA;
		setUpFrame();
		this.setVisible(true);
	}
	
	public void closeResultView() {
		// Other things to be done ?
		this.setVisible(false);
	}
	/*
	public static void main(String[] args) {
		ResultView resultView = new ResultView();
		resultView.setUpFrame();
		resultView.setVisible(true);

	}
	*/
}
