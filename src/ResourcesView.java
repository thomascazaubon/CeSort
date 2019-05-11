

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.Font;

public class ResourcesView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	
	private String res;
	private ImageIcon imageIcon; 

	public Controller controller;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public ResourcesView(Controller c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.controller = c;
		
		panel = new JPanel();
		layout = new SpringLayout();
		
		this.setSize(600,600);
		//setUpFrame();

	}
	private void setUpFrame() {
		/* General things to do. */
		setContentPane(panel);
		// Set the pan with its layout
		panel.setLayout(layout);
		
		JButton btnResults = new JButton("Results");
		layout.putConstraint(SpringLayout.NORTH, btnResults, 521, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, btnResults, 25, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, btnResults, -10, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, btnResults, -421, SpringLayout.EAST, panel);
		btnResults.setFont(new Font("Lucida Grande", Font.BOLD, 20));
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
		layout.putConstraint(SpringLayout.NORTH, lblResource, 26, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, lblResource, 27, SpringLayout.WEST, panel);
		panel.add(lblResource);
		

		JLabel imageRes = null;		
		imageRes = new JLabel(imageIcon);
		layout.putConstraint(SpringLayout.NORTH, imageRes, 70, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imageRes, 70, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, imageRes, -70, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, imageRes, -70, SpringLayout.EAST, panel);
		
		imageRes.setOpaque(false);
	    panel.add(imageRes);
		
		panel.repaint();
		panel.revalidate();
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