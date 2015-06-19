package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.input.PlayerAPI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CircleTest {

    protected Circle circle;
    protected Circle newCircle;
    protected Circle otherCircle;
    protected Vector2 center;
    protected float radius;
    protected PlayerAPI playerAPI = PlayerAPI.getPlayerApi();
    protected ColourSelectBox selectBox;

    @Before
    public void Setup() {
        playerAPI.setCameraInputSize(100, 75);
        circle = new Circle(10d, 10d, 1f);
        center = new Vector2(100f, 650f);
        radius = 10f;

        newCircle = new Circle(10d, 10d, 1f);
        otherCircle = new Circle(20d, 20d, 2f);
        selectBox = new ColourSelectBox(Colour.BLUE, 25f, 25f, 50f, 50f);
    }

    @Test
    public void returnCenterOfPlayerTest() {
        Vector2 playerCenter = circle.getCenterOfPlayer();
        assertEquals(center, playerCenter);
        assertEquals(100f, circle.getX(), 1d);
        assertEquals(650f, circle.getY(), 1d);
    }

    @Test
    public void returnRadiusTest() {
        float radiusOfCircle = circle.getRadiusOfCircle();
        assertEquals(radius, radiusOfCircle, 1d);
        assertEquals(10f, circle.getRadius(), 1d);
    }

    @Test
    public void equalCirclesTest() {
        assertEquals(circle, newCircle);
    }

    @Test
    public void TestNullCircle() {
        assertNotEquals(circle, null);
    }

    @Test
    public void TestNotEqualsCircle() {
        assertNotEquals(circle, otherCircle);
    }

    @Test
    public void TestNotInstanceOf() {
        assertNotEquals(circle, selectBox);
    }
}