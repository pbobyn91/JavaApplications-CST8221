import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

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

public class ClientChatUI extends JFrame {
	
	private JTextField hostText;	// text field for the hostname to connect to 
	private JComboBox<String> combo;	// the combo box for the connection section, selects the port
	private JButton connect;	// the connect button
	private JTextField text;	// the text field for the message section
	private JTextArea history;	// the history of the chat for the display section
	private JButton send;	// the send button for sending the message

	public ClientChatUI(String title) {
		setTitle(title);
		runClient();
	}
	
	private void runClient() {
		setContentPane(createClientUI());
		addWindowListener(new WindowController());
	}
	
	public JPanel createClientUI () {
		
		// Create Variables
		JPanel panel = new JPanel(new BorderLayout());
		Controller controller = new Controller();
		JPanel connectionMessage = new JPanel(new BorderLayout());
		
		// Connection Panel Variables
		JPanel connection = new JPanel(new BorderLayout());
		JLabel host = new JLabel("Host: ");
		JLabel port = new JLabel("Port: ");
		JPanel connectionNorth = new JPanel(new BorderLayout());
		JPanel connectionSouth = new JPanel(new FlowLayout(FlowLayout.LEFT));
		String ports[] = {"", "8089", "65000", "65535"};
		
		// Display Panel Variables
		JPanel display = new JPanel(new BorderLayout());
		JScrollPane scroll;
		
		// Message Panel Variables
		JPanel message = new JPanel(new BorderLayout());
		JPanel messageInner = new JPanel(new BorderLayout());
		
		// Set host label
		host.setPreferredSize(new Dimension(40,20));
		host.setDisplayedMnemonic('H');
		hostText = new JTextField("localhost");
		hostText.setPreferredSize(new Dimension(100,20));
		hostText.requestFocus();
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
		text = new JTextField("Type Message");
		send = new JButton("Send");
		send.setMnemonic('S');
		send.setEnabled(false);
		send.addActionListener(controller);
		
		// add all the panels to the message panel
		messageInner.add(text);
		messageInner.add(send, BorderLayout.EAST);
		messageInner.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		message.add(messageInner);
		
		// set the send panel
		message.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));
		
		// add all panels to the connectionMessage panel
		connectionMessage.add(connection);
		connectionMessage.add(message, BorderLayout.SOUTH);
		
		// add the history label to the display panel
		history = new JTextArea("");
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
		panel.add(connectionMessage, BorderLayout.NORTH);
		panel.add(display);
		
		return panel;
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
