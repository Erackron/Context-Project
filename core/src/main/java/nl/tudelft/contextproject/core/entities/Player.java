package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.positioning.Coordinate;

/**
 * Created by Ike on 6-5-2015.
 */
public class Player {
    private Vector2 position;
    private Vector2 brushPosition;
    private float radius;

    public Player() {
        position = new Vector2(100, 100);
        radius = 50;
        brushPosition = new Vector2(100 + radius, 100);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getBrushPosition() {
        return brushPosition;
    }

    public float getRadius() {
        return radius;
    }
}
