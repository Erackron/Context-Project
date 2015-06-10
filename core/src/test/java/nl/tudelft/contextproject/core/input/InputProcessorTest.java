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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
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

    private Vector2 playerPosition;

    private KeyboardInputProcessor processor;

    private static final int activePlayerId = 1;

    @Before
    public void setup() {
        playerPosition = new Vector2(200,300);
        keys = Mockito.spy(HashMap.class);
        when(players.get(1)).thenReturn(player);
        when(player.getPosition()).thenReturn(playerPosition);
        processor = new KeyboardInputProcessor(players,keys);
    }

    @Test
    public void KeyDownCTest() {
        int key = Input.Keys.C;
        keys.put(key,true);
        processor.keyDown(key);
        assertTrue(processor.toggled);
        verify(keys, Mockito.times(2)).put(key, false);
    }

    @Test
    public void KeyDownSPACETest() {
        int key = Input.Keys.SPACE;
        keys.put(key,true);
        processor.keyDown(key);
        assertTrue(processor.paintToggled);
        verify(keys, Mockito.times(2)).put(key,false);
    }

    @Test
    public void KeyDownNUM_1tTest() {
        int key = Input.Keys.NUM_1;
        keys.put(key,true);
        processor.keyDown(key);
        assertTrue(processor.playerToggles[0]);
        verify(keys, Mockito.times(2)).put(key,false);
    }

    @Test
    public void paintDrawTest() {
        boolean drawOn = true;
        processor.update(1f, activePlayerId);
        processor.paintDraw(drawOn);
        Mockito.verify(player).getPosition();
    }
}
