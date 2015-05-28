package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class KeyboardMovementTest {
    Vector2 centre = new Vector2(100,100);
    Vector2 start = new Vector2(100,50);
    Vector2 end = new Vector2(100,150);
    KeyboardMovement movement = new KeyboardMovement(centre, start, end);

    @Test
    public void returnCentreTest() {
        Vector2 playercentre = movement.getCenterOfPlayer();
        assertTrue(playercentre.x == 100);
        assertTrue(playercentre.y == 100);
    }

    @Test
    public void returnStartTest() {
        Vector2 startmovement = movement.getStartOfMovement();
        assertTrue(startmovement.x == 100);
        assertTrue(startmovement.y == 50);
    }

    @Test
    public void returnEndTest() {
        Vector2 endmovement = movement.getEndOfMovement();
        assertTrue(endmovement.x == 100);
        assertTrue(endmovement.y == 150);
    }
}