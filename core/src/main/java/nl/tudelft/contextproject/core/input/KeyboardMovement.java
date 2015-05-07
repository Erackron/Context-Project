package nl.tudelft.contextproject.core.input;

import nl.tudelft.contextproject.core.input.PlayerMovement;
import nl.tudelft.contextproject.core.positioning.Coordinate;

/**
 * Created by Ike on 6-5-2015.
 */
public class KeyboardMovement implements PlayerMovement {

    private Coordinate center;
    private Coordinate start;
    private Coordinate end;

    public KeyboardMovement(Coordinate center, Coordinate start, Coordinate end) {
        this.center = center;
        this.start = start;
        this.end = end;
    }

    @Override
    public Coordinate getCenterOfPlayer() {
        return center;
    }

    @Override
    public Coordinate getStartOfMovement() { return start; }

    @Override
    public Coordinate getEndOfMovement() {
        return end;
    }
}
