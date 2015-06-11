package nl.tudelft.contextproject.core.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by LC on 13/05/15.
 */
public class ColourPaletteTest {
    private ColourPalette palette;

    @Before
    public void Setup() {
        palette = ColourPalette.standardPalette();
    }

    @Test
    public void TestStartColour() {
        assertEquals(palette.getColours().get(0), palette.getCurrentColour());
    }

    @Test
    public void TestCycle() {
        palette.cycle();
        assertEquals(palette.getColours().get(1), palette.getCurrentColour());
    }

    @Test
    public void TestCyclicBehaviour() {
        for (int i = 0; i < palette.getColours().size(); i++) {
            palette.cycle();
        }
        assertEquals(palette.getColours().get(0), palette.getCurrentColour());
    }

    @Test
    public void TestSetColour() {
        palette.setColour(Colour.BLUE);
        assertEquals(Colour.BLUE, palette.getCurrentColour());
    }

    @Test
    public void TestSetCurrent() {
        palette.setCurrent(2);
        assertEquals(2, palette.getCurrent());
    }

}

