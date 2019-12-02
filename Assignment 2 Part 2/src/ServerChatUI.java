/*
 * File Name: ServerChatUI.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: This creates the GUI for the Server chat side. It also accepts the connection and allows messages to be sent.
 * Class List: ServerChatUI.java, WindowController.java, Controller.java
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * @author Pat
 * @version 1.0
 * @see WindowController, Controller
 * @since 1.8.0_144
 */
public class ServerChatUI extends JFrame implements Accessible {

	/**
	 * message The message to be sent back to the client
	 */
	private JTextField message;
	
	/**
	 * sendButton The sendButton to send the message 
	 */
	private JButton sendButton;
	
	/**
	 * display The display of the whole conversation
	 */
	private JTextArea display;
	
	/**
	 * scroll the Scroll Pane used to scroll the display section
	 */
	private JScrollPane scroll;
	
	/**
	 * outputStream The output from the message 
	 */
	private ObjectOutputStream outputStream;
	
	/**
	 * socket The socket connection
	 */
	private Socket socket;
	
	/**
	 * connection The instance of the ConnectionWrapper
	 */
	private ConnectionWrapper connection;
	
	/**
	 * windowController An Instance of the Window Controller
	 */
	private WindowController windowController = new WindowController();
	
	/**
	 * controller The controller that reads the input
	 */
	private Controller controller = new Controller();
	
	/**
	 * The constructor
	 * @param socket the socket used for the connection
	 */
	public ServerChatUI(Socket socket) {
		// constructor for the server chat
		this.socket = socket;
		setFrame(createUI());
		runClient();
	}
	
	/**
	 * The method that creates the GUI
	 * @return Returns a JPanel
	 */
	public JPanel createUI () {
		
		// local variables
		JPanel panel = new JPanel(new BorderLayout());	// creates a panel to be returned
		JPanel messagePanel = new JPanel(new BorderLayout());	// the message panel
		JPanel messageInner = new JPanel(new BorderLayout());	// the messageInner panel
		JPanel displayPanel = new JPanel(new BorderLayout());	// the display panel
		
		// add panels to the send panel
		message = new JTextField("Type Message");
		message.requestFocus();
		sendButton = new JButton("Send");
		sendButton.setMnemonic('S');
		sendButton.setEnabled(true);
		sendButton.setActionCommand("S");
		sendButton.addActionListener(controller);
				
		// add the panels to the messageInner panel
		messageInner.add(message);
		messageInner.add(sendButton, BorderLayout.EAST);
		messageInner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		// adds this to the message panel
		messagePanel.add(messageInner);
				
		// set the send panel
		messagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));
				
		// add the history label to the display panel
		display = new JTextArea();
		display.setRows(30);
		display.setColumns(45);
		display.setAlignmentX(Component.LEFT_ALIGNMENT);
		display.setEditable(false);
		scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		displayPanel.add(scroll);
						
		// set the display panel
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 10), "CHAT DISPLAY", TitledBorder.CENTER, TitledBorder.TOP));
				
		// Add panels to the whole frame 
		panel.add(messagePanel, BorderLayout.NORTH);
		panel.add(displayPanel);
				
		return panel;
	}
	
	/** 
	 * The overriden getDisplay method from the Accessible class
	 */
	@Override
	public JTextArea getDisplay() {
		
		// return display
		return this.display;
	}
	
	/**
	 * the overriden clostChat method from the Accessible class
	 */
	@Override
	public void closeChat() {
		
		// tries to close the connection and then disposes of the frame
		try {
			connection.closeConnection();
			windowController.windowClosed();
			ServerChatUI.this.dispose();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * A method that sets the frame 
	 * @param panel The panel that is used for the frame
	 */
	public final void setFrame(JPanel panel) {
		
		// adds the panel passed to it and creates the frame
		this.setContentPane(panel);
		setMinimumSize(new Dimension(588, 500));
		setResizable(false);
		this.addWindowListener(windowController);
	}
	
	/** 
	 * the method that runs the client
	 */
	private void runClient() {
		
		// initialize the connection
		connection = new ConnectionWrapper(this.socket);
		
		// uses the connection to create streams and initialize the output stream
		try {
			connection.createStreams();
			outputStream = connection.getOutputStream();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		// create object of type Runnable using ChatRunnable
		Runnable runnable = new ChatRunnable(this, connection);
		
		// creates a Thread passing the runnable reference and starts the thread
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	/**
	 * @author Pat
	 * @version 1.0
	 * @since 1.8.0_144
	 */
	private class WindowController extends WindowAdapter {
		
		/**
		 * the method to close the window
		 */
		public void windowClosing() {
			
			Boolean writingError = false;
			Boolean closingError = false;
			
			// prints Server UI Closing
			System.out.println("Server UI Closing!");
			
			// using the outputStream writes string
			try {
				outputStream.writeObject(ChatProtocolConstants.DISPLACEMENT + ChatProtocolConstants.CHAT_TERMINATOR + ChatProtocolConstants.LINE_TERMINATOR);
			} catch (IOException e) {
				writingError = true;
			} finally {	// if exception occurs dispose frame
				if (writingError)
					ServerChatUI.this.dispose();
			}
			
			System.out.println("Closing Chat!");
			// using the connection tries to close the connection
			// if exception occurs disposes the  frame
			try {
				connection.closeConnection();
			} catch (IOException ioe) {
				closingError = true;
			} finally {
				if (closingError)
					ServerChatUI.this.dispose();
			}
			
			// disposes the frame and prints a message
			ServerChatUI.this.dispose();
			System.out.println("Chat Closed!");
		}
		
		/** 
		 * the method that closes the server side chat
		 */
		public void windowClosed() {
			System.out.println("Server UI Closed");
		}
	}
	
	/**
	 * @author Pat
	 * @version 1.0
	 * @since 1.8.0_144
	 */
	private class Controller implements ActionListener {
		
		/** 
		 * the overriden actionPerformed method
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getActionCommand() == "S") {
				send();
			}
		}
		
		/**
		 * the method that sends the user written message
		 */
		private void send() {
			
			// local variable sendMessage gets text from message text field
			String sendMessage = message.getText();
			
			// appends sendMessage and LINE_TERMINATOR to the output stream
			display.append(sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
			
			// uses outputStream to write sendMessage 
			// if run-time error occurs, display errors on the chat display text area
			try {
				outputStream.writeObject(ChatProtocolConstants.DISPLACEMENT + sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
			} catch (IOException ioe) {
				display.append(ioe.getMessage() + ChatProtocolConstants.LINE_TERMINATOR);
			}
		}
	}
}
