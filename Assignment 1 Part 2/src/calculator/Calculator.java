/*
 * File Name: Calculator.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 2
 * Date: Nov 1st, 2019
 * Professor: Svillan Ranev
 * Purpose: This is the main class for the calculator, it gathers all objects and prints the calculator out to the screen.
 */

package calculator;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

/** 
 * @author Patrick Bobyn
 * @version 1
 * @since 1.8.0_144
 */

public class Calculator {

	/**
	 * the main function that runs the whole calculator
	 * @param args the arguments passed in from command line, not used
	 */
	
	public static void main(String[] args) {
		
		// create the splash screen and show it along with the View of the calculator
		CalculatorSplashScreen splashScreen = new CalculatorSplashScreen(5000); // the splash screen 
		splashScreen.showSplashWindow(); // display the splash screen 
		CalculatorViewController calculator = new CalculatorViewController(); // create the view 
		
		EventQueue.invokeLater(new Runnable() { 
		
			/**
			 * Method to run the calculator
			 */
			
			public void run() {
				
				JFrame frame = new JFrame(); // create the calculator frame
				
				// Create the calculator
				frame.setTitle("Patrick's Calculator App");
				frame.setMinimumSize(new Dimension(380,540));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(calculator);
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		}); 
	}
}
