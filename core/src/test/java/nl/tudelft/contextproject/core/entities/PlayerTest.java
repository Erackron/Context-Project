package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    ColourPalette colourPalette;
    @Mock
    GameScreen gameScreen;

    private Player player;

    @Before
    public void setUp() {
        player = new Player(colourPalette, position, 12f);
        doReturn(Collections.singletonList(player)).when(gameScreen).getPlayers();
        doCallRealMethod().when(gameScreen).createColourSpots();
        gameScreen.createColourSpots();
    }

    @Test
    public void constructorOverLoadTest() {
        player = new Player(colourPalette, 30, 30, 12f);
        assertEquals(new Vector2(30f, 30f), player.getPosition());
    }

    @Test
    public void testRadiusOfPlayer() {
        assertEquals(12f, player.getRadius(), 0.1);
    }

    @Test
    public void testColourPalette() {
        assertEquals(colourPalette, player.getColourPalette());
    }

    @Test
    public void testPositiveMove() {
        player.move(50, 50);
        Mockito.verify(position).add(50, 50);
    }
/*
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
    */
}
