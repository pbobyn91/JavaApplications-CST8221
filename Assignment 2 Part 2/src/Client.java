/*
 * File Name: Client.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: The main method for the client side of the chat.
 */

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author Pat
 * @version 1.0
 * @since 1.8.0_144
 */
public class Client {

	/**
	 * The client side main method 
	 * @param args The default main arugments
	 */
	public static void main (String [] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				// set the ui as the frame
				JFrame frame = new ClientChatUI ("Patrick's ClientChatUI");
				
				frame.setLocationByPlatform(true);
				frame.setMinimumSize(new Dimension(588, 500));
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
