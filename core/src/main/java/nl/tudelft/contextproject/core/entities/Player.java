package nl.tudelft.contextproject.core.entities;

import nl.tudelft.contextproject.core.positioning.Coordinate;

/**
 * Created by Ike on 6-5-2015.
 */
public class Player {
    private Coordinate position;
    private Coordinate brushPosition;

    public Player() {
        position = new Coordinate(100, 100);
        brushPosition = new Coordinate(150, 100);
    }

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate getBrushPosition() {
        return brushPosition;
    }
}
