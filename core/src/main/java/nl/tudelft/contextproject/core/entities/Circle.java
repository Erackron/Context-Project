package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import nl.tudelft.contextproject.core.input.PlayerPosition;

@Data
public class Circle implements PlayerPosition {
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

    @Override
    public Vector2 getCenterOfPlayer() {
        return new Vector2((float) x, (float) y);
    }

    @Override
    public float getRadiusOfCircle() {
        return radius;
    }
}
