package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ike on 6-5-2015.
 * This class represents the player for the keyboard input layer.
 */
@Data
public class Player {

    protected Vector2 position;
    protected float radius;

    private final ColourPalette colourPalette;
    protected List<ColourSelectBox> colourSelectBoxes = new ArrayList<>();

    /**
     * Create a new Player object.
     *
     * @param colourPalette The colours the player has available
     * @param x             The x coordinate of the player
     * @param y             The y coordinate of the player
     * @param radius        The radius of the player
     */
    public Player(ColourPalette colourPalette, float x, float y, float radius) {
        this(colourPalette, new Vector2(x, y), radius);
    }

    /**
     * Create a new Player object.
     *
     * @param colourPalette The colours the player has available
     * @param position      The position of the player
     * @param radius        The radius of the player
     */
    public Player(ColourPalette colourPalette, Vector2 position, float radius) {
        this.colourPalette = colourPalette;
        this.position = position;
        this.radius = radius;
    }

    /**
     * Moves the player and his brush through 2d space by specified parameters.
     *
     * @param dx translation in x direction
     * @param dy translation in y direction
     */
    public void move(float dx, float dy) {
        position.add(dx, dy);
        checkPosition();
    }

    /**
     * Checks position of player, and changes player colour accordingly.
     */
    public void checkPosition() {
        for (ColourSelectBox box : colourSelectBoxes) {
            if (box.inBox(position)) {
                colourPalette.setColour(box.getColour());
                break;
            }
        }
    }
}
