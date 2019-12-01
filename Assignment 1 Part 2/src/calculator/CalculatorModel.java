/*
 * File Name: CalculatorModel.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 2
 * Date: Nov 1st, 2019
 * Professor: Svillan Ranev
 * Purpose: This is the operations for the entire calculator
 */

package calculator;

public class CalculatorModel {
	/**
	 * The string holder for the 1st operand
	 */
	private String operand1 = "";
	
	/**
	 * the string holder for the 2nd operand
	 */
	private String operand2 = "";
	
	/**
	 * the string holder for the arithmetic
	 */
	private String arithmetic = "";
	
	/**
	 * the flag for the operational mode
	 */
	private int operationalMode = 0;
	
	/**
	 * the flag for the precision
	 */
	private int precision = 1;
	
	/**
	 * the flag for the errorMode
	 */
	private boolean errorMode = false;
	
	/**
	 * Reset all of the values back to nothing
	 */
	public void reset() {
		operand1 = "";
		operand2 = "";
		arithmetic = "";
	}
	
	/**
	 * set operand 1
	 * @param text the value operand 1 will become
	 */
	public void setOperand1 (String text) {
		this.operand1 = text;
	}
	
	/**
	 * Returns operand1
	 * @return Returns Operand1
	 */
	public String getOperand1 () {
		return this.operand1;
	}
	
	/**
	 * Set operand 2
	 * @param text the value to for operand 2
	 */
	public void setOperand2 (String text) {
		this.operand2 = text;
	}
	
	/**
	 *  set arithmetic
	 *  @param text the text that sets the arithmetic
	 */
	public void setArithmetic (String text) {
		this.arithmetic = text;
	}
	
	/**
	 * Set the operational mode
	 * @param mode the value of operational mode
	 */
	public void setOperationalMode (int mode) {
		this.operationalMode = mode;
	}
	
	/**
	 * Get the operational mode
	 * @return Returns the operational mode
	 */
	public int getOperationalMode () {
		return this.operationalMode;
	}
	
	/**
	 * Set precision
	 * @param precision the value to set to precision
	 */
	public void setPrecision (int precision) {
		this.precision = precision;
	}
	
	/**
	 * Return the operation form based on current precision 
	 * @return return the calculation
	 */
	public String operation () {
		String operation = calculate();
		return operation;
	}
	
	/**
	 * Set error mode
	 * @param error the value to be assigned to error mode, subject to change
	 */
	public void setErrorMode(boolean error) {
		
		// if the operands or arithmetic are empty then set error to true
		if (this.operand1 == null || this.operand2 == null || this.arithmetic == null)
			error = true;
		else if (Float.isInfinite(Float.valueOf(this.operand1)) || Float.isInfinite(Float.valueOf(this.operand2)) || 
				Float.isNaN(Float.valueOf(this.operand1)) || Float.isNaN(Float.valueOf(this.operand2))) {
			error = true;
		}
		
		this.errorMode = error;
	}
	
	/**
	 * Get error mode
	 * @return the error mode
	 */
	public boolean getErrorMode () {
		return this.errorMode;
	}
	
	/**
	 * Calculate the equation
	 * @return the end calculation
	 */
	private String calculate () {
		
		// string for the final result, set to illegal for incase nothing works
		String result = "Illegal Mode";
		
		// hexadecimal result
		if (this.operationalMode == 1) {
			
			int op1 = 0, op2 = 0;
			
			// make sure the math can be done 
			if (this.operand2.equals("0") && this.arithmetic.equals("/")) {
				this.setErrorMode(true);
				return "Cannot divide by Zero";
			}
			
			// try to convert String to int
			try {	
				op1 = Integer.parseInt(this.operand1, 16);
				op2 = Integer.parseInt(this.operand2, 16);
			} catch (Exception e) {
				this.setErrorMode(true);
				return "Operators not defined";
			}
			
			// set the return  value based on the arithmetic
			switch(this.arithmetic) {
			case "*":
				result = String.format("%X", op1 * op2);
				break;
			case "/":
				if (op1 < 0 && op2 < 0) {
					op1 = -op1;
					op2 = -op2;
					result = String.format("%X",  op1 / op2);
				} else if (op1 < 0) {
					op1 = -op1;
					result = String.format("-%X", op1 / op2);
				} else if (op2 < 0) {
					op2 = -op2;
					result = String.format("-%X", op1 / op2);
				} else 
					result = String.format("%X", op1 / op2);
				break;
			case "+":
				result = String.format("%X", op1 + op2);
				break;
			case "-":
				result = String.format("%X", op1 - op2);
				break;
			default:
				this.setErrorMode(true);
				return "Illegal Operator";
			} 
		} else { // float result
			

			float op1 = 0, op2 = 0;
			
			// make sure the math can be done 
			if (this.operand2.equals("0") && this.arithmetic.equals("/")) {
				this.setErrorMode(true);
				return "Cannot divide by Zero";
			}
			
			// try to convert String to int
			try {
				op1 = Float.valueOf(this.operand1);
				op2 = Float.valueOf(this.operand2);
			} catch (Exception e) {
				this.setErrorMode(true);
				return "Operators not defined";
			}
			
			// return .00 
			if (precision == 1) {
				
				switch(this.arithmetic) {
				case "*":
					result = String.format("%.2f", op1 * op2);
					break;
				case "/":
					result = String.format("%.2f", op1 / op2);
					break;
				case "+":
					result = String.format("%.2f", op1 + op2);
					break;
				case "-":
					result = String.format("%.2f", op1 - op2);
					break;
				default:
					this.setErrorMode(true);
					return "Illegal Operator";
				}
			} else if (precision == 0) { // return .0 
				switch(this.arithmetic) {
				case "*":
					result = String.format("%.1f", op1 * op2);
					break;
				case "/":
					result = String.format("%.1f", op1 / op2);
					break;
				case "+":
					result = String.format("%.1f", op1 + op2);
					break;
				case "-":
					result = String.format("%.1f", op1 - op2);
					break;
				default:
					this.setErrorMode(true);
					return "Illegal Operator";
				}
			} else { // return in scientific format
				switch(this.arithmetic) {
				case "*":
					result = String.format("%E", op1 * op2);
					break;
				case "/":
					result = String.format("%E", op1 / op2);
					break;
				case "+":
					result = String.format("%E", op1 + op2);
					break;
				case "-":
					result = String.format("%E", op1 - op2);
					break;
				default:
					this.setErrorMode(true);
					return "Illegal Operator";
				}
			}
		}
		
		// return the result
		return result;
	}
}
