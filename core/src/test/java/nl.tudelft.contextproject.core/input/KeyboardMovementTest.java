//package nl.tudelft.contextproject.core.input;
//
//import static org.junit.Assert.assertEquals;
//import com.badlogic.gdx.math.Vector2;
//import org.junit.Test;
//
//
//public class KeyboardMovementTest {
//    Vector2 centre = new Vector2(100,100);
//    Vector2 start = new Vector2(100,50);
//    Vector2 end = new Vector2(100,150);
//    KeyboardMovement movement = new KeyboardMovement(centre, start, end);
//
//    @Test
//    public void returnCentreTest() {
//        Vector2 playercentre = movement.getCenterOfPlayer();
//        assertEquals(centre,playercentre);
//    }
//
//    @Test
//    public void returnStartTest() {
//        Vector2 startmovement = movement.getStartOfMovement();
//        assertEquals(start,startmovement);
//    }
//
//    @Test
//    public void returnEndTest() {
//        Vector2 endmovement = movement.getEndOfMovement();
//        assertEquals(end,endmovement);
//    }
//}