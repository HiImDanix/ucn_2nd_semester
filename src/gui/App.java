package gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import controller.DataController;
import controller.EmployeeController;
import db.DBConnection;
import db.DataAccessException;


/**
 * @author Daniels Kanepe
 *
 */
public class App {

	private static final Boolean resetDB = false;
	private static final String DEFAULT_EMAIL = "email@example.com";
	private static final String DEFAULT_PASSWORD = "password";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (resetDB) {
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
}
