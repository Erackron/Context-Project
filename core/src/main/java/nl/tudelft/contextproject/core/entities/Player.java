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

    @Getter
    protected float opacity;

    /**
     * Create a new Player object.
     */
    public Player() {
        position = new Vector2(100f, 100f);
        radius = 50f;
        angle = 0.0;
        brushPosition = new Vector2(100f + radius, 100f);
        brush = initializeBrush();
        opacity = 1.0f;
    }

    public double addAngle(double angleDelta) {
        angle += angleDelta;
        return angle;
    }

    public Colour initializeBrush(){
        red = Colour.RED;
        yellow = Colour.YELLOW;
        blue = Colour.BLUE;
        red.setNext(yellow);
        yellow.setNext(blue);
        blue.setNext(red);
        return red;
    }

    public void changeBrushColour(){
        brush = brush.getNext();
    }

    public void changeOpacity(float i) {
        if (opacity + i > 1) {
        } else if (opacity + i < 0.2) {
            opacity = 0.2f;
        } else {
            opacity += i;
        }
    }
}
