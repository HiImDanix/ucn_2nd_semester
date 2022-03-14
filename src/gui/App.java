package gui;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;


/**
 * @author Daniels Kanepe
 *
 */
public class App {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// Set FlatLaf 'Look And Feel' 
		try {
		    UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}
		

		// TODO: Generate some default data
		//new GenerateDataController().generateData();
		
		// TODO: Add default employee
		
		// Show login window
		EventQueue.invokeLater(() -> {
			try {
				Login frame = new Login("email@example.com", "password");
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
}
