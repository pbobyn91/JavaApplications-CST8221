/*
 * File Name: CalculatorSplashScreen.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 2
 * Date: Nov 1st, 2019
 * Professor: Svillan Ranev
 * Purpose: This is splash screen for the document, it prints out a screen before the the calculator is shown
 */

package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/** 
 * @author Patrick Bobyn
 * @version 1
 * @since 1.8.0_144
 */

public class CalculatorSplashScreen extends JWindow {
	
	/**
	 * duration sets the duration of the splashscreen.
	 * 
	 * @value 5000
	 */
	private final int duration;

	/**
	 * This is the constructor for the splash screen.
	 * 
	 * @param duration sets the duration of the splashscreen
	 */
	
	public CalculatorSplashScreen(int duration) {
		this.duration = duration;
	}
	
	/**
	 * This is the method that shows the splashscreen.
	 */
	
	public void showSplashWindow() {
		
		// create a panel and set the background to black
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLACK);
		
		// set the width and height, and set the dimensions of the panel (set the the size of the image)
		int width = 650;
		int height = 400;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);
		
		// give the panel the image and add another label with my name and student number
		JLabel label = new JLabel(new ImageIcon(getClass().getResource("splashscreen.png")));
		JLabel name = new JLabel("Patrick Bobyn  040 889 706", JLabel.CENTER);
		name.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));
		name.setForeground(Color.WHITE);
		
		// add the image and the name to the panel
		panel.add(label, BorderLayout.CENTER);
		panel.add(name, BorderLayout.SOUTH);
		
		// set the border of the panel
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		
		// show the panel
		setContentPane(panel);
		setVisible(true);
		
		// run the code for the duration
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			
		}
		dispose();
	}
}
