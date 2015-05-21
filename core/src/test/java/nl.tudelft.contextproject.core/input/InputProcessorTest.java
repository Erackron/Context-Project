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
        when(player.getBrushPosition()).thenReturn(playerPosition);
        processor = new KeyboardInputProcessor(players,keys);
    }

    @Test
    public void updateTestNorth() {
        keys.put(Input.Keys.W,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).move(0,75);
    }

    @Test
    public void updateTestSouth() {
        keys.put(Input.Keys.S,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).move(0,-75);
    }


    @Test
    public void updateTestWest() {
        keys.put(Input.Keys.A,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).move(-75,0);
    }

    @Test
    public void updateTestEast() {
        keys.put(Input.Keys.D,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).move(75,0);
    }

    @Test
    public void turnBrushTest() {
        keys.put(Input.Keys.LEFT,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).turnBrush(KeyboardInputProcessor.ANGLE,1f);
    }
    @Test
    public void reverseTurnBrushTest() {
        keys.put(Input.Keys.RIGHT,true);
        processor.update(1f,activePlayerId);
        Mockito.verify(player).turnBrush(-KeyboardInputProcessor.ANGLE,1f);
    }
    @Test
    public void keyDownSpaceTest() {
        int key = Input.Keys.SPACE;
        processor.activePlayerId=1;
        processor.keyDown(key);
        verify(player).getBrushPosition();
        verify(keys).put(key,true);
    }

    @Test
    public void KeyDownCTest() {
        int key = Input.Keys.C;
        keys.put(key,true);
        processor.keyDown(key);
        assertTrue(processor.toggled);
        verify(keys, Mockito.times(2)).put(key,false);
    }

}
