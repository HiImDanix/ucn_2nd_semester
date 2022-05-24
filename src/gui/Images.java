package gui;


import javax.swing.*;
import java.awt.*;

public enum Images {
    // TODO: convert to resource
    // Here are the images that will be used in the program.
    ADD_ITEM("images/add_item.png"),
    MOON("images/moon.png"),
    SUN("images/sun.png"),
    RELOAD("images/reload.png"),
    DOOR("images/door.png"),
    TENANT("images/tenant.png"),
    ROOM_CATEGORY("images/room_category.png"),
    CONTRACT("images/contract.png");


    private final String path;
    private final String defaultPath = "images/default.png";

    // Take path of image
    Images(String path) {
        this.path = path;
    }

    // Get ImageIcon
    public ImageIcon getImageIcon() {
        // If image at path is not found, return default image icon
        ImageIcon imageIcon = new ImageIcon(path);
        if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            imageIcon = new ImageIcon(defaultPath);
        }

        if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            return imageIcon;
        } else {
            System.out.println("Image not found: " + path);
            return null;
        }
    }

    // Get resized image
    public ImageIcon getImageIcon(int width, int height) {
        ImageIcon imageIcon = getImageIcon();
        // resize only if size is different
        if (imageIcon.getIconWidth() != width || imageIcon.getIconHeight() != height) {
            Image image = imageIcon.getImage();
            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        }
        return imageIcon;
    }

    // get image and resize to JComponent font's size
    public ImageIcon getImageIcon(JComponent component) {
        int fontSize = component.getFont().getSize();
        return getImageIcon(fontSize, fontSize);

    }
}

