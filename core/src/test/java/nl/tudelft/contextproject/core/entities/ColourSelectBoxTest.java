package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
        BoundingBox inBox = new BoundingBox(new Vector3(20f, 20f, 0), new Vector3(30f, 30f, 0));
        assertTrue(selectBox.inBox(inBox));
    }

    @Test
    public void TestNotInBox() {
        BoundingBox outBox = new BoundingBox(new Vector3(51f, 51f, 0), new Vector3(70f, 70f, 0));
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
