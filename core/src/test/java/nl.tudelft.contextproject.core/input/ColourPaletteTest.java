package nl.tudelft.contextproject.core.input;

import nl.tudelft.contextproject.core.entities.Colour;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by LC on 13/05/15.
 */
public class ColourPaletteTest {
    private ColourPalette palette;
    @Before
    public void Setup(){
        palette=ColourPalette.standardPalette();
    }

    @Test
    public void TestStartColour(){
        assertEquals(palette.getCurrentColour(), Colour.RED);
    }

    @Test
    public void TestCycle(){
        palette.cycle();
        assertEquals(palette.getCurrentColour(),Colour.BLUE);
    }

    @Test
    public void TestCyclicBehaviour(){
        for(int i=0; i<3;i++) {
            palette.cycle();
        }
        assertEquals(palette.getCurrentColour(),Colour.RED);
    }

}

