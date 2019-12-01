/*
 * File Name: CalculatorViewController.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 1
 * Date: Oct 15th, 2019
 * Professor: Svillan Ranev
 * Purpose: This is the view for the calculator, it creates all the buttons and places them in the frame.
 * Class List: CalculatorViewController.java, Controller.java
 */

package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
	
/** 
 * @author Patrick Bobyn
 * @version 1
 * @see Controller, ActionListener
 * @since 1.8.0_144
 */

public class CalculatorViewController extends JPanel {
		
	/**
	 * display1 Displays the 1st Display in the display section of the calculator
	 */
	private JTextField display1; // the calculator display1 reference
	
	/**
	 * display1 Displays the 2nd Display in the display section of the calculator
	 */
	private JTextField display2; // the calculator display2 reference
	
	/**
	 * error Displays the error label at the top of left of the calculator
	 */
	private JLabel error; // the mode/error display label reference
	
	/**
	 * dotButton Displays the dot button in the keypad
	 */
	private JButton dotButton; // the decimal point (dot) button reference
	
	/**
	 * hexButtons An array that is a reference to all of the hexButton characters in the keypad
	 */
	private JButton [] hexButtons; // reference to container for alphabetical hex buttons
		
	/**
	 * Constructor for the Calculator View
	 */
	
