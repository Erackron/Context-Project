package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ike on 21-5-2015.
 */
public class ColourTest {

    @Test
    public void combineRedBlueTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.RED, Colour.BLUE));
        assertTrue(test == Colour.PURPLE);
    }

    @Test
    public void combineRedYellowTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.RED, Colour.YELLOW));
        assertTrue(test == Colour.ORANGE);
    }

    @Test
    public void combineBlueYellowTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.BLUE, Colour.YELLOW));
        assertTrue(test == Colour.GREEN);
    }

    @Test
    public void combineOrangeTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.ORANGE, Colour.YELLOW));
        assertTrue(test == Colour.YELLOW);
    }

    @Test
    public void combinePurpleTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.PURPLE, Colour.BLUE));
        assertTrue(test == Colour.BLUE);
    }

    @Test
    public void combineGreenTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.GREEN, Colour.BLUE));
        assertTrue(test == Colour.BLUE);
    }

    @Test
    public void combineSameColourTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.RED, Colour.RED));
        assertTrue(test == Colour.RED);
    }

    @Test
    public void combineComplementaryTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.GREEN, Colour.RED));
        assertTrue(test == Colour.BLACK);
    }

    @Test
    public void combineThreeColoursTest() {
        Colour test = Colour.combine(Arrays.asList(Colour.RED, Colour.BLUE, Colour.YELLOW));
        assertTrue(test == Colour.BLACK);
    }

    @Test
    public void complementaryRedGreenTest() {
        assertTrue(Colour.areComplementary(Arrays.asList(Colour.RED, Colour.GREEN)));
    }

    @Test
    public void complementaryYellowPurpleTest() {
        assertTrue(Colour.areComplementary(Arrays.asList(Colour.YELLOW, Colour.PURPLE)));
    }

    @Test
    public void complementaryBlueOrangeTest() {
        assertTrue(Colour.areComplementary(Arrays.asList(Colour.BLUE, Colour.ORANGE)));
    }

    @Test
    public void notComplementaryTest() {
        assertFalse(Colour.areComplementary(Arrays.asList(Colour.BLUE, Colour.YELLOW)));
    }

    @Test
    public void complementaryThreeColoursTest() {
        assertFalse(Colour.areComplementary(Arrays.asList(Colour.BLUE, Colour.YELLOW, Colour.PURPLE)));
    }

    @Test
    public void getPrimaryColourTest() {
        Colour red = Colour.RED;
        assertEquals(Colour.getColour(red.getPixelValue()), red);
    }

    @Test
    public void getSecondaryColourTest() {
        Colour orange = Colour.ORANGE;
        assertEquals(Colour.getColour(orange.getPixelValue()), orange);
    }

    @Test
    public void getNoValidColourTest() {
        Colour black = Colour.BLACK;
        assertEquals(Colour.getColour(0), black);
    }

    @Test
    public void getLibgdxColorTest() {
        Colour colourblue = Colour.BLUE;
        Color colorblue = Color.BLUE;
        assertEquals(colorblue, colourblue.getLibgdxColor());
    }
}
