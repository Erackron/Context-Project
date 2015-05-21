package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;

import java.util.Collection;
import java.util.Iterator;

public enum Colour {

    RED(1, 0, 0, -16776961),
    YELLOW(1, 1, 0, -65281),
    BLUE(0, 0, 1, 65535),
    GREEN(34 / 255f, 170 / 255f, 34 / 255f, 581575423),
    PURPLE(128 / 255f, 0, 128 / 255f, 2147450625),
    ORANGE(255 / 255f, 165 / 255f, 0, 5963521),
    BLACK(0, 0, 0, 255);

    private Color libgdxColor;
    private int pixelValue;

    /**
     * Constructor method.
     * @param r The red value for a colour; lies between 0 and 1.
     * @param g The green value for a colour; lies between 0 and 1.
     * @param b The blue value for a colour; lies between 0 and 1.
     * @param pixelValue The colour value of a pixel from the pixmap.
     */
    Colour(float r, float g, float b, int pixelValue) {
        this.libgdxColor = new Color(r, g, b, 1);
        this.pixelValue = pixelValue;
    }

    /**
     * Method that returns the colour corresponding to the pixelValue.
     * Checks the Enums as defined at the top of the class.
     * @param p The pixelValue.
     * @return The corresponding colour.
     */
    public static Colour getColour(int p) {
        for (Colour c : Colour.values()) {
            if (p == c.pixelValue) {
                return c;
            }
        }
        return BLACK;
    }

    /**
     * Method that checks if two colours are complementary.
     * @param colours Collection that contains the two colours that need to be compared.
     * @return Boolean value.
     */
    public static boolean areComplementary(Collection<Colour> colours) {
        if (colours.size() != 2) {
            return false;
        } else {
            if (       colours.contains(RED) && colours.contains(GREEN)
                    || colours.contains(YELLOW) && colours.contains(PURPLE)
                    || colours.contains(BLUE) && colours.contains(ORANGE)) {
                return true;
            }
            return false;
        }
    }

    /**
     * Method that combines two colours.
     * @param colours Collection that contains the two colours that need to be blended.
     * @return The new colour resulting from the blending process.
     */
    public static Colour combine(Collection<Colour> colours) {
        if (colours.size() != 2) {
            return BLACK;
        } else {

            // Returns combinations.
            if (colours.contains(RED) && colours.contains(BLUE)) {
                return PURPLE;
            } else if (colours.contains(RED) && colours.contains(YELLOW)) {
                return ORANGE;
            } else if (colours.contains(BLUE) && colours.contains(YELLOW)) {
                return GREEN;
            } else if (areComplementary(colours)) {
                return BLACK;
            } else {

                Iterator<Colour> it = colours.iterator();
                Colour first = it.next();
                Colour second = it.next();

                if (first == RED || first == BLUE || first == YELLOW) {
                    return first;
                } else {
                    return second;
                }
            }
        }
    }

    public Color getLibgdxColor() {
        return libgdxColor;
    }
}
