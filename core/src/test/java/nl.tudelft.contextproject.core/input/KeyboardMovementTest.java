package nl.tudelft.contextproject.core.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;


public class KeyboardMovementTest {
    Vector2 center = new Vector2(100,100);
    float radius = 12f;
    KeyboardMovement movement = new KeyboardMovement(center, radius);

    @Test
    public void returnCenterTest() {
        Vector2 playercenter = movement.getCenterOfPlayer();
        assertEquals(center, playercenter);
    }

    @Test
    public void returnRadiusTest() {
        float radiusOfCircle = movement.getRadiusOfCircle();
        assertTrue(radius == radiusOfCircle);
    }
}