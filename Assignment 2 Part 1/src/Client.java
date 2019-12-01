import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Client {

	public static void main (String [] args) {
		
		// create a ClientChatUI
		ClientChatUI ui = new ClientChatUI("Patrick's ClientChatUI");
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				// set the ui as the frame
				JFrame frame = ui;
				
				frame.setMinimumSize(new Dimension(588, 500));
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
			}
		});
	}
}
