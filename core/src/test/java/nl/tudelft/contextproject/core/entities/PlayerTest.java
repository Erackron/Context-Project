package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.playertracking.PlayerTracker;
import nl.tudelft.contextproject.core.screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
    @Spy
    private Vector2 position;
    @Mock
    ColourPalette colourPalette;
    @Mock
    GameScreen gameScreen;
    @Mock
    PlayerTracker playerTracker;

    private Player player;

    @Before
    public void setUp() {
        player = new Player(colourPalette, position, 12f);
        doCallRealMethod().when(gameScreen).createColourSpots();
        doNothing().when(playerTracker).setColourSelectBoxes(any());
        doCallRealMethod().when(gameScreen).setPlayerTracker(any());
        gameScreen.setPlayerTracker(playerTracker);
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
        player.moveTo(position);
        player.position = position;
        verify(position).set(position);
    }
}
