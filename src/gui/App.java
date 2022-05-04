package gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controller.DataController;
import controller.EmployeeController;
import db.DataAccessException;

public class App {
	public static boolean DEBUG = false;
	private static final String DEFAULT_EMAIL = "email@example.com";
	private static final String DEFAULT_PASSWORD = "password";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// parse args
		if (args.length > 0) {
			if (args[0].equals("-debug")) {
				DEBUG = true;
			}
			if (args[0].equals("-reset")) {
				resetDatabase();
			}
		}

		// Set FlatLaf 'Look And Feel'  to a light theme
		try {
		    UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}

		// Show login screen
		showLoginDefaultWithCredentials();

	}

	public static void showLoginDefaultWithCredentials() {
		EventQueue.invokeLater(() -> {
			try {
				Login frame = new Login(DEFAULT_EMAIL, DEFAULT_PASSWORD);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void resetDatabase() {
		System.out.println("Resetting database...");
		DataController dataCtrl = new DataController();

		// Clear data
		try {
			dataCtrl.clear();
		} catch (IOException | DataAccessException e) {
			System.out.println("Error clearing data.");
			System.exit(1);
		}

		// Add demo data
		try {
			dataCtrl.addDemoData();
		} catch (DataAccessException e) {
			System.out.println("Error adding demo data.");
			System.exit(1);
		}

		// Add default employee to log in
		try {
			new EmployeeController().addEmployee("Admin", "Admin", DEFAULT_EMAIL, DEFAULT_PASSWORD);
		} catch (DataAccessException e) {
			e.printStackTrace();
			System.out.println("Could not create default employee. Quitting...");
			System.exit(1);
		}
	}
}
