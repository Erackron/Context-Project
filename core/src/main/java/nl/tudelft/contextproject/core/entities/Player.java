package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;

/**
 * Created by Ike on 6-5-2015.
 * This class represents the player for the keyboard input layer.
 */
@Data
public class Player {

    protected final Vector2 position;

    protected final Vector2 brushPosition;

    protected final float radius;

    protected double angle;

    private final ColourPalette colourPalette;


    /**
     * Create a new Player object.
     * @param colourPalette the colours the player has available.
     */
    public Player(ColourPalette colourPalette) {
        this.colourPalette = colourPalette;
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
