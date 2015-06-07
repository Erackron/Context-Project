package nl.tudelft.contextproject.core.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;


public class KeyboardMovementTest {
    Vector2 centre = new Vector2(100,100);
    float radius = 12f;
    KeyboardMovement movement = new KeyboardMovement(centre, radius);

    @Test
    public void returnCentreTest() {
        Vector2 playercentre = movement.getCenterOfPlayer();
        assertEquals(centre, playercentre);
    }

    @Test
    public void returnRadiusTest() {
        float radiusOfCircle = movement.getRadiusOfCircle();
        assertTrue(radius == radiusOfCircle);
    }
}