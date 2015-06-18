package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import nl.tudelft.contextproject.core.config.Constants;

/**
 * Enum to encapsulate paramaters for direction in which the player can move.
 */
public enum Direction {
    NORTH(1, 1, (d, p, movement) -> {
        if (p.y + movement[d.axis] > Constants.CAM_HEIGHT) {
            movement[d.axis] = Constants.CAM_HEIGHT - p.y;
        }
    }),
    WEST(0, -1, (d, p, movement) -> {
        if (p.x + movement[d.axis] < 0) {
            movement[d.axis] = -p.x;
        }
    }),
    SOUTH(1, -1, (d, p, movement) -> {
        if (p.y + movement[d.axis] < 0) {
            movement[d.axis] = -p.y;
        }
    }),
    EAST(0, 1, (d, p, movement) -> {
        if (p.x + movement[d.axis] > Constants.CAM_WIDTH) {
            movement[d.axis] = Constants.CAM_WIDTH - p.x;
        }
    });

    @Getter
    private final int axis;
    @Getter
    private final float direction;
    private final BoundsChecker checker;

    Direction(int axis, float direction, BoundsChecker checker) {
        this.axis = axis;
        this.direction = direction;
        this.checker = checker;
    }

    /**
     * Check the bounds of the movement and clip it to the edge if needed.
     *
     * @param position  the position of the player
     * @param movements the deltamovements of the current cycle
     */
    public void checkBounds(Vector2 position, float[] movements) {
        checker.checkBounds(this, position, movements);
    }
}
