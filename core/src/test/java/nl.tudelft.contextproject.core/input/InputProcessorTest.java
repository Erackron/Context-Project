package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by LC on 20/05/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class InputProcessorTest {
    private HashMap<Integer,Boolean> keys;
    @Mock
    private List<Player> players;
    @Mock
    private Player player;
    @Mock
    private Vector2 playerPosition;

    private KeyboardInputProcessor processor;

    private static final int activePlayerId = 1;

    @Before
    public void setup() {
        keys = new HashMap<>();
        when(players.get(1)).thenReturn(player);
        when(player.getPosition()).thenReturn(playerPosition);
        processor = new KeyboardInputProcessor(players,keys);
    }

    @Test
    public void updateTestNorth() {
        keys.put(Input.Keys.W,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).move(0,75);
        keys.put(Input.Keys.W,false);
    }
}
