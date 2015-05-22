package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

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
    public void testPositiveMove() {
        player.move(50,50);
        Mockito.verify(position).add(50,50);
        Mockito.verify(brushPosition).add(50,50);
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

    @Test
    public void testTurnBrush() {
        player.turnBrush(330, 1);
        Mockito.verify(brushPosition).add(1, 0);
    }

    @Test
    public void testTurnBrush2() {
        player.turnBrush(90, 1);
        Mockito.verify(brushPosition).add(0, 1);
    }

}
