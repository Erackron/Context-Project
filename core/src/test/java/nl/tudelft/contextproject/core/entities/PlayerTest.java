package nl.tudelft.contextproject.core.entities;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by LC on 20/05/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
    @Mock
    private Vector2 position;
    @Mock
    private Vector2 brushPosition;
    @Mock
    ColourPalette colourPalette;

    private Player player;

    @Before
    public void setUp() {
        player = new Player(colourPalette,position,brushPosition,0);
    }

    @Test
    public void constructorOverLoadTest() {
        player = new Player(colourPalette,30,30);
        assertEquals(new Vector2(30f, 30f), player.getPosition());
        assertEquals(new Vector2(80f,30f),player.getBrushPosition());
    }

    @Test
    public void testPositiveMove() {
        player.move(50,50);
        Mockito.verify(position).add(50, 50);
        Mockito.verify(brushPosition).add(50, 50);
    }

    @Test
    public void addAngle() {
        assertEquals(30, player.addAngle(30), 1);
    }

    @Test
    public void testChangeRadiusPositive() {
        player.changeRadius(30f);
        assertEquals(80f, player.radius, 1f);
    }

    @Test
    public void testChangeRadiusNegative() {
        player.changeRadius(-30f);
        assertEquals(20f, player.radius, 1f);
    }

    @Test
    public void testChangeRadiusOver250() {
        player.changeRadius(300f);
        assertEquals(250f, player.radius, 1f);
    }

    @Test
    public void testChangeRadiusUnder10() {
        player.changeRadius(-300f);
        assertEquals(10f, player.radius, 1f);
    }

    @Data
    private static class FloatMatcher extends ArgumentMatcher<Float> {
        private final float lower;
        private final float upper;

        @Override
        public boolean matches(Object argument) {
            Float value = (Float) argument;
            return (value > lower && value < upper);
        }
    }

    @Test
    public void testTurnBrush() {
        FloatMatcher matcher = new FloatMatcher(-51f,-49f);
        player.turnBrush(Math.PI, 1);
        Mockito.verify(brushPosition).set(Mockito.floatThat(matcher), Mockito.anyFloat());

    }

    @Test
    public void testTurnBrush2() {
        FloatMatcher matcher = new FloatMatcher(49f,51f);
        player.turnBrush(Math.PI / 2, 1);
        Mockito.verify(brushPosition).set(Mockito.anyFloat(), Mockito.floatThat(matcher));

    }
/*
    @Test
    public void testSetRed() {
        player.setRed();
        assertEquals(Colour.RED, player.getColourPalette().getCurrentColour());
    }

    @Test
    public void testSetBlue() {
        player.setBlue();
        assertEquals(Colour.BLUE, player.getColourPalette().getCurrentColour());
    }

    @Test
    public void testSetYellow() {
        player.setYellow();
        assertEquals(Colour.YELLOW, player.getColourPalette().getCurrentColour());
    }

    @Test
    public void testSetWhite(){
        player.setWhite();
        assertEquals(Colour.ERASER, player.getColourPalette().getCurrentColour());
    }
    */
}
