package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import lombok.Data;
import nl.tudelft.contextproject.core.playertracking.LineSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ike on 6-5-2015.
 * This class represents the player for the player tracking.
 */
@Data
public class Player {

    protected Vector2 position;
    protected Vector2 direction;
    protected BoundingBox boundingBox;
    protected Vector3 minimumBox;
    protected Vector3 maximumBox;
    protected LineSize lineSize;
    protected float radius;
    protected int playerIndex;

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
        this.lineSize = LineSize.getLineSize((int) radius);
        this.radius = radius;
        this.direction = new Vector2(Vector2.Zero);

        minimumBox = new Vector3(position.x - radius, position.y - radius, 0);
        maximumBox = new Vector3(position.x + radius, position.y + radius, 1);
        this.boundingBox = new BoundingBox(minimumBox, maximumBox);
    }


    /**
     * Checks position of player, and changes player colour accordingly.
     */
    public void checkPosition() {
        for (ColourSelectBox box : colourSelectBoxes) {
            if (box.inBox(boundingBox)) {
                colourPalette.setColour(box.getColour());
                break;
            }
        }
    }

    /**
     * Move the Player to another position.
     * This also updates the direction vector.
     *
     * @param newCenter The new positions of the player
     */
    public void moveTo(Vector2 newCenter) {
        direction.x = newCenter.x - position.x;
        direction.y = newCenter.y - position.y;
        position.set(newCenter);
        minimumBox.set(position.x - radius, position.y - radius, 0);
        maximumBox.set(position.x + radius, position.y + radius, 1);
        boundingBox.set(minimumBox, maximumBox);
        checkPosition();
    }

    /**
     * Get the expected position of the player.
     * This adds the previous movement direction to the current position
     *
     * @return The expected position
     */
    public Vector2 getExpectedPosition() {
        return new Vector2(position).add(direction);
    }
}
