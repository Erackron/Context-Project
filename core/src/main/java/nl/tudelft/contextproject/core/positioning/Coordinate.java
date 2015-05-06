package nl.tudelft.contextproject.core.positioning;

public class Coordinate {
    protected double x;
    protected double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
