package nl.tudelft.contextproject.core.entities;


import lombok.Data;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class ColourPalette {
    @NonNull
    private final List<Colour> colours;

    private int current = 0;

    protected static Random random = new Random();

    /**
     * Create a new ColourPalette.
     *
     * @param colours The colours available in this colour palette
     */
    public ColourPalette(List<Colour> colours) {
        this.colours = colours;
        do {
            this.current = random.nextInt(colours.size());
        } while (colours.get(current) == Colour.ERASER);
    }

    /**
     * Cycle through the colours of this ColourPalette.
     *
     * @return The next colour in the cycle
     */
    public Colour cycle() {
        current++;
        current %= colours.size();
        return colours.get(current);
    }

    /**
     * Set the colour index of the active colour.
     *
     * @param colourIndex The colour index to set it to
     */
    public void setCurrent(int colourIndex) {
        current = colourIndex;
    }

    /**
     * Set the active colour to a specified colour.
     * Does nothing if the colour is not in this ColourPalette.
     *
     * @param colour The colour to set the active colour to
     */
    public void setColour(Colour colour) {
        int index = colours.indexOf(colour);
        if (index != -1) {
            current = index;
        }
    }

    /**
     * Get the current colour of this ColourPalette.
     *
     * @return The current colour
     */
    public Colour getCurrentColour() {
        return colours.get(current);
    }

    /**
     * Create a new Standard ColourPalette.
     *
     * @return The newly created ColourPalette
     */
    public static ColourPalette standardPalette() {
        return new ColourPalette(
                Arrays.asList(Colour.RED, Colour.BLUE, Colour.YELLOW, Colour.ERASER)
        );
    }
}
