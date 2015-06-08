package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ike on 6-5-2015.
 * The KeyboardPosition class implements PlayerPosition to be able to communicate with the core.
 */
public class KeyboardPosition implements PlayerPosition {

    private Vector2 center;
    private float radius;

    /**
     * Create a new KeyboardPosition instance.
     *
     * @param center The center of the player
     * @param radius The radius of the circle
     */
    public KeyboardPosition(Vector2 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector2 getCenterOfPlayer() {
        return center;
    }

    @Override
    public float getRadiusOfCircle() {
        return radius;
    }

}
