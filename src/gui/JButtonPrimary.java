package gui;

import javax.swing.*;
import java.awt.*;

public class JButtonPrimary extends JButton {
	

    private static final long serialVersionUID = 90062794964789095L;

    public JButtonPrimary(String text) {
        super(text);
        super.setBackground(Palette.PRIMARY);
        super.setForeground(Color.WHITE);
        super.setMargin(new Insets(5, 15, 5, 15));
    }
    
}