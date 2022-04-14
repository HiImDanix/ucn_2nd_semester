package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * @author Daniels Kanepe
 *
 */
public class JLink extends JButton {
	private static final long serialVersionUID = -6117412042952963334L;
	
	private String text;

	public JLink(String text, Color color) {
		super(text);
		this.text = text;

		this.setForeground(color);
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
		    public void mouseEntered(MouseEvent e) {
				if (JLink.this.isEnabled()) {
			        // the mouse is on the label: underline it
					JLink.super.setText("<HTML><U>" + JLink.this.text +"</U></HTML>");
				}

		    }
		 
			@Override
		    public void mouseExited(MouseEvent e) {
				if (JLink.this.isEnabled()) {
			        // the mouse has exited the label: set back to original
			    	JLink.super.setText(JLink.this.text);
				}
		    }
		});
	}

	/*
	 * Create a JLink with default primary color
	 */
	public JLink(String text) {
		this(text, Palette.PRIMARY.getColor());
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

//	@Override
//	public void setEnabled(boolean enabled) {
//		super.setEnabled(enabled);
//
//		if (enabled) {
//			this.setForeground(Palette.PRIMARY.getColor());
//		} else {
//			this.setForeground(Palette.SECONDARY_GREY_LIGHT.getColor());
//		}
//	}
}
