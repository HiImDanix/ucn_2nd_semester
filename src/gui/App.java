package gui;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import db.DBConnection;
import db.DataAccessException;


/**
 * @author Daniels Kanepe
 *
 */
public class App {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Try to get database connection
		try {
			Connection con = DBConnection.getInstance().getConnection();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		// Set FlatLaf 'Look And Feel'  to a light theme
		try {
		    UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}
		

		// TODO: Generate some default data
		//new GenerateDataController().generateData();

		showLoginDefaultWithCredentials();
		
	}

	public static void showLoginDefaultWithCredentials() {
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
