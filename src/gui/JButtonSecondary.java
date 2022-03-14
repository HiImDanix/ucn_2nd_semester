package gui;

import javax.swing.*;

import java.awt.*;

public class JButtonSecondary extends JButton {
	

    private static final long serialVersionUID = 90062794964789095L;

    public JButtonSecondary(String text) {
        super(text);
        super.setBackground(Color.WHITE);
        super.setBorder(BorderFactory.createLineBorder(Palette.PRIMARY));
        super.setForeground(Palette.PRIMARY);
        super.setMargin(new Insets(5, 15, 5, 15));
    }
    
    
}