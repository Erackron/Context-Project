package nl.tudelft.contextproject.core.entities;

import lombok.Data;

@Data
public class Circle {
    protected double x;
    protected double y;
    protected float radius;

    /**
     * Create a new Circle.
     *
     * @param centerX The x-coordinate of the center
     * @param centerY The y-coordinate of the center
     * @param radius  The radius of the circle
     */
    public Circle(double centerX, double centerY, float radius) {
        this.x = centerX;
        this.y = centerY;
        this.radius = radius;
    }
}
