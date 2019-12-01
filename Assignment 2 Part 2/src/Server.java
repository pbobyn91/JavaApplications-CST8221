import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class Server {
	
	private static final int DEFAULT_PORT = 65535;	// static default port

	public static void main(String [] args) {
		
		// local variables
		int port;						// port connection
		int friend = 0;					// local friend 
		ServerSocket server = null;		// TCP/IP socket
				
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
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
					System.out.printf("Connected to client socket: Socket - addr: %s port: %d localport: %d\n", socket.getInetAddress(), socket.getPort(), socket.getLocalAddress());
					
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
	
	public static void launchClient(Socket socket, String title) {
		
		// launches the server client
		JFrame chat = new ServerChatUI(socket);
		chat.setTitle(title);
		chat.pack();
		chat.setVisible(true);
		chat.setLocationRelativeTo(null);
	}
}
