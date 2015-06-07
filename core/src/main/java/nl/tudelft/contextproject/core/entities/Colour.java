package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public enum Colour {

    RED(1, 0, 0, -16776961),
    YELLOW(1, 1, 0, -65281),
    BLUE(0, 0, 1, 65535),
    GREEN(34 / 255f, 170 / 255f, 34 / 255f, 581575423),
    PURPLE(128 / 255f, 0, 128 / 255f, -2147450625),
    ORANGE(255 / 255f, 165 / 255f, 0, -5963521),
    BLACK(0, 0, 0, 255),
    ERASER(0.5f, 0.5f, 0.5f, 2139062271),
    WHITE(1, 1, 1, 0),
    EXCEPTION(1, 0.68f, 0.68f, -5394945);

    private final Color libgdxColor;

    @Getter
    private final int pixelValue;

    /**
     * Constructor method.
     *
     * @param r          The red value for a colour; lies between 0 and 1.
     * @param g          The green value for a colour; lies between 0 and 1.
     * @param b          The blue value for a colour; lies between 0 and 1.
     * @param pixelValue The colour value of a pixel from the pixmap.
     */
    Colour(float r, float g, float b, int pixelValue) {
        this.libgdxColor = new Color(r, g, b, 1);
        this.pixelValue = pixelValue;
    }

    /**
     * Method that returns the colour corresponding to the pixelValue.
     * Checks the Enums as defined at the top of the class.
     *
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
     *
     * @param colours Collection that contains the two colours that need to be compared.
     * @return Boolean value.
     */
    public static boolean areComplementary(Collection<Colour> colours) {
        return colours.size() == 2 && (
                colours.contains(RED) && colours.contains(GREEN)
                        || colours.contains(YELLOW) && colours.contains(PURPLE)
                        || colours.contains(BLUE) && colours.contains(ORANGE)
            );
    }

    /**
     * Method that combines two colours.
     *
     * @param colours Collection that contains the two colours that need to be blended.
     * @return The new colour resulting from the blending process.
     */
    public static Colour combine(Collection<Colour> colours) {
        if (colours.size() == 2) {
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
//
//                Iterator<Colour> it = colours.iterator();
//                Colour first = it.next();
//                Colour second = it.next();
//
//                if (first == RED || first == BLUE || first == YELLOW) {
//                    return first;
//                } else {
//                    return second;
//                }
            }
        }

        return EXCEPTION;
    }

    /**
     * Method to return the LibgdxColor when drawing.
     *
     * @return The libgdxColor;
     */
    public Color getLibgdxColor() {
        return libgdxColor;
    }


    /**
     * Get the base colours including the eraser.
     *
     * @return The list of base colours
     */
    public static List<Colour> getBaseColours() {
        return Arrays.asList(RED, BLUE, YELLOW, ERASER);
    }
}
