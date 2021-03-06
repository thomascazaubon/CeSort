

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import java.awt.Font;

public class QuestionView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	
	private JPanel previousQuestionsPanel;
	private BoxLayout previousQuestionsLayout;
	private JScrollPane previousQuestionsScroll;

	private JPanel answersPanel;
	private BoxLayout answersLayout;
	private JScrollPane answersScroll;
	
	private Controller controller;
	private Question currentQuestion;
	private HashMap<String, String> listQA;
	
	private String currentAnswer;
	
	private ArrayList<JRadioButton> listBtnAnswer;
	
	private JLabel questionLabel;
	private JLabel lblQuestionItself;
	private JLabel lblYourPreviousAnswers;
	private JButton btnPreviousQuestion;
	private JButton btnNextQuestion;
	private JButton btnHome;
	
	private ImageIcon logoCeSort;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public QuestionView(Controller c) {
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
		
		listBtnAnswer = new ArrayList<JRadioButton>();
		
		previousQuestionsPanel = new JPanel();
		previousQuestionsPanel.setBackground(new Color(224, 255, 255));
		previousQuestionsScroll = new JScrollPane(previousQuestionsPanel);
		layout.putConstraint(SpringLayout.WEST, previousQuestionsScroll, 479, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, previousQuestionsScroll, -79, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, previousQuestionsScroll, -10, SpringLayout.EAST, panel);
		previousQuestionsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		previousQuestionsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		previousQuestionsLayout = new BoxLayout(previousQuestionsPanel, BoxLayout.Y_AXIS);
		
		answersPanel = new JPanel();
		answersPanel.setBackground(new Color(211, 211, 211));
		answersScroll = new JScrollPane(answersPanel);
		layout.putConstraint(SpringLayout.WEST, answersScroll, 40, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, answersScroll, -148, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, answersScroll, -40, SpringLayout.WEST, previousQuestionsScroll);
		answersScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		answersScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		answersLayout = new BoxLayout(answersPanel, BoxLayout.Y_AXIS);
		
		this.setResizable(false);
		this.setSize(1000,600);
		//setUpFrame();

	}
	private void setUpFrame() {
		/* General things to do. */
		setContentPane(panel);
		// Set the pan with its layout
		panel.setLayout(layout);
		previousQuestionsPanel.setLayout(previousQuestionsLayout);
		answersPanel.setLayout(answersLayout);

		
		panel.add(previousQuestionsScroll);
		panel.add(answersScroll);
		
		questionLabel = new JLabel("Question number "+ currentQuestion.getNum());
		questionLabel.setOpaque(true);
		layout.putConstraint(SpringLayout.NORTH, questionLabel, 34, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, questionLabel, 40, SpringLayout.WEST, panel);
		questionLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		panel.add(questionLabel);
		
		lblQuestionItself = new JLabel("<html>"+currentQuestion.getTitle()+"<html>");
		lblQuestionItself.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		layout.putConstraint(SpringLayout.NORTH, lblQuestionItself, 23, SpringLayout.SOUTH, questionLabel);
		layout.putConstraint(SpringLayout.SOUTH, lblQuestionItself, -379, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.NORTH, answersScroll, 25, SpringLayout.SOUTH, lblQuestionItself);
		layout.putConstraint(SpringLayout.WEST, lblQuestionItself, 40, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, lblQuestionItself, -60, SpringLayout.WEST, previousQuestionsScroll);
		panel.add(lblQuestionItself);
		
		lblYourPreviousAnswers = new JLabel("Your previous answers :");
		layout.putConstraint(SpringLayout.WEST, lblYourPreviousAnswers, 272, SpringLayout.EAST, questionLabel);
		lblYourPreviousAnswers.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		layout.putConstraint(SpringLayout.SOUTH, lblYourPreviousAnswers, -539, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.NORTH, previousQuestionsScroll, 6, SpringLayout.SOUTH, lblYourPreviousAnswers);
		panel.add(lblYourPreviousAnswers);
		
		btnPreviousQuestion = new JButton("Previous question");
		layout.putConstraint(SpringLayout.NORTH, btnPreviousQuestion, 55, SpringLayout.SOUTH, answersScroll);
		layout.putConstraint(SpringLayout.WEST, btnPreviousQuestion, 40, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnPreviousQuestion, -53, SpringLayout.SOUTH, panel);
		btnPreviousQuestion.setForeground(new Color(0, 0, 0));
		btnPreviousQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Go to previous question
				controller.previousQuestion();
			}
		});
		panel.add(btnPreviousQuestion);
		if(currentQuestion.getNum() == 1) {
			btnPreviousQuestion.setVisible(false);
		}
		
		btnNextQuestion = new JButton("Next question");
		layout.putConstraint(SpringLayout.EAST, btnPreviousQuestion, -55, SpringLayout.WEST, btnNextQuestion);
		layout.putConstraint(SpringLayout.NORTH, btnNextQuestion, -1, SpringLayout.NORTH, btnPreviousQuestion);
		layout.putConstraint(SpringLayout.WEST, btnNextQuestion, 257, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnNextQuestion, -54, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, btnNextQuestion, 0, SpringLayout.EAST, lblQuestionItself);
		btnNextQuestion.setForeground(new Color(46, 139, 87));
		btnNextQuestion.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnNextQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Advance to the next question
				controller.nextQuestion(currentAnswer);
				}
		});
		panel.add(btnNextQuestion);
		
		btnHome = new JButton("Home");
		layout.putConstraint(SpringLayout.NORTH, btnHome, 18, SpringLayout.SOUTH, previousQuestionsScroll);
		layout.putConstraint(SpringLayout.WEST, btnHome, -166, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnHome, -21, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, btnHome, -61, SpringLayout.EAST, panel);
		btnHome.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		btnHome.setForeground(new Color(255, 80, 80));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Advance to the next question
				controller.displayHome();
				}
		});
		panel.add(btnHome);
		
		// Creation of a list of answers.
		if(currentQuestion.getAnswers() != null) {
			for(String answer : currentQuestion.getAnswers().values()) {
				JRadioButton newAnswer = new JRadioButton(answer);
				newAnswer.setOpaque(true);
				listBtnAnswer.add(newAnswer);
				newAnswer.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				newAnswer.setBackground(answersPanel.getBackground());
				newAnswer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Check out of the other answers
						for(JRadioButton rb : listBtnAnswer) {
							if(rb.equals(e.getSource())) {
								// Advance to the next question
								currentAnswer = e.getActionCommand();
							}
							else {
								rb.setSelected(false);
							}
						}
					}
				});
				answersPanel.add(newAnswer);
				answersPanel.revalidate();
				answersPanel.repaint();
			}
		}
		
		// Creation of a list of previous Q & A.
		if(listQA != null) {
			for(String question : listQA.keySet()) {
				JLabel newQuestion = new JLabel("<html>"+question+"<html>");
				newQuestion.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				JLabel newAnswer = new JLabel("<html> >>> " + listQA.get(question) + "<html>");
				newAnswer.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
				JLabel br = new JLabel(" ");
				br.setFont(new Font("Lucida Grande", Font.BOLD, 13));
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
	
	
	
	
	public void startQuestionView(Question cQ, HashMap<String, String> QA) {
		this.currentQuestion = cQ;
		this.listQA = QA;
		setUpFrame();
		this.setVisible(true);
	}
	
	public void closeQuestionView() {
		// Other things to be done ?
		this.setVisible(false);
	}
}
