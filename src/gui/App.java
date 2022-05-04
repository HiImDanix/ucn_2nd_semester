package gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controller.DataController;
import controller.EmployeeController;
import db.DataAccessException;

public class App {
	public static boolean DEBUG = false;
	public static final String DEFAULT_EMAIL = "email@example.com";
	public static final String DEFAULT_PASSWORD = "password";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// parse args
		if (args.length > 0) {
			if (Arrays.stream(args).anyMatch(arg -> arg.toLowerCase().equals("-debug"))) {
				DEBUG = true;
			}
			if (Arrays.stream(args).anyMatch(arg -> arg.toLowerCase().equals("-reset"))) {
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
	}
}
