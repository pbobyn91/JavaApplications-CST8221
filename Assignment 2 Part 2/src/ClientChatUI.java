/*
 * File Name: ClientChatUI.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: This is the GUI for the Client Chat system. It also sets up the connection to the server and acts as a chat mmessage.
 * Class List: ClientChatUI.java, WindowController.java, Controller.java
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * @author Pat
 * @verion 1.0
 * @see WindowController, Controller
 * @since 1.8.0_144
 */

public class ClientChatUI extends JFrame implements Accessible {
	
	/** 
	 * hostText A JTextField that is used to get a location to connect to.
	 */
	private JTextField hostText;
	
	/**
	 * combo The combo box option to hold the port values 
	 */
	private JComboBox<String> combo;
	
	/**
	 * connect The button to allow the connection
	 */
	private JButton connect;
	
	/**
	 * message The JTextField to allow the user to enter a message 
	 */
	private JTextField message;
	
	/** 
	 * display The display for the whole chat
	 */
	private JTextArea display;
	
	/** 
	 * sendButton The button that allows a message to be sent.
	 */
	private JButton sendButton;
	
	/**
	 * outputStream The output.
	 */
	private ObjectOutputStream outputStream;
	
	/**
	 * socket The socket to connect Client to server
	 */
	private Socket socket;
	
	/** 
	 * connection The ConnectionWrapper 
	 */
	private ConnectionWrapper connection;
	
	/** 
	 * controller An instance of the controller class
	 */
	private Controller controller = new Controller();
	
	/** 
	 * ports The port options to be used 
	 */
	private String ports[] = {"", "8089", "65000", "65535"};
	

	/**
	 * The Constructor for the ClienChatUI class.
	 * @param title The title of the client side chat
	 */
	public ClientChatUI(String title) {
		super.setTitle(title);
		runClient();
	}
	
	/**
	 * the Overriden getDisplay method from the Accessible interface.
	 */
	@Override
	public JTextArea getDisplay() {
		// return the display
		return this.display;
	}
	
	/**
	 * the Overriden closeChat method from the Accessible interface.
	 */
	@Override
	public void closeChat() {
		// if the socket is not closed, tries to close the connection
		// then calls enableConnectButton()
		try {
			connection.closeConnection();
			enableConnectButton();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 *  A method to run the client
	 */
	private void runClient() {
		setContentPane(createClientUI());
		addWindowListener(new WindowController());
	}
	
	/**
	 * The method creates the appearance of the GUI
	 * @return Returns a JPanel to be inputted into the frame
	 */
	public JPanel createClientUI () {
		
		// Create Variables
		JPanel panel = new JPanel(new BorderLayout());
		JPanel connectionMessage = new JPanel(new BorderLayout());
		
		// Connection Panel Variables
		JPanel connection = new JPanel(new BorderLayout());
		JLabel host = new JLabel("Host: ");
		JLabel port = new JLabel("Port: ");
		JPanel connectionNorth = new JPanel(new BorderLayout());
		JPanel connectionSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		// Display Panel Variables
		JPanel displayPanel = new JPanel(new BorderLayout());
		JScrollPane scroll;
		
		// Message Panel Variables
		JPanel messagePanel = new JPanel(new BorderLayout());
		JPanel messageInner = new JPanel(new BorderLayout());
		
		// Set host label
		host.setPreferredSize(new Dimension(40,20));
		host.setDisplayedMnemonic('H');
		hostText = new JTextField("localhost");
		hostText.setPreferredSize(new Dimension(100,20));
		hostText.requestFocusInWindow();
		hostText.setMargin(new Insets(0,5,0,0));
		host.setLabelFor(hostText);
		
		// set border and add panels to the north connection panel
		connectionNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		connectionNorth.add(host, BorderLayout.WEST);
		connectionNorth.add(hostText, BorderLayout.CENTER);
		
		// set the connection panel south displays 
		port.setPreferredSize(new Dimension(35, 30));
		port.setDisplayedMnemonic('P');
		combo = new JComboBox<String>(ports);
		combo.setEditable(true);
		combo.setBackground(Color.WHITE);
		combo.setPreferredSize(new Dimension(100, 20));
		connect = new JButton("Connect");
		connect.setPreferredSize(new Dimension(100, 20));
		connect.setMnemonic('C');
		connect.setBackground(Color.RED);
		connect.setActionCommand("C");
		connect.addActionListener(controller);
		port.setLabelFor(combo);
		
		// add the objects to the panel
		connectionSouth.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		connectionSouth.add(port, BorderLayout.WEST);
		connectionSouth.add(combo, BorderLayout.CENTER);
		connectionSouth.add(connect, BorderLayout.EAST);
		
		// set the connection panel
		connection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 10), "CONNECTION"));
		connection.add(connectionNorth, BorderLayout.NORTH);
		connection.add(connectionSouth, BorderLayout.SOUTH);
		
