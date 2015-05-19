package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Ike on 19-5-2015.
 */
@RunWith(Parameterized.class)
public class KeyboardInputProcessorTest {

    private int input;
    private Vector2 expected;

    private Player player;
    private static KeyboardInputProcessor inputProcessor;

    private static float playerX = 100f;
    private static float playerY = 100f;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Input.Keys.W, new Vector2(playerX, playerY + inputProcessor.PIXELS_PER_UPDATE)}, {Input.Keys.S, new Vector2(playerX, playerY - inputProcessor.PIXELS_PER_UPDATE)},
                {Input.Keys.A, new Vector2(playerX - inputProcessor.PIXELS_PER_UPDATE, playerY)}, {Input.Keys.D, new Vector2(playerX + inputProcessor.PIXELS_PER_UPDATE, playerY)},
                {Input.Keys.UP, new Vector2(playerX, playerY)}, {Input.Keys.DOWN, new Vector2(playerX, playerY)},
                {Input.Keys.LEFT, new Vector2(playerX, playerY)}, {Input.Keys.RIGHT, new Vector2(playerX, playerY)},
                {Input.Keys.M, new Vector2(playerX, playerY)}, {Input.Keys.N, new Vector2(playerX, playerY)},
                {Input.Keys.C, new Vector2(playerX, playerY)}
        });
    }

    public KeyboardInputProcessorTest(int input, Vector2 expected) {
        this.input = input;
        this.expected = expected;
    }

    @Before
    public void setUp() {
        player = new Player();
        player.setPosition(new Vector2(playerX, playerY));
        inputProcessor = new KeyboardInputProcessor(player);
    }

    @Test
    public void keyDownTest() {
        inputProcessor.keyDown(input);
        assertTrue(inputProcessor.isPressed(input));
    }

    @Test
    public void keyUpTest() {
        inputProcessor.keyDown(input);
        inputProcessor.keyUp(input);
        assertFalse(inputProcessor.isPressed(input));
    }

    @Test
    public void updateTest() {
        inputProcessor.keyDown(input);
        inputProcessor.update(1);

        assertTrue(player.getPosition().x == expected.x);
        assertTrue(player.getPosition().y == expected.y);
        
    }

}
