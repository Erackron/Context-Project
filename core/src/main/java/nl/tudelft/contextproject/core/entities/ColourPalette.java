package nl.tudelft.contextproject.core.entities;


import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC on 13/05/15.
 */
@Data
public class ColourPalette {
    @NonNull
    private final List<Colour> colours;

    private int current = 0;

    /**
     * Cycle through the colours of this ColourPalette.
     * @return The next colour in the cycle
     */
    public Colour cycle() {
        current++;
        current %= colours.size();
        return colours.get(current);
    }

    public void setCurrent(int colourindex){
        current = colourindex;
    }

    /**
     * Get the current colour of this ColourPalette.
     * @return The current colour
     */
    public Colour getCurrentColour() {
        return colours.get(current);
    }

    /**
     * Create a new Standard ColourPalette.
     * @return The newly created ColourPalette
     */
    public static ColourPalette standardPalette() {
        List<Colour> colours = new ArrayList<>();
        colours.add(Colour.RED);
        colours.add(Colour.BLUE);
        colours.add(Colour.YELLOW);
        colours.add(Colour.BLACK);
        return new ColourPalette(colours);
    }
}
