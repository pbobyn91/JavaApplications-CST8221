/*
 * File Name: Accessible.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: An interface called accessible that has the options getDisplay() and clostChat().
 */

import javax.swing.JTextArea;

/**
 * @author Pat
 * @version 1.0
 * @since 1.8.0_144
 */
public interface Accessible {

	/**
	 * The method to be overriden used to get the Display
	 * @return Returns the display
	 */
	public JTextArea getDisplay();
	
	/**
	 * the method to be overriden used to close the chat
	 */
	public void closeChat();
}
