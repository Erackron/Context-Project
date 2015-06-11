package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Mitchell on 11-6-2015.
 */
public class ColourSelectBoxTest {

    private ColourSelectBox selectBox;

    @Before
    public void Setup(){
        selectBox = new ColourSelectBox(Colour.BLUE, 25f, 25f, 50f, 50f);
    }

    @Test
    public void TestConstuctor(){
        Vector3 bottomLeft = new Vector3(25f, 25f, 0);
        Vector3 topRight = new Vector3(50f, 50f, 0);
        assertTrue(selectBox.getBoundingBox().contains(bottomLeft));
        assertTrue(selectBox.getBoundingBox().contains(topRight));
    }

    @Test
    public void TestColour(){
        assertEquals(Colour.BLUE, selectBox.getColour());
    }

    @Test
    public void TestInBox(){
        Vector2 inBox = new Vector2(30f, 30f);
        assertTrue(selectBox.inBox(inBox));
    }

    @Test
    public void TestNotInBox(){
        Vector2 outBox = new Vector2(70f, 70f);
        assertFalse(selectBox.inBox(outBox));
    }
}
