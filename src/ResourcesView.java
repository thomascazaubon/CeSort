

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
import java.awt.Image;

public class ResourcesView extends JFrame {
	static final long serialVersionUID = 1L;
	
	/* * * * * A T T R I B U T E S * * * * */
	
	private JPanel panel;
	private SpringLayout layout;
	private JLabel error;
	
	private String res;
	private ImageIcon imageIcon; 

	private Controller controller;
	private JLabel imageRes;
	
	/* * * * * C O N S T R U C T O R * * * * */
	
	public ResourcesView(Controller c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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

		imageRes = new JLabel();
		layout.putConstraint(SpringLayout.NORTH, btnResults, 18, SpringLayout.SOUTH, imageRes);
		layout.putConstraint(SpringLayout.NORTH, imageRes, 26, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imageRes, 25, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, imageRes, -121, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, imageRes, -33, SpringLayout.EAST, panel);
		Image imgScaled = imageIcon.getImage().getScaledInstance(540, 350, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(imgScaled);
		imageRes.setIcon(imageIcon);
		imageRes.setOpaque(false);
	    panel.add(imageRes);
	    
	    JLabel error = new JLabel("");
	    layout.putConstraint(SpringLayout.NORTH, error, 16, SpringLayout.SOUTH, btnResults);
	    layout.putConstraint(SpringLayout.WEST, error, 57, SpringLayout.WEST, panel);
	    layout.putConstraint(SpringLayout.SOUTH, error, -10, SpringLayout.SOUTH, panel);
	    layout.putConstraint(SpringLayout.EAST, error, 536, SpringLayout.WEST, panel);
	    panel.add(error);
	    
	    JButton btnNewButton = new JButton("Open to modify");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		controller.modifyResource(res);
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