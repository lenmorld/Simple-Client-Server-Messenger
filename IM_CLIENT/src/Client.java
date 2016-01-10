//client side - GUI and connection stuff
//diff. project but will be in a diff. PC
//1 on server, 1 on client

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame {
	
	
	private JTextField userText;
	private JTextArea chatWindow;
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String msg = "";
	private String serverIP;	//server IP
	
	//notice no Server object
	
	private Socket socketConnection;
	
	private String clientName;
	
	
	// public can't connect to the client's PC
	
	//constructor and GUI
	public Client(String host) {	
		super("Client Chat");
		
		serverIP = host;	// init serverIP to passed host, WE USE THE SAME COMPUTER FOR BOTH SERVER AND CLIENT
		//if not, input the actual server IP here
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//CENTER
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		
		//SOUTH
		
		userText = new JTextField();
		userText.setEditable(false);	// disable typing until connected
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand()); // get String and send
						userText.setText("");		// reset to blank
					}
				}
				);
		add(userText, BorderLayout.SOUTH);
		
		
		setSize(500, 500);
		
		
		LoginView login = new LoginView(this);
		login.setVisible(true);
		
		
	
		
		//setVisible(true);
	}
	
	//connect to server
	public void startRunning() {
		try {
			//ON SERVER, we wait for connection,
			// but here the client has to connect to server
			connectToServer();			//CLIENT counterpart of waitForConnection
			setupStreams();
			whileChatting();
		}catch(EOFException eof) {	// client doesn't want to talk anymore
			showMessage("\nClient Terminated connection");
		}catch(IOException ioe) {	// something went horribly wrong
			ioe.printStackTrace();
		}finally {	// maintenance - closing streams
			closeChat();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException {
		showMessage("Attempting connection... \n");
		//create a socket - IP and port number
		//connect to server IP and port - 6789
		socketConnection = new Socket(InetAddress.getByName(serverIP), 6789);
		//display connection info
		showMessage("Connected to: " + socketConnection.getInetAddress().getHostName()); 	//get server IP
		
	}
	
	
	//setup streams to send and receive messages
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(socketConnection.getOutputStream());  //get output stream - client to server
		output.flush();	//flush all the crap through the pipes
		input = new ObjectInputStream(socketConnection.getInputStream());
		showMessage("\n STREAMS ARE setup \n");
	}
	
	//while chatting with server
	private void whileChatting() throws IOException {
		ableToType(true);
		
		do {
			
			try{
				msg = (String) input.readObject();
				showMessage("\n" + msg );
			}catch(ClassNotFoundException cnfe) {
				showMessage("\n Unknown format");
			}
			
		}while(!msg.equals("SERVER - END"));
	}
	
	
	//close streams safely
	private void closeChat() {
		showMessage("\n Closing chat...");
		ableToType(false);
		try {
			output.close();
			input.close();
			
			socketConnection.close();
			
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	//send message to server
	private void sendMessage(String s) {
		try {
			//output.writeObject("CLIENT - " + s);
			output.writeObject(clientName + " - " + s);
			output.flush();
			//showMessage("\nCLIENT - " + s);
			showMessage("\n" + clientName + " - " + s);
		}
		catch(IOException ioe) {
			chatWindow.append("\nMESSAGE NOT SENT");
		}
	}

	//show message - updates the chat window through a THREAD
	private void showMessage(final String msg) {
		SwingUtilities.invokeLater(
				new Runnable() {		// create thread
					public void run() {
						chatWindow.append(msg);  //add this string to the end 
					}
				}
				);
	
	}
	
	//ableToType - enables/disable text field for user
	
	private void ableToType(final boolean tof) {
		
		SwingUtilities.invokeLater(
				new Runnable() {		// create thread
					public void run() {
						userText.setEditable(tof);
					}
				}
				
				);
	}
	
	public void setClientName(String s) {
		clientName = s;
	}
	
	public String getClientName() {
		return clientName;
	}
	


}
