/*
 * File Name: ConnectionWrapper.java
 * Author: Patrick Bobyn, 040889706 
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 2
 * Date: Dec 6th, 2019
 * Professor: Svillan Ranev
 * Purpose: A wrapper class for the Connection, has the input and output streams.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Pat
 * @version 1.0
 * @since 1.8.0_144
 */
public class ConnectionWrapper {

	/**
	 * outputStream The output stream from the socket
	 */
	private ObjectOutputStream outputStream;
	
	/**
	 * inputStream The input stream from the socket
	 */
	private ObjectInputStream inputStream;
	
	/** 
	 * socket The socket used for the connection
	 */
	private Socket socket;
	
	/**
	 * The constructor
	 * @param socket The socket for the connection
	 */
	public ConnectionWrapper (Socket socket) {
		this.socket = socket;
	}
	
	/** 
	 * Gets the socket from this class
	 * @return Returns the socket
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Gets the ouput stream 
	 * @return Returns the output stream
	 */
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	/**
	 * Gets the input stream
	 * @return Returns the input stream
	 */
	public ObjectInputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * Creates the class Input stream
	 * @return Returns the created input stream
	 * @throws IOException Thrown if it doesnt work
	 */
	public ObjectInputStream createObjectIStreams() throws IOException {
		
		inputStream = new ObjectInputStream(socket.getInputStream());
		return inputStream;
	}
	
	/**
	 * Creates the class output stream
	 * @return Returns the created output stream
	 * @throws IOException Throws an IOException if this doesnt work
	 */
	public ObjectOutputStream createObjectOStreams() throws IOException {
		
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		return outputStream;
	}
	
	/** 
	 * Creates the 2 streams
	 * @throws IOException Throws an Exception if it doesnt work
	 */
	public void createStreams() throws IOException {
		
		outputStream = createObjectOStreams();
		inputStream = createObjectIStreams();
	}
	
	/**
	 * Closes the connection
	 * @throws IOException Throws an IOException it cant close correctly 
	 */
	public void closeConnection() throws IOException {
		
		if (socket != null || !socket.isClosed())
			socket.close();
		
		if (outputStream != null)
			outputStream.close();
		
		if (inputStream != null)
			inputStream.close();
	}
}
