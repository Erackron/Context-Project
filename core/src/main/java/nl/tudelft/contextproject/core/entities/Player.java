package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

/**
 * Created by Ike on 6-5-2015.
 * This class represents the player for the keyboard input layer.
 */
public class Player {
    @Getter
    protected Vector2 position;
    @Getter
    protected Vector2 brushPosition;
    @Getter
    protected float radius;
    @Getter
    protected double angle;

    /**
     * Create a new Player object.
     */
    public Player() {
        position = new Vector2(100f, 100f);
        radius = 50f;
        angle = 0.0;
        brushPosition = new Vector2(100f + radius, 100f);
    }

    public double addAngle(double angleDelta) {
        angle += angleDelta;
        return angle;
    }
}
