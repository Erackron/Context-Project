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

}
