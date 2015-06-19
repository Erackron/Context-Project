package nl.tudelft.contextproject.core.entities;

import static org.junit.Assert.*;
import com.badlogic.gdx.graphics.Color;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Ike on 21-5-2015.
 */
@RunWith(Enclosed.class)
public class ColourTest {

    @RunWith(Parameterized.class)
    public static class CombineTests{

        private Colour first;
        private Colour second;
        private Colour expected;

        @Parameterized.Parameters
        public static Collection<Object> data() {
            return Arrays.asList(new Object[][]{
                    {Colour.RED , Colour.BLUE,Colour.PURPLE},
                    {Colour.RED,Colour.YELLOW,Colour.ORANGE},
                    {Colour.BLUE,Colour.YELLOW,Colour.GREEN},
                    {Colour.ORANGE,Colour.YELLOW,Colour.EXCEPTION},
                    {Colour.PURPLE,Colour.BLUE,Colour.EXCEPTION},
                    {Colour.GREEN,Colour.BLUE,Colour.EXCEPTION},
                    {Colour.RED,Colour.RED,Colour.EXCEPTION},
                    {Colour.GREEN,Colour.RED,Colour.BLACK}
            });
        }

        /**
         * Generate a CombineTest Scenario
         * @param first first colour to blend
         * @param second second colour to blend
         * @param expected  expected colour as result of the blend
         */
        public CombineTests(Colour first, Colour second, Colour expected) {
            this.first = first;
            this.second = second;
            this.expected = expected;
        }

        @Test
        public void combineTest() {
            assertEquals(expected, Colour.combine(Arrays.asList(first,second)));
        }
    }

    public static class AdditionalColourTests {

        @Test
        public void combineThreeColoursTest() {
            Colour test = Colour.combine(Arrays.asList(Colour.RED, Colour.BLUE, Colour.YELLOW));
            assertEquals(test, Colour.EXCEPTION);
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
            assertEquals(Colour.getColour(5), black);
        }

        @Test
        public void paintOnEraserTest() {
            Colour white = Colour.WHITE;
            Colour red = Colour.RED;
            Colour actual = Colour.combine(Arrays.asList(red, white));
            assertEquals(red, actual);
        }

        @Test
        public void getLibgdxColorTest() {
            Colour colourBlue = Colour.BLUE;
            Color colorBlue = Color.BLUE;
            assertEquals(colorBlue, colourBlue.getLibgdxColor());
        }
    }
}
