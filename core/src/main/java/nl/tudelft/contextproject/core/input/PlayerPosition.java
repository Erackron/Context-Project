package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;

/**
 * The PlayerPosition interface which can be implemented by an input layer wishing to communicate
 * player positions with the core layer.
 */
public interface PlayerPosition {
    /**
     * Get the coordinates of the player.
     * This is used to identify which player this movement belongs to.
     *
     * @return The coordinates of the player's center
     */
    Vector2 getCenterOfPlayer();

    /**
     * Get the coordinates where the movement started.
     *
     * @return The start coordinates
     */
    float getRadiusOfCircle();
}
