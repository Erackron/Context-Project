package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import lombok.Data;

@Data
public class ColourSelectBox {
    protected final Colour colour;
    protected final BoundingBox boundingBox;

    /**
     * Create a new ColourSelectBox.
     *
     * @param colour      The colour to use
     * @param bottomLeftX The x coordinate of the bottom left corner
     * @param bottomLeftY The y coordinate of the bottom left corner
     * @param topRightX   The x coordinate of the top right corner
     * @param topRightY   The y coordinate of the top right corner
     */
    public ColourSelectBox(Colour colour, float bottomLeftX, float bottomLeftY, float topRightX,
                           float topRightY) {
        this(colour, new Vector2(bottomLeftX, bottomLeftY), new Vector2(topRightX, topRightY));
    }

    /**
     * Create a new ColourSelectBox.
     *
     * @param colour     The colour to use
     * @param bottomLeft The bottom left corner
     * @param topRight   The top right corner
     */
    public ColourSelectBox(Colour colour, Vector2 bottomLeft, Vector2 topRight) {
        this.colour = colour;
        this.boundingBox = new BoundingBox(new Vector3(bottomLeft.x, bottomLeft.y, 0),
                new Vector3(topRight.x, topRight.y, 1));
    }

    /**
     * Check if the player bounds intersect with this ColourSelectBox.
     *
     * @param bounds The player bounds
     * @return Whether the player intersects with this ColourSelectBox
     */
    public boolean inBox(BoundingBox bounds) {
        return boundingBox.intersects(bounds);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof ColourSelectBox) {
            ColourSelectBox other = (ColourSelectBox) obj;
            return this.getColour().equals(other.getColour())
                    && this.boundingBox.getMin().equals(other.boundingBox.getMin())
                    && this.boundingBox.getMax().equals(other.boundingBox.getMax());
        }
        return false;
    }

}
