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
        for(int i=0; i<4;i++) {
            palette.cycle();
        }
        assertEquals(palette.getCurrentColour(),Colour.RED);
    }

    @Test
    public void TestSetCurrent(){
        palette.setCurrent(2);
        assertEquals(palette.getCurrent(), 2);
    }

}

