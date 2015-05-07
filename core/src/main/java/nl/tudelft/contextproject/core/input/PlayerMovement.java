package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;

public interface PlayerMovement {
    Vector2 getCenterOfPlayer();

    Vector2 getStartOfMovement();

    Vector2 getEndOfMovement();
}
