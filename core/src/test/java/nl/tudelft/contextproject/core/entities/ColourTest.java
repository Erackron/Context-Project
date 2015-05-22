package nl.tudelft.contextproject.core.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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



}