	public CalculatorViewController () {
		
		// VARIABLES
		// declare variables
		Controller controller = new Controller(); // the controller that is used
		JPanel north = new JPanel(new BorderLayout()); // the whole North Panel
		JPanel upperNorth = new JPanel(new BorderLayout()); // the upper part of the North Panel
		JPanel modePanel = new JPanel(new BorderLayout()); // the lower part of the North Panel
		JPanel center = new JPanel(new BorderLayout()); // the center panel
		JButton backspace = new JButton(); // the backspace button
		JCheckBox checkbox; // the checkbox 
		ButtonGroup radios = new ButtonGroup(); // button group for the radio buttons
		JRadioButton singleZero; // the 1st radio button
		JRadioButton doubleZeroes; // the 2nd radio button
		JRadioButton sci; // the 3rd radio button
		JPanel east = new JPanel(new GridLayout(2,1,3,3)); // the east panel 
		JPanel west = new JPanel(new GridLayout(2,1,3,3)); // the west panel
		JButton multiply = new JButton(); // the multiply button
		JButton divide = new JButton(); // the divide button
		JButton addition = new JButton(); // the addition button
		JButton subtraction = new JButton(); // the subtraction button
		JPanel keypad = new JPanel(); // the keypad panel
		JPanel display = new JPanel(new BorderLayout()); // the display panel for displays 1 and 2
		JButton clear = new JButton(); // the clear button
		JButton equal = new JButton(); // the equal button
		JPanel radioButtons = new JPanel(new BorderLayout()); // the radio buttons panel
		JPanel box = new JPanel(new BorderLayout()); // the box panel
				
		// class variables
		display1 = new JTextField(); 
		display2 = new JTextField();
		String keys[] = {"A", "B", "C", "D", "E", "F", "7", "8", "9", "4", "5", "6", "1", "2", "3", ".", "0", "\u00b1"};
				
		// add error to the north Panel
		error = new JLabel("F");
		error.setPreferredSize(new Dimension(52,55));
		error.setOpaque(true);
		error.setBackground(Color.YELLOW);
		error.setBorder(BorderFactory.createMatteBorder(0,1,0,5, Color.BLACK));
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setFont(new Font(error.getFont().getName(), error.getFont().getStyle(), 20));
		
		upperNorth.add(error, BorderLayout.WEST);
						
		// Add the Backspace feature to the panel 
		backspace.setPreferredSize(new Dimension(52,55));
		backspace.setOpaque(false);
		backspace.setBackground(Color.YELLOW);
		backspace.setBorder(BorderFactory.createMatteBorder(0,5,0,1, Color.BLACK));
		backspace.setText("\u21da");
		backspace.setFont(new Font(backspace.getFont().getName(), backspace.getFont().getStyle(), 20));
		backspace.setToolTipText("Backspace (Alt-B)");
		backspace.setMnemonic('b');
		backspace.setActionCommand("\u21da");
		backspace.addActionListener(controller);
		
		upperNorth.add(backspace, BorderLayout.EAST);
						
		// Keypad buttons
		keypad.setLayout(new GridLayout(6,3,3,3));
		keypad.setBackground(Color.WHITE);
		hexButtons = new JButton[6];
		// Add all of buttons to the keypad
		for (int i = 0; i < keys.length; i++) {
			if (i < 6) {
				hexButtons[i] = createButton(keys[i], keys[i], Color.BLACK, Color.BLUE, new Controller());
				keypad.add(hexButtons[i]);
				hexButtons[i].setEnabled(false);
			} else if (keys[i].equals(".")) {
				dotButton = createButton(keys[i], keys[i], Color.BLACK, Color.MAGENTA, new Controller());
				keypad.add(dotButton);
			} else if (keys[i].equals("\u00b1")) {
				keypad.add(createButton(keys[i], keys[i], Color.BLACK, Color.MAGENTA, new Controller()));
			} else {
				keypad.add(createButton(keys[i], keys[i], Color.BLACK, Color.BLUE, new Controller()));
			}
		}
		center.add(keypad, BorderLayout.CENTER);
		
		// Add Arithmetic buttons
		// multiply button
		multiply.setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.BLACK));
		multiply.setText("*");
		multiply.setBackground(Color.CYAN);
		multiply.setPreferredSize(new Dimension(48, 45));
		multiply.addActionListener(new Controller());
		
		west.add(multiply, BorderLayout.NORTH);
		
		// divide button
		divide.setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.BLACK));
		divide.setText("/");
		divide.setBackground(Color.CYAN);
		divide.setPreferredSize(new Dimension(48, 45));
		divide.addActionListener(new Controller());
		
		west.add(divide, BorderLayout.SOUTH);
		
		// addition button
		addition.setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.BLACK));
		addition.setText("+");
		addition.setBackground(Color.CYAN);
		addition.setPreferredSize(new Dimension(48, 45));
		addition.addActionListener(new Controller());
		
		east.add(addition, BorderLayout.NORTH);
		
		// subtraction button
		subtraction.setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.BLACK));
		subtraction.setText("-");
		subtraction.setBackground(Color.CYAN);
		subtraction.setPreferredSize(new Dimension(48, 45));
		subtraction.addActionListener(new Controller());
			
		east.add(subtraction, BorderLayout.SOUTH);
		
		// Clear Button
		clear = createButton("C", "C", Color.BLACK, Color.RED, new Controller());
		clear.setBorder(BorderFactory.createMatteBorder(5,0,0,0, Color.BLACK));
		center.add(clear, BorderLayout.NORTH);
		
		// Equal Button
		equal = createButton("=", "=", Color.BLACK, Color.YELLOW, new Controller());
		center.add(equal, BorderLayout.SOUTH);
		
		// Displays
		// 1st display
		display1.setPreferredSize(new Dimension(14, 30));
		display1.setBackground(Color.WHITE);
		display1.setEditable(false);
		display1.setHorizontalAlignment(JTextField.RIGHT);
		display1.setBorder(BorderFactory.createEmptyBorder());
			
		// 2nd display
		display2.setPreferredSize(new Dimension(14,30));
		display2.setBackground(Color.WHITE);
		display2.setEditable(false);
		display2.setHorizontalAlignment(JTextField.RIGHT);
		display2.setBorder(BorderFactory.createEmptyBorder());
		display2.setText("0.0");
		
		// add the displays to display
		display.add(display1, BorderLayout.NORTH);
		display.add(display2, BorderLayout.SOUTH);
		
		upperNorth.add(display, BorderLayout.CENTER);
						
		// radio buttons
		singleZero = new JRadioButton(".0", false);
		singleZero.addActionListener(controller);
		singleZero.setBackground(Color.YELLOW);
		doubleZeroes = new JRadioButton(".00", true);
		doubleZeroes.addActionListener(controller);
		doubleZeroes.setBackground(Color.YELLOW);
		sci = new JRadioButton("SCI", false);
		sci.addActionListener(controller);
		sci.setBackground(Color.YELLOW);
		
		// add the radio buttons
		radios.add(singleZero);
		radios.add(doubleZeroes);
		radios.add(sci);
		radioButtons.add(singleZero, BorderLayout.WEST);
		radioButtons.add(doubleZeroes, BorderLayout.CENTER);
		radioButtons.add(sci, BorderLayout.EAST);
		
		radioButtons.setBorder(BorderFactory.createMatteBorder(5,0,0,0,Color.BLACK));
		
		// Add Hex Checkbox
		checkbox = new JCheckBox("HEX");
		checkbox.setBackground(Color.green);
		checkbox.addActionListener(controller);
		
		box.add(checkbox);
		box.setBorder(BorderFactory.createMatteBorder(5,0,0,0,Color.BLACK));
		
		// add the modePanel features
		modePanel.add(box, BorderLayout.WEST);
		modePanel.add(radioButtons, BorderLayout.EAST);
		
		// set Borders
		modePanel.setBorder(BorderFactory.createEmptyBorder());
		east.setBorder(BorderFactory.createMatteBorder(5,5,0,0,Color.BLACK));
		west.setBorder(BorderFactory.createMatteBorder(5,0,0,5,Color.BLACK));
		east.setBackground(Color.BLACK);
		west.setBackground(Color.BLACK);
		
		// modePanel Features
		modePanel.setBackground(Color.BLACK);
		
		// Add the upper and lower Panels to NORTH
		north.add(upperNorth, BorderLayout.NORTH);
		north.add(modePanel, BorderLayout.SOUTH);		
		
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));
		
		// Add all PANELS to the FRAME
		add(north, BorderLayout.NORTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	/** 
	 * This method creates buttons for the panel.
	 * 
	 * @param text The text that is to be put into the button
	 * @param ac the action command for the listener
	 * @param fg the foreground color of the button
	 * @param bg the background color of the button
	 * @param handler the action listener for the button
	 * @return Returns a Button object
	 */
	
	private JButton createButton (String text, String ac, Color fg, Color bg, ActionListener handler) {
		
		// create the button
		JButton temp = new JButton();
		
		// set the text and the colours
		temp.setText(text);
		temp.setForeground(fg);
		temp.setBackground(bg);
		
		// sets the actionCommand if it was not set to null
		if (ac != null)
			temp.setActionCommand(ac);
	
		// set the font
		temp.setFont(new Font(temp.getFont().getName(), temp.getFont().getStyle(), 20));
		
		// add the action listener
		temp.addActionListener(handler);
		
		// return the button
		return temp;
	}
	
	/** 
	 * @author Patrick Bobyn
	 * @version 1
	 * @since 1.8.0_144
	 */
	
	private class Controller implements ActionListener {
		
		/**
		 * This is an action listener and listens for when an action was performed.
		 * 
		 * @param event The event that was performed.
		 */
		
		public void actionPerformed(ActionEvent event) {
			display2.setText(event.getActionCommand());
		}
	}
}