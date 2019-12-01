import java.awt.Dimension;
import java.net.Socket;

public class Server {

	public static void main(String [] args) {
		// calls the launchClient method to launch a server
		launchClient(null, "Patrick's Friend ServerChatUI");
	}
	
	public static void launchClient(Socket socket, String title) {
		
		// launches the server client
		ServerChatUI chat = new ServerChatUI(null);
		chat.setPreferredSize(new Dimension(588, 500));
		chat.setTitle(title);
		chat.setVisible(true);
		chat.setLocationRelativeTo(null);
	}
}
