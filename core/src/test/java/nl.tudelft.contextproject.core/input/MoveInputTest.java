package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by LC on 20/05/15.
 */
@RunWith(Parameterized.class)
public class MoveInputTest {
    private Player player;

    private KeyboardInputProcessor inputProcessor;
    private Vector2 position;

    //parameters
    private Direction direction;
    private int axis;
    private double value;
    private float x;
    private float y;
    double value2;

    private static final float NearTop = Constants.CAM_HEIGHT  - 10;
    private static final float NearRight = Constants.CAM_WIDTH - 10;

    @Before
    public void setup(){
        player =Mockito.mock(Player.class);
        position = new Vector2(300,400);
        Mockito.when(player.getPosition()).thenReturn(position);
        List<Player> players = new ArrayList<>();
        players.add(player);
        inputProcessor = new KeyboardInputProcessor(players);
    }

    @Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {Direction.NORTH,1,75d,0,NearTop,10},
                {Direction.SOUTH,1,-75d,0,10,-10},
                {Direction.EAST, 0, 75d,NearRight,0,10},
                {Direction.WEST,0,-75d,10,0,-10}
        });
    }

    public MoveInputTest(Direction d, int axis, double value,float x, float y,double value2){
        direction=d;
        this.axis=axis;
        this.value=value;
        this.x=x;
        this.y=y;
        this.value2=value2;
    }


    @Test
    public void Move(){
        inputProcessor.move(direction,1);
        assertEquals(value,inputProcessor.deltaMovement[axis],0.5);
    }

    @Test
    public void MoveBounds(){
        position.x = x;
        position.y = y;
        inputProcessor.move(direction,1);
        assertEquals(value2,inputProcessor.deltaMovement[axis],1);
    }
}
