import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ServerChatUI extends JFrame {

	private JTextField text; // text for the message field 
	private JButton send;	// the button to send the message
	private JTextArea history;	// the history of the whole conversation
	private JScrollPane scroll;	// a scroll pane 
	
	public ServerChatUI(Socket socket) {
		// constructor for the server chat
		socket = new Socket();
		setFrame(createUI());
		runClient();
	}
	
	public JPanel createUI () {
		
		// local variables
		JPanel panel = new JPanel(new BorderLayout());	// creates a panel to be returned
		JPanel message = new JPanel(new BorderLayout());	// the message panel
		JPanel messageInner = new JPanel(new BorderLayout());	// the messageInner panel
		JPanel display = new JPanel(new BorderLayout());	// the display panel
		
		Controller controller = new Controller();	// the controller 
		
		// add panels to the send panel
		text = new JTextField("Type Message");
		text.requestFocus();
		send = new JButton("Send");
		send.setMnemonic('S');
		send.setEnabled(true);
		send.addActionListener(controller);
				
		// add the panels to the messageInner panel
		messageInner.add(text);
		messageInner.add(send, BorderLayout.EAST);
		messageInner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		// adds this to the message panel
		message.add(messageInner);
				
		// set the send panel
		message.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));
				
		// add the history label to the display panel
		history = new JTextArea();
		history.setRows(30);
		history.setColumns(45);
		history.setAlignmentX(Component.LEFT_ALIGNMENT);
		history.setEditable(false);
		scroll = new JScrollPane(history);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		display.add(scroll);
						
		// set the display panel
		display.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 10), "CHAT DISPLAY", TitledBorder.CENTER, TitledBorder.TOP));
				
		// Add panels to the whole frame 
		panel.add(message, BorderLayout.NORTH);
		panel.add(display);
				
		return panel;
	}
	
	public final void setFrame(JPanel panel) {
		
		// adds the panel passed to it and creates the frame
		add(panel);
		setMinimumSize(new Dimension(588, 500));
		setResizable(false);
		setVisible(true);
	}
	
	private void runClient() {
		
	}
	
	private class WindowController extends WindowAdapter {
		
		public void windowClosing() {
			System.exit(0);
		}
	}
	
	private class Controller implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
		}
	}
}
