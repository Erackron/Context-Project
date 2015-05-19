package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

/**
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

    /**
     * Method to calculate the new angle.
     * @param angleDelta The difference between the new and old angle.
     * @return Return the new angle.
     */
    public double addAngle(double angleDelta) {
        angle += angleDelta;
        return angle;
    }

    /**
     * Method that initializes the brush colour.
     * @return Return the selected colour.
     */
    public Colour initializeBrush(){
        red = Colour.RED;
        yellow = Colour.YELLOW;
        blue = Colour.BLUE;
        red.setNext(yellow);
        yellow.setNext(blue);
        blue.setNext(red);
        return red;
    }

    /**
     * Method that enables colour changing.
     */
    public void changeBrushColour(){
        brush = brush.getNext();
    }

    /**
     * Method that enables opacity change.
     *
     * @param i The requested change value of opacity.
     */
    public void changeOpacity(float i) {
        if (opacity + i > 1) {
        } else if (opacity + i < 0.2) {
            opacity = 0.2f;
        } else {
            opacity += i;
        }
    }

    public void changeRadius(float r) {
        if (radius + r > 200) {
        } else if (radius + r < 10) {
            radius = 10;
        } else {
            radius += r;
        }
    }
}
