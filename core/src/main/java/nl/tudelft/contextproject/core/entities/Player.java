package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter
    protected Colour brush;

    protected Colour red;
    protected Colour yellow;
    protected Colour blue;


    /**
     * Create a new Player object.
     */
    public Player() {
        position = new Vector2(100f, 100f);
        radius = 50f;
        angle = 0.0;
        brushPosition = new Vector2(100f + radius, 100f);
        red = new Colour(Color.RED);
        yellow = new Colour(Color.YELLOW);
        blue = new Colour(Color.BLUE);
        red.setNext(yellow);
        yellow.setNext(blue);
        blue.setNext(red);
        brush = red;
    }

    public double addAngle(double angleDelta) {
        angle += angleDelta;
        return angle;
    }
}
