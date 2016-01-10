//run server side of IM app

import javax.swing.*;

public class ServerTest {
	
	public static void main(String[] args) {
		Server serv1 = new Server();		// constr - GUI
		serv1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serv1.startRunning();		// start execution
		
	}

}