		// add panels to the send panel
		message = new JTextField("Type Message");
		sendButton = new JButton("Send");
		sendButton.setMnemonic('S');
		sendButton.setEnabled(false);
		sendButton.setActionCommand("S");
		sendButton.addActionListener(controller);
		
		// add all the panels to the message panel
		messageInner.add(message);
		messageInner.add(sendButton, BorderLayout.EAST);
		messageInner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		messagePanel.add(messageInner);
		
		// set the send panel
		messagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));
		
		// add all panels to the connectionMessage panel
		connectionMessage.add(connection);
		connectionMessage.add(messagePanel, BorderLayout.SOUTH);
		
		// add the history label to the display panel
		display = new JTextArea("");
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
		panel.add(connectionMessage, BorderLayout.NORTH);
		panel.add(displayPanel);
		
		return panel;
	}
	
	/**
	 * A method to enable the connect button, for after a connection has been made
	 */
	private void enableConnectButton() {
		// enables the connect button, sets background color to red
		connect.setEnabled(true);
		connect.setBackground(Color.RED);
		// disables the send button
		sendButton.setEnabled(false);
		// host text field requests focus
		hostText.setRequestFocusEnabled(true);
	}
	
	/**
	 * @author Pat
	 * @version 1.0
	 * @since 1.8.0_144
	 */
	private class WindowController extends WindowAdapter {
		
		/**
		 * the option to close the window
		 */
		public void windowClosing() {
			
			// tries to output message, if it fails then exits
			try {
				outputStream.writeObject(ChatProtocolConstants.CHAT_TERMINATOR);
			} catch (IOException ioe) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * @author Pat
	 * @version 1.0
	 * @since 1.8.0_144
	 */
	private class Controller implements ActionListener {
		
		/**
		 * The overriden version of the actionPerformed method
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			
			// boolean called connected set to false
			Boolean connected = false;
			String host;
			int port;
			
			// if connect button has been clicked declare variable host and assigns it to the string displayed in host text field and gets the port
			if (event.getActionCommand() == "C") {
				 host = hostText.getText();
				 port = Integer.parseInt(Objects.requireNonNull(combo.getSelectedItem()).toString());
				 
				 // call connect() and assign it to connected
				 connected = connect(host, port);
				 
				 // if connected is true, disable connect button, makes background of connect blue, enables send button and requests focus to message 
				 if (connected) {
					 connect.setEnabled(false);
					 connect.setBackground(Color.BLUE);
					 sendButton.setEnabled(true);
					 message.setRequestFocusEnabled(true);
					 
					 // creates object of Runnable using ChatRunnable, then creates thread passing runnable as reference, starts the thread
					 Runnable runnable = new ChatRunnable(ClientChatUI.this, connection);
					 Thread thread = new Thread(runnable);
					 thread.start();
					 
				 } else {	// if connected is false return;
					 return;
				 }
			} else if (event.getActionCommand()== "S") {
				send();
			}
		}
		
		/**
		 * The connect method that is used to connect to the server side 
		 * @param host The host variable to be used 
		 * @param port The port to connect through
		 * @return Returns a Boolean
		 */
		private Boolean connect (String host, int port) {
			
			try {
				// method tries to create a time-out socket, if the socket is successful it assigns the instance to the socket class field 
				socket = new Socket();
				// 5 second timeout
				int timeout = 5000;
				socket.connect(new InetSocketAddress(InetAddress.getByName(host), port), timeout);
				
				// set the socket
				if (socket.getSoLinger() != -1) socket.setSoLinger(true, 5);
				if (!socket.getTcpNoDelay()) socket.setTcpNoDelay(true);
				
				display.append("Connecting to client socket " + socket.getInetAddress()+ ", port " + socket.getPort() + "\n");
				
				// initializes connection, create streams, set outputStream
				connection = new ConnectionWrapper(socket);
				connection.createStreams();
				outputStream = connection.getOutputStream();
				
				return true;
				
			} catch (UnknownHostException uhe) {
				display.append("Error: Unknown Host" + ChatProtocolConstants.LINE_TERMINATOR);
			} catch (IOException e) {
				display.append("ERROR: Connection Refused" + ChatProtocolConstants.LINE_TERMINATOR);
			}
			
			return false;
		}
		
		/**
		 * The send method used to send a message 
		 */
		private void send() {
			
			// gets text from message field and assigns it to sendMessage
			String sendMessage = message.getText();
			
			// appends it to display and add the line terminator
			display.append(sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
			
			// uses output stream to write an object
			try {
				outputStream.writeObject(ChatProtocolConstants.DISPLACEMENT + sendMessage + ChatProtocolConstants.LINE_TERMINATOR);
			} catch (IOException ioe) {		// if error occurs call enableConnectButton and then display error
				enableConnectButton();
				display.append(ioe.getMessage() + ChatProtocolConstants.LINE_TERMINATOR);
			}
		}
	}
}
