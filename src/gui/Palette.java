/**
 * 
 */
package gui;

import java.awt.Color;

/**
 * @author Daniels Kanepe
 * 
 * This file defines the colour palette used throughout the project
 *
 */
public enum Palette {
	PRIMARY(101, 88, 245),
	SECONDARY_YELLOW(247, 195, 37),
	SECONDARY_ORANGE(232, 131, 58),
	SECONDARY_GREY_DARK(75, 92, 107),
	SECONDARY_GREY_NORMAL(120, 136, 150),
	SECONDARY_GREY_LIGHT(195, 207, 217),
	SECONDARY_GREY_LIGHTEST(237, 241, 245),
	SUCCESS(32, 120, 104),
	DANGER(211, 69, 91);

	private final int red;
	private final int green;
	private final int blue;

	private Palette(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color getColor() {
		return new Color(red, green, blue);
	}
}
