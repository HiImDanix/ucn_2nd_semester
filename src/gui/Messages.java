package gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Daniels Kanepe
 * 
 * Pop-up windows used throughout the project
 *
 */
public class Messages {

	private Messages() {
	}
	
	/**
	 * Show an error message window.
	 *
	 * @param component component component the component to center the window in. It can be null!
	 * @param message the message
	 * @param title the title
	 */
	public static void error(Component component, String message, String title) {
        JOptionPane.showMessageDialog(component, message, title,
                JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show an error message window.
	 *
	 * @param component component component the component to center the window in. It can be null!
	 * @param message the message
	 */
	public static void error(Component component, String message) {
		Messages.error(component, message, "Error");
	}


	/**
	 * Show an error message window.
	 *
	 * @param message the message
	 * @param title the title
	 */
	public static void error(String message, String title) {
		Messages.error(null, message, title);
	}
	
	/**
	 * Shows a confirmation window
	 *
	 * @param component the component to center the confirmation window in. It can be null!
	 * @param message the message
	 * @param title the title
	 * @return true, if successful
	 */
	public static boolean confirm(Component component, String message, String title) {
		int confirmResult = JOptionPane.showConfirmDialog(component, message, title, JOptionPane.YES_NO_OPTION);
        return confirmResult == 0 ? true : false;
	}
	public static boolean confirm(Component component, String message) {
		return Messages.confirm(null, message, "Confirm");
	}

	/**
	 * Shows a confirmation window
	 *
	 * @param message the message
	 * @param title the title
	 * @return true, if successful
	 */
	public static boolean confirm(String message, String title) {
		return Messages.confirm(null, message, title);
	}

	/**
	 * Shows a confirmation window
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	public static boolean confirm(String message) {
		return Messages.confirm(null, message, "Confirm");
	}
	
	/**
	 * Show an information window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 * @param title the title
	 */
	public static void info(Component component, String message, String title) {
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
		
	}

	/**
	 * Show an information window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 */
	public static void info(Component component, String message) {
		Messages.info(component, message, "Information");
	}

	/**
	 * Show an information window
	 *
	 * @param message the message
	 * @param title the title
	 */
	public static void info(String message, String title) {
		Messages.info(null, message, title);
	}

	/**
	 * Show a success window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 * @param title the title
	 */
	public static void success(Component component, String message, String title) {
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show a success window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 */
	public static void success(Component component, String message) {
		Messages.success(component, message, "Success");
	}

	/**
	 * Show a success window
	 *
	 * @param message the message
	 * @param title the title
	 */
	public static void success(String message, String title) {
		Messages.success(null, message, title);
	}

	/**
	 * Show a warning window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 * @param title the title
	 */
	public static void warning(Component component, String message, String title) {
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Show a warning window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 */
	public static void warning(Component component, String message) {
		Messages.warning(component, message, "Warning");
	}

	/**
	 * Show a warning window
	 *
	 * @param message the message
	 * @param title the title
	 */
	public static void warning(String message, String title) {
		Messages.warning(null, message, title);
	}
}
