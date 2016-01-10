import javax.swing.JFrame;

// Server Program sits on a Public PC
//CLient Program - TURN INTO JAR and Distribute to clients


public class ClientTest {
	

	public static void main(String[] args) {

		//create client object
		
		//we have to give IP address
		//TO TEST THIS ON YOUR PC - use localhost address
		//SET LOOPBACK ADDRESS(this PC) as Server IP to connect to
		
		//LOOPBACK
		//Client cli1 = new Client("127.0.0.1");	
		
		//SERVER on Another PC (preferably in the same network)
		//Client cli1 = new Client("172.16.10.104");
		
		//ask for IP
		//String servIP = JOptionPane.showInputDialog("Enter IP address of server (127.0.0.1 if this PC)");
		
	
		
		//String clientName = JOptionPane.showInputDialog("Enter nick name");
		
		//LoginView login = new LoginView();
		//login.setVisible(true);
		
		
		String servIP = "192.168.1.117";
		Client cli1 = new Client(servIP);
		
		
		cli1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cli1.startRunning(); 
	}

}
