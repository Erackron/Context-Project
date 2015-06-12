package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.input.PlayerAPI;
import nl.tudelft.contextproject.core.input.PlayerPosition;

@Data
public class Circle implements PlayerPosition {
    protected double x;
    protected double y;
    protected float radius;
    protected static PlayerAPI playerAPI = PlayerAPI.getPlayerApi();

    /**
     * Create a new Circle.
     *
     * @param centerX The x-coordinate of the center
     * @param centerY The y-coordinate of the center
     * @param radius  The radius of the circle
     */
    public Circle(double centerX, double centerY, float radius) {
        Vector2 camSize = playerAPI.getCameraInputSize();
        this.x = centerX * Constants.CAM_WIDTH / camSize.x;
        this.y  = Constants.CAM_HEIGHT - (centerY * Constants.CAM_HEIGHT / camSize.y);
        this.radius = radius * Constants.CAM_WIDTH / camSize.x;
    }

    @Override
    public Vector2 getCenterOfPlayer() {
        return new Vector2((float) x, (float) y);
    }

    @Override
    public float getRadiusOfCircle() {
        return radius;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Circle) {
            Circle other = (Circle) obj;
            Vector2 position = new Vector2((float) this.getX(), (float) this.getY());
            Vector2 otherPosition = new Vector2((float) other.getX(), (float) other.getY());
            return position.equals(otherPosition)
                    && Math.abs(this.getRadius()- other.getRadius()) < 1f;
        }
        return false;
    }
}
