package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ike on 6-5-2015.
 * The KeyboardMovement class implements PlayerMovement to be able to communicate with the core.
 */
public class KeyboardMovement implements PlayerMovement {

    private Vector2 center;
    private Vector2 start;
    private Vector2 end;

    /**
     * Create a new KeyboardMovement instance.
     *
     * @param center The center of the player
     * @param start  The start of the movement
     * @param end    The end of the movement
     */
    public KeyboardMovement(Vector2 center, Vector2 start, Vector2 end) {
        this.center = center;
        this.start = start;
        this.end = end;
    }

    @Override
    public Vector2 getCenterOfPlayer() {
        return center;
    }

    @Override
    public Vector2 getStartOfMovement() {
        return start;
    }

    @Override
    public Vector2 getEndOfMovement() {
        return end;
    }
}
