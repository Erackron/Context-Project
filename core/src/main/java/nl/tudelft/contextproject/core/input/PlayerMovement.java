package nl.tudelft.contextproject.core.input;

import nl.tudelft.contextproject.core.positioning.Coordinate;

public interface PlayerMovement {
    Coordinate getCenterOfPlayer();

    Coordinate getStartOfMovement();

    Coordinate getEndOfMovement();
}
