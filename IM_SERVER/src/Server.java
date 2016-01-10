//SIMPLE chat room

// SERVER waits for someone to connect, listening at given port
// streams, sockets client and server, data can be transmitted using streams
// once sockets are created on both

//SERVER 

//3 main things (inside startRunning() method)
// 1. wait for someone to connect
// 2. set up connections
// 3. runs the code that allows you to communicate

//CLIENT must connect, supplying the complete socket (IP address and port)
// associated with the server's

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {

	private JTextField userText;	//where you type message
	private JTextArea chatWindow;	// display the conversation
	private JLabel label1;
	//streams
	private ObjectOutputStream output;	//stream - binary output
	private ObjectInputStream input;	

	// program that goes on the server, waits for everyone to connect
	private ServerSocket server;
	
	// a connection bet computers 
	private Socket socketConnection;
	
	//constr - GUI
	public Server() {
		super("SERVER Messenger");
		
		

		//CENTER
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		add(new JScrollPane(chatWindow));		// window with scroll bar
		
		//SOUTH
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		userText = (new JTextField("",25));
		userText.setSize(400, 15);
		userText.setText("                 waiting for connection                  ");
		userText.setEditable(false); 	
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand()); // get String in the text field
						userText.setText(""); // empty it when after sending
					}
				}
				
				);
		
		label1 = new JLabel("Message:");
		label1.setSize(100, 15);
		p1.add(label1);
		p1.add(userText);
		
		add(p1, BorderLayout.SOUTH);		// add to window
		
		
		setSize(500,300);
		setVisible(true);
	}
	
	
	//set up and run the server	
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);	// port number 6789, 
													//max 100 people sit and wait on port
													//(backlog/ queue length - how many people can wait)
			
			while (true) {
				try {
					//1. wait for someone to connect
					waitForConnection();
					//2. set up streams (in and out)
					setupStreams();
					//3. allows to send messages back and forth
					whileChatting();	
				}
				catch(EOFException eof) {		// when finished, end of stream, end of connection
					//connect and have conversation
					showMessage("\n Server ended connection");
				}
				finally {
					closeChat();
				}
			}
			
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}	
	
	
	//1. wait for connection, then display connected info
	
	private void waitForConnection() throws IOException {
		showMessage("Waiting for someone to connect...\n");
		
		//do this over and over
		// only create socket when connected to someone
		socketConnection = server.accept();		// when someone asks to connect, accept
		showMessage("Now connected to " + socketConnection.getInetAddress().getCanonicalHostName());		// returns IP address or hostname	
	 
		userText.setText("");
		
		
	}
	//2. get stream to send and receive data
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(socketConnection.getOutputStream());	//out stream
		output.flush();	//flush leftover data to the other person
		input = new ObjectInputStream(socketConnection.getInputStream());	// in stream
		//only the other one can flush to you, no flush for input
		showMessage("\nStreams are now setup\n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException {
		String message = "You are now connected" ; 
		sendMessage(message);
		ableToType(true);	// let user type

		//loop as long as user doesn't type END, read message and display with a new line
		do {
			// have a conversation
			try {
				message = (String) input.readObject();		//get object from input stream
				showMessage("\n" + message);
			}
			catch(ClassNotFoundException cnfe) {	// some weird object that we don't understand (not a string)
				showMessage("\n IDK what the user sent");
			}
		}while(!message.equals("CLIENT - END"));	// if user types END, stop
	}
	
	//close connection
	private void closeChat() throws IOException {
		showMessage("\nClosing connection...\n");
		ableToType(false);	
		try {
			//close the streams
			output.close();
			input.close();
			socketConnection.close();	// close socket
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//send message to client
	private void sendMessage(String msg) {
		try{
			output.writeObject("SERVER - " + msg);	 //create an object and send to output stream
			output.flush();		//left over bytes flush to the user
			showMessage("\nSERVER - " + msg); 	// show conversation history in text area
		}
		catch(IOException ioe) {
			chatWindow.append("\n ERROR: Can't send the message");
		}
	}
	
	//displays chat messages, etc in chat window
	// updates chat window
	
	private void showMessage(final String text) {
		//update GUI component /dynamic
		
		// allows to create a thread that updates the GUI
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(text);	// adds a message to the end
					}
				}
		);	
	}	
	
	//let the user type stuff into their box
	private void ableToType(final boolean tof) {
		//like before, we're updating the GUI
		
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						userText.setEditable(tof);	// allow/disallow user to edit
					}
				}
		);	
	}
	
}
