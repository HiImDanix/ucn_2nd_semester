package gui;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controller.DBController;
import db.DBConnection;
import db.DataAccessException;

public class App {
	public static final String DEFAULT_EMAIL = "email@example.com";
	public static final String DEFAULT_PASSWORD = "password";

	// State and default values
	public static boolean DEBUG = false;
	public static boolean darkMode = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Create thread that checks connection to database every 5 seconds
		Thread checkConnection = new Thread(() -> {
			boolean showMsgOnNextFail = true;
			while (true) {
				if (new DBController().checkConnection()) {
					showMsgOnNextFail = true;
				} else {
					if (showMsgOnNextFail) {
						Messages.error("You do not have a connection to the database.");
						showMsgOnNextFail = false;
					}
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} );
		checkConnection.start();

		// parse args
		if (args.length > 0) {
			if (Arrays.stream(args).anyMatch(arg -> arg.equalsIgnoreCase("-debug"))) {
				System.out.println("Debug mode enabled.");
				DEBUG = true;
			}
			if (Arrays.stream(args).anyMatch(arg -> arg.equalsIgnoreCase("-rebuildDB"))) {
				System.out.println("Rebuilding database.");
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
		DBController dataCtrl = new DBController();

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
