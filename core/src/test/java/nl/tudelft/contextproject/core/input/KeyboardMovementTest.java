package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class KeyboardMovementTest {
    Vector2 center = new Vector2(100,100);
    float radius = 12f;
    KeyboardPosition movement = new KeyboardPosition(center, radius);

    @Test
    public void returnCenterTest() {
        Vector2 playerCenter = movement.getCenterOfPlayer();
        assertEquals(center, playerCenter);
    }

    @Test
    public void returnRadiusTest() {
        float radiusOfCircle = movement.getRadiusOfCircle();
        assertTrue(radius == radiusOfCircle);
    }
}