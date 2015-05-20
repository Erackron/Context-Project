package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by LC on 20/05/15.
 * Strategy pattern interface for boundChecking in directions.
 */
public interface BoundsChecker {
    void checkBounds(Direction d, Vector2 position, float[] deltamovements);
}
