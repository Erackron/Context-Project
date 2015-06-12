package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mitchell on 11-6-2015.
 */
public class ColourSelectBoxTest {

    private ColourSelectBox selectBox;
    private ColourSelectBox selectBox2;
    private ColourSelectBox selectBox3;
    private Circle circle;


    @Before
    public void Setup() {
        selectBox = new ColourSelectBox(Colour.BLUE, 25f, 25f, 50f, 50f);
        selectBox2 = new ColourSelectBox(Colour.BLUE, 25f, 25f, 50f, 50f);
        selectBox3 = new ColourSelectBox(Colour.RED, 50f, 50f, 100f, 100f);
        circle = new Circle(10d, 10d, 1f);
    }

    @Test
    public void TestConstructor() {
        Vector3 bottomLeft = new Vector3(25f, 25f, 0);
        Vector3 topRight = new Vector3(50f, 50f, 0);
        assertTrue(selectBox.getBoundingBox().contains(bottomLeft));
        assertTrue(selectBox.getBoundingBox().contains(topRight));
    }

    @Test
    public void TestColour() {
        assertEquals(Colour.BLUE, selectBox.getColour());
    }

    @Test
    public void TestInBox() {
        Vector2 inBox = new Vector2(30f, 30f);
        assertTrue(selectBox.inBox(inBox));
    }

    @Test
    public void TestNotInBox() {
        Vector2 outBox = new Vector2(70f, 70f);
        assertFalse(selectBox.inBox(outBox));
    }

    @Test
    public void TestEqualsBoxes() {
        assertEquals(selectBox, selectBox2);
    }

    @Test
    public void TestNullBox() {
        assertNotEquals(selectBox, null);
    }

    @Test
    public void TestNotEqualsBoxes() {
        assertNotEquals(selectBox, selectBox3);
    }

    @Test
    public void TestNotInstanceOf() {
        assertNotEquals(selectBox, circle);
    }
}
