package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ike on 7-5-2015.
 */
@RunWith(Parameterized.class)
public class KeyboardInputProcessorTest {

    private KeyboardInputProcessor inputProcessor;
    private int inputKey;
    private boolean expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Input.Keys.W, true}, {Input.Keys.S, true}, {Input.Keys.A, true}, {Input.Keys.D, true}, {Input.Keys.UP, true}, {Input.Keys.DOWN, true}, {Input.Keys.SPACE, true}
        });
    }

    public KeyboardInputProcessorTest(int input, boolean expected) {
        this.inputKey = input;
        this.expected = expected;
    }

    @Before
    public void setUp() {
        inputProcessor = new KeyboardInputProcessor();
    }

    @Test
    public void keyDownTest() {
        inputProcessor.keyDown(inputKey);
        assertTrue(inputProcessor.isPressed(inputKey) == expected);
    }

    @Test
    public void keyUpTest() {
        inputProcessor.keyUp(inputKey);
        assertTrue(inputProcessor.isPressed(inputKey) == !expected);
    }

}
