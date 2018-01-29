package phoneticinventory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


/**
 * The main class for the Phonetic Inventory application. Starts up the GUI and sets the window size.
 * 
 * @author Kim Steffens
 */
public class PhoneticInventory {
 
	
	/**
	 * The main method creates the QueryPanel and the application window.
	 * 
	 * @param args Not used.
	 */
	public static void main (String [] args){
	
		JFrame frame = new JFrame("Phonetic Inventory Database");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		QueryPanel panel = new QueryPanel();
	
		frame.getContentPane().add(panel);
		
		// make fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.setLayout(new GridBagLayout());
		
		frame.pack();
	
		frame.setVisible(true);
		
	}
}
