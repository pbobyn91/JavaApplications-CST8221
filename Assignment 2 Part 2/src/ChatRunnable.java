import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChatRunnable <T extends JFrame & Accessible> implements Runnable {

	private final T ui;
	private final Socket socket;
	private final ObjectInputStream inputStream;
	private final ObjectOutputStream outputStream;
	private final JTextArea display;
	
	public ChatRunnable (T ui, ConnectionWrapper connection) {
		this.ui = ui;
		this.outputStream = connection.getOutputStream();
		this.inputStream = connection.getInputStream();
		this.socket = connection.getSocket();
		this.display = ui.getDisplay();
	}
	
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
				e.printStackTrace();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			final String str = ChatProtocolConstants.DISPLACEMENT + time.format(dtf) 
				+ ChatProtocolConstants.LINE_TERMINATOR + strin;
			display.append(str);
		}
		
		if (!socket.isClosed()) {
			String str = ChatProtocolConstants.DISPLACEMENT + ChatProtocolConstants.LINE_TERMINATOR 
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
