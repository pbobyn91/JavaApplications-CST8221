/*
 * File Name: ChatRunnable.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: Creates the Runnable part of the connection.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 
 * @author Pat
 * @version 1.0
 * @since 1.4.0_144
 * @param <T> is an extension of JFrame and Accessible
 */
public class ChatRunnable <T extends JFrame & Accessible> implements Runnable {

	/**
	 * The instance of T, which is JFrame and Accessible
	 */
	private final T ui;
	
	/**
	 * The Socket used for the connection
	 */
	private final Socket socket;
	
	/**
	 * the input Stream 
	 */
	private final ObjectInputStream inputStream;
	
	/**
	 * The Output stream
	 */
	private final ObjectOutputStream outputStream;
	
	/**
	 * The display
	 */
	private final JTextArea display;
	
	/**
	 * The constructor
	 * @param ui An object T used to instantiate other variables
	 * @param connection The connection used to instantiate other variables
	 */
	public ChatRunnable (T ui, ConnectionWrapper connection) {
		this.ui = ui;
		this.outputStream = connection.getOutputStream();
		this.inputStream = connection.getInputStream();
		this.socket = connection.getSocket();
		this.display = ui.getDisplay();
	}
	
	/**
	 * The method that connects both the server and the client applications
	 */
	@Override
	public void run() {
		
		String strin = "";
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM dd, HH:mm a");
		
		while (true) {
			
			if (socket.isClosed())
				break;
			
			try {
				strin = (String) inputStream.readObject();
				
				if (strin.trim().equals(ChatProtocolConstants.CHAT_TERMINATOR)) {
					final String terminate = ChatProtocolConstants.DISPLACEMENT + time.format(dtf) 
							+ ChatProtocolConstants.LINE_TERMINATOR + strin;
					
					display.append(terminate);
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Connection Ended");
				break;
			} catch (Exception e) {
				System.out.println("Connection Ended");
			}
			
			final String str = ChatProtocolConstants.DISPLACEMENT + time.format(dtf) 
				+ ChatProtocolConstants.LINE_TERMINATOR + strin;
			display.append(str);
		}
		
		if (!socket.isClosed()) {
			String str = ChatProtocolConstants.DISPLACEMENT + ChatProtocolConstants.CHAT_TERMINATOR 
					+ ChatProtocolConstants.LINE_TERMINATOR;
			
			try {
				outputStream.writeObject(str);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		ui.closeChat();
	}
}
