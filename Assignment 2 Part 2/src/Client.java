import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Client {

	public static void main (String [] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				// set the ui as the frame
				JFrame frame = new ClientChatUI ("Patrick's ClientChatUI");
				
				frame.setLocationByPlatform(true);
				frame.setMinimumSize(new Dimension(588, 500));
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
