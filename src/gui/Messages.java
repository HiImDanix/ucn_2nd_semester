package gui;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Daniels Kanepe
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
	public static void error(Component component, String message) {
		Messages.error(component, message, "Error");
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
	 * Show an information window
	 *
	 * @param component component the component to center the window in. It can be null!
	 * @param message the message
	 * @param title the title
	 */
	public static void info(Component component, String message, String title) {
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
		
	}
	public static void info(Component component, String message) {
		Messages.info(component, message, "Information");
	}
	
	

}
