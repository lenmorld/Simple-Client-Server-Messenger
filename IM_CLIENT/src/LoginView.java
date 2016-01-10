
import javax.swing.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SuppressWarnings("serial")
public class LoginView extends JFrame {
	
	//static String servIP = "localhost";
	static String servIP = "192.168.1.117:3306";
	
	private JTextField userText;
	
	private JPasswordField passwordText;
	private String username;

	public LoginView(Client c) {
		
		super("Login to Server-Messenger");
		
		setSize(300,150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 JPanel panel = new JPanel();
		 
		 panel.setLayout(null);
		 
		 JLabel userLabel = new JLabel("User");
			userLabel.setBounds(10, 10, 80, 25);
			panel.add(userLabel);

			userText = new JTextField(20);
			userText.setBounds(100, 10, 160, 25);
			panel.add(userText);

			JLabel passwordLabel = new JLabel("Password");
			passwordLabel.setBounds(10, 40, 80, 25);
			panel.add(passwordLabel);

			 passwordText = new JPasswordField(20);
			passwordText.setBounds(100, 40, 160, 25);
			panel.add(passwordText);

			JButton loginButton = new JButton("login");
			loginButton.setBounds(10, 80, 80, 25);
			
			loginButton.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//check credentials through DB connection
							
							if (verify())
							{	c.setClientName(username);
								c.setVisible(true);
								close();
							}
							
						}
					});
			
			panel.add(loginButton);
			
			JButton exitButton = new JButton("exit");
			exitButton.setBounds(180, 80, 80, 25);
			panel.add(exitButton);
			
			add(panel);
		
	}

	public boolean verify() {
	       try{
	       
	           Class.forName("com.mysql.jdbc.Driver");

	           java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://" + servIP  + "/chat", "lenny", "abc123");
 
	           //java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://" + servIP + "/chat?user=lenny&password=abc123&useUnicode=true&characterEncoding=UTF-8");

	           Statement  st  = con.createStatement(); 
	           
	           //SQL query
	           String query = "SELECT * FROM users "
	                   + "WHERE username = '" + userText.getText().trim()  + "'" + 
	                   " AND password = '" + String.valueOf(passwordText.getPassword())  + "';";
	           
	           //password.getPassword() returns a char[]
	           
	             //System.out.println(query);
	           
	           //process result
	           ResultSet rs = st.executeQuery(query);
	           if (!rs.next()) {
	               System.out.println("No results found");
	               JOptionPane.showMessageDialog(null, "Account not found. Please try again.");
	               
	               
	           }
	           else
	           {
	               JOptionPane.showMessageDialog(null, "Welcome " + rs.getString("username"));
	               username = rs.getString("username");
	               con.close();
	               return true;
	             
	           }

	           //close connection
	           con.close();
	           }
	           catch(Exception e){
	               e.printStackTrace();
	           }
	           //*************************************
	       
	       	return false;		//not authenticated
	}
	
	public void close() {
		this.dispose();
	}
	
	
}
