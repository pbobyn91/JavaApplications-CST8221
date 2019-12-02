/*
 * File Name: Server.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: the main method for the server GUI.
 */

import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * @author Pat
 * @version 1.0
 * @since 1.8.0_144
 */
public class Server {
	
	/**
	 * DEFAULT_PORT the default port option to be used for this application 
	 */
	private static final int DEFAULT_PORT = 65535;

	/** 
	 * The main method
	 * @param args the default input parameters for main
	 */
	public static void main(String [] args) {
		
		// local variables
		int port;						// port connection
		int friend = 0;					// local friend 
		ServerSocket server = null;		// TCP/IP socket
				
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
			System.out.println("Port: " + port + " being used ");
		} else {
			System.out.println("Default Port: " + DEFAULT_PORT + " being used");
			port = DEFAULT_PORT;
		}
		
		try {	// create TCP/IP socket with port
			server = new ServerSocket(port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		while (true) {
			
			// call accept on server socket instance
			try {
				Socket socket = server.accept();
				
				if (socket != null) {
					
					if (socket.getSoLinger() !=  -1) 
						socket.setSoLinger(true, 5);
					if (!socket.getTcpNoDelay()) 
						socket.setTcpNoDelay(true);
					
					// print socket information
					System.out.printf("Connected to client socket: Socket - addr: %s port: %d localport: %d\n", socket.getInetAddress(), socket.getPort(), socket.getLocalPort());
					
					// increment friend
					friend++;
					
					// final string with name
					final String title = "Patrick's Friend " + friend;
					
					// launch client with socket and title
					launchClient(socket, title);
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}
	
	/**
	 * the method to launch the server side application
	 * @param socket The socket that connects ther server and the client
	 * @param title The title given to this frame
	 */
	public static void launchClient(Socket socket, String title) {
		
		// launches the server client
		JFrame chat = new ServerChatUI(socket);
		chat.setTitle(title);
		chat.pack();
		chat.setVisible(true);
		chat.setLocationRelativeTo(null);
		chat.setSize(588, 500);
	}
}
