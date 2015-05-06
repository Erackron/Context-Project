package nl.tudelft.contextproject.core.entities;

import nl.tudelft.contextproject.core.positioning.Coordinate;

/**
 * Created by Ike on 6-5-2015.
 */
public class Player {
    private Coordinate position;
    private Coordinate brushPosition;
    private double radius;

    public Player() {
        position = new Coordinate(100, 100);
        radius = 50;
        brushPosition = new Coordinate(100 + radius, 100);
    }

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate getBrushPosition() {
        return brushPosition;
    }

    public double getRadius() {
        return radius;
    }
}
