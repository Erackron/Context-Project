package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import nl.tudelft.contextproject.core.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;


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
    @Mock
    GameScreen gameScreen;

    private Player player;

    @Before
    public void setUp() {
        player = new Player(colourPalette, position, brushPosition, 0, 50f);
        doReturn(Collections.singletonList(player)).when(gameScreen).getPlayers();
        doCallRealMethod().when(gameScreen).createColourSpots();
        gameScreen.createColourSpots();
    }

    @Test
    public void constructorOverLoadTest() {
        player = new Player(colourPalette, 30, 30);
        assertEquals(new Vector2(30f, 30f), player.getPosition());
        assertEquals(new Vector2(80f, 30f), player.getBrushPosition());
    }

    @Test
    public void testPositiveMove() {
        player.move(50, 50);
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
        FloatMatcher matcher = new FloatMatcher(-51f, -49f);
        player.turnBrush(Math.PI, 1);
        Mockito.verify(brushPosition).set(Mockito.floatThat(matcher), Mockito.anyFloat());

    }

    @Test
    public void testTurnBrush2() {
        FloatMatcher matcher = new FloatMatcher(49f, 51f);
        player.turnBrush(Math.PI / 2, 1);
        Mockito.verify(brushPosition).set(Mockito.anyFloat(), Mockito.floatThat(matcher));

    }

    @Test
    public void testCheckPositionRed() {
        player.position.x = 33f;
        player.position.y = 134f;
        player.checkPosition();
        Mockito.verify(colourPalette).setColour(Colour.RED);
    }

    @Test
    public void testCheckPositionBlue() {
        player.position.x = 14f;
        player.position.y = 225f;
        player.checkPosition();
        Mockito.verify(colourPalette).setColour(Colour.BLUE);
    }

    @Test
    public void testCheckPositionYellow() {
        player.position.x = 26f;
        player.position.y = 332f;
        player.checkPosition();
        Mockito.verify(colourPalette).setColour(Colour.YELLOW);
    }

    @Test
    public void testCheckPositionEraser() {
        player.position.x = 42f;
        player.position.y = 412f;
        player.checkPosition();
        Mockito.verify(colourPalette).setColour(Colour.ERASER);
    }


}
