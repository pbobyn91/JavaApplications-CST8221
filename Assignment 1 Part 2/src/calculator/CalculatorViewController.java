/*
 * File Name: CalculatorViewController.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 2
 * Date: Nov 1st, 2019
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
	 * the backspace button was made global since some parameters change 
	 */
	private JButton backspace = new JButton(); // the backspace button
	
	/** 
	 * the checkbox button used the HEX buttons
	 */
	private JCheckBox checkbox; // the checkbox
	
		
	/**
	 * Constructor for the Calculator View
	 */
	
	public CalculatorViewController () {
		
		// VARIABLES
		// declare variables
		CalculatorModel model = new CalculatorModel();
		Controller controller = new Controller(model); // the controller that is used
		JPanel north = new JPanel(new BorderLayout()); // the whole North Panel
		JPanel upperNorth = new JPanel(new BorderLayout()); // the upper part of the North Panel
		JPanel modePanel = new JPanel(new BorderLayout()); // the lower part of the North Panel
		JPanel center = new JPanel(new BorderLayout()); // the center panel 
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
		backspace.setOpaque(true);
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
				hexButtons[i] = createButton(keys[i], keys[i], Color.BLACK, Color.BLUE, controller);
				keypad.add(hexButtons[i]);
				hexButtons[i].setEnabled(false);
			} else if (keys[i].equals(".")) {
				dotButton = createButton(keys[i], keys[i], Color.BLACK, Color.MAGENTA, controller);
				keypad.add(dotButton);
			} else if (keys[i].equals("\u00b1")) {
				keypad.add(createButton(keys[i], keys[i], Color.BLACK, Color.MAGENTA, controller));
			} else {
				keypad.add(createButton(keys[i], keys[i], Color.BLACK, Color.BLUE, controller));
			}
		}
		center.add(keypad, BorderLayout.CENTER);
		
		// Add Arithmetic buttons
		// multiply button
		multiply.setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.BLACK));
		multiply.setText("*");
		multiply.setBackground(Color.CYAN);
		multiply.setPreferredSize(new Dimension(48, 45));
		multiply.addActionListener(controller);
		
		west.add(multiply, BorderLayout.NORTH);
		
		// divide button
		divide.setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.BLACK));
		divide.setText("/");
		divide.setBackground(Color.CYAN);
		divide.setPreferredSize(new Dimension(48, 45));
		divide.addActionListener(controller);
		
		west.add(divide, BorderLayout.SOUTH);
		
		// addition button
		addition.setBorder(BorderFactory.createMatteBorder(0,1,1,1, Color.BLACK));
		addition.setText("+");
		addition.setBackground(Color.CYAN);
		addition.setPreferredSize(new Dimension(48, 45));
		addition.addActionListener(controller);
		
		east.add(addition, BorderLayout.NORTH);
		
		// subtraction button
		subtraction.setBorder(BorderFactory.createMatteBorder(1,1,0,1, Color.BLACK));
		subtraction.setText("-");
		subtraction.setBackground(Color.CYAN);
		subtraction.setPreferredSize(new Dimension(48, 45));
		subtraction.addActionListener(controller);
			
		east.add(subtraction, BorderLayout.SOUTH);
		
		// Clear Button
		clear = createButton("C", "clear", Color.BLACK, Color.RED, controller);
		clear.setBorder(BorderFactory.createMatteBorder(5,0,0,0, Color.BLACK));
		center.add(clear, BorderLayout.NORTH);
		
		// Equal Button
		equal = createButton("=", "=", Color.BLACK, Color.YELLOW, controller);
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
		
		radios.add(checkbox);
		
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
		
		CalculatorModel model; // an implementation of the CalculatorModel class to be used in this class
		
		/**
		 * A constructor to create the model 
		 * 
		 * @param model the CalculatorModel that is used 
		 */
		public Controller (CalculatorModel model) {
			this.model = model;
		}
		
		/**
		 * This is an action listener and listens for the actions that are performed.
		 * 
		 * @param event The event that was performed.
		 */
		
		public void actionPerformed(ActionEvent event) {
			
			boolean errors = false;
			
			// switch case for all the buttons that can be pressed
			switch (event.getActionCommand()) {
			// all the numbers
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			case "8":
			case "9":
			case "0":
			case "A":
			case "B":
			case "C":
			case "D":
			case "E":
			case "F":
				// make sure no errors were done 
				if (!model.getErrorMode()) {
					
					// enable the backspace button if it was disabled
					if (!backspace.isEnabled())
						display2.setText("");
					backspace.setEnabled(true);
					
					// if the operational mode is not 1 reset the Error
					if (model.getOperationalMode() != 1)
						resetError();
					
					// change the display with the characters
					if (display2.getText().equals("0.0")) {
						display2.setText(event.getActionCommand());
					} else {
						display2.setText(display2.getText().concat(event.getActionCommand()));
					}
					
					// change the error if one has occured
					if (model.getErrorMode())
						errors = true;
				}
				break;
			// all the arithemtic operators
			case "*":
			case "/":
			case "-":
			case "+":
				if (!model.getErrorMode()) {
					
					// reenable the backsapce button
					backspace.setEnabled(true);
					
					// assign the 1st operand and set the text for the dislays
					model.setOperand1(display2.getText());
					display1.setText(display2.getText().concat(" " + event.getActionCommand()));
					display2.setText("");
					
					// assign the arithmetic character
					model.setArithmetic(event.getActionCommand());
					
					if (model.getErrorMode())
						errors = true;
				}
				break;
			// the equal button
			case "=":
				// set the displays 
				if (display2.getText().equals(""))
					model.setOperand2(model.getOperand1());
				else if (display1.getText().equals(""))
					model.setOperand1(display2.getText());
				else 
					model.setOperand2(display2.getText());
				display1.setText("");
				
				// disable the backspace button
				backspace.setEnabled(false);
				
				// do the operations
				display2.setText(model.operation());
				
				if (model.getErrorMode())
					errors = true;
				
				break;
			// the dot button
			case ".":
				if (!model.getErrorMode()) {
					// add the dot to the display
					display2.setText(display2.getText().concat("."));
				}
				break;
			// the +/- button
			case "\u00b1":
				if (!model.getErrorMode()) {
					// add or take off the +/- on the number
					if (display2.getText().startsWith("-")) {
						display2.setText(display2.getText().substring(1));
					} else {
						display2.setText("-".concat(display2.getText()));
					}
					if (model.getErrorMode()) {
						errors = true;
					}
				}
				break;
			// the backspace button
			case "\u21da":
				if (!model.getErrorMode()) {
					
					// if the display is greater than > 1 
					if (display2.getText().length() >= 1) { 
							
						// subtract the display
						display2.setText(display2.getText().substring(0, display2.getText().length() - 1)); 
						
						if (display2.getText().length() == 1 && display2.getText().contains("-")) {
							setDisplays("", "0.0"); 
							model.reset();
						}
					} else { // otherwise the display is 0.0
						setDisplays("", "0.0"); 
						model.reset();
					}
				}
					
				if (model.getErrorMode()) {
					errors = true;
				}
				break;
			// the clear button
			case "clear":
				// set the display to nothing, and reset the operands
				setDisplays("", "0.0");
				model.reset();
				backspace.setEnabled(true);
				resetError();
				break;
			// the .0 option
			case ".0":
				// set the operational mode 
				model.setOperationalMode(0);
				model.setPrecision(0);
				
				// reset the displays
				setDisplays("", "");
				
				// turn off hex buttons
				if (hexButtons[0].isEnabled()) {
					for (int i = 0; i < hexButtons.length; i++)
						hexButtons[i].setEnabled(false);
					dotButton.setEnabled(true);
					
					// change the error button
					error.setText("F");
					error.setBackground(Color.YELLOW);
				}
				break;
			// the .00 option
			case ".00":
				// set the operational mode
				model.setOperationalMode(0);
				model.setPrecision(1);
				
				// reset the displays
				setDisplays("", "");
				
				// turn off the hexbuttons
				if (hexButtons[0].isEnabled()) {
					for (int i = 0; i < hexButtons.length; i++)
						hexButtons[i].setEnabled(false);
					dotButton.setEnabled(true);
					
					// change the error button
					error.setText("F");
					error.setBackground(Color.YELLOW);
				}
				break;
			// the sci option
			case "SCI":
				// set the operational mode
				model.setOperationalMode(0);
				model.setPrecision(2);
				
				// reset the displays
				setDisplays("", "");
				
				// turn off the hex buttons
				if (hexButtons[0].isEnabled()) {
					for (int i = 0; i < hexButtons.length; i++)
						hexButtons[i].setEnabled(false);
					dotButton.setEnabled(true);
					
					// change the error button
					error.setText("F");
					error.setBackground(Color.YELLOW);
				}
				break;
			// the hex button
			case "HEX":
				
				// reset the displays
				setDisplays("", "");
				
				// set the hexbuttons to enabled and disable the dotbutton, change the operational mode
				for (int i = 0; i < hexButtons.length; i++)
					hexButtons[i].setEnabled(true);
				dotButton.setEnabled(false);
				model.setOperationalMode(1);
					
				// change the error button
				error.setText("H");
				error.setBackground(Color.GREEN);
				
				break;
				
			}
			
			// if there was an error set the error button
			if (errors) {
				error.setText("E");
				error.setBackground(Color.RED);
			}
		}
		
		/**
		 * Resets the display
		 * @param disp1 What display 1 becomes
		 * @param disp2 What display 2 becomes
		 */
		private void setDisplays(String disp1, String disp2) {
			display1.setText(disp1);
			display2.setText(disp2);
		}
		
		/**
		 * Reset the error field
		 */
		private void resetError() {
			error.setText("F");
			error.setBackground(Color.YELLOW);
		}
	}
}