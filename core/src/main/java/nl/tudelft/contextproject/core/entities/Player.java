package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.Data;

/**
 * Created by Ike on 6-5-2015.
 * This class represents the player for the keyboard input layer.
 */
@Data
public class Player {

    protected final Vector2 position;

    protected final Vector2 brushPosition;

    protected float radius;

    protected double angle;

    private final ColourPalette colourPalette;


    /**
     * Create a new Player object.
     * @param colourPalette the colours the player has available.
     */
    public Player(ColourPalette colourPalette, float x, float y) {
        this.colourPalette = colourPalette;
        position = new Vector2(x, y);
        radius = 50f;
        angle = 0.0;
        brushPosition = new Vector2(x + radius, y);
    }

    protected Player(ColourPalette colourPalette,Vector2 position, Vector2 brushPosition,
                     float angle) {
        this.colourPalette = colourPalette;
        this.position = position;
        this.brushPosition = brushPosition;
        radius = 50f;
        this.angle = angle;
    }

    /**
     * Method used to turn the player's brush around.
     *
     * @param a  The angle to turn around in radians
     * @param dt The time that has passed since the last render in radians
     */
    public void turnBrush(double a, float dt) {
        double angle = addAngle(a * dt);

        float newX = (float) Math.cos(angle) * radius + position.x;
        float newY = (float) Math.sin(angle) * radius + position.y;

        brushPosition.set(newX, newY);

    }


    public double addAngle(double angleDelta) {
        angle += angleDelta;
        return angle;
    }

    /**
     * change the radius of the brush.
     * @param r is the value with which the radius is changed
     */
    public void changeRadius(float r) {
        if (radius + r > 250) {
            radius = 250;
        } else if (radius + r < 10) {
            radius = 10;
        } else {
            radius += r;
        }
    }

    /**
     * Moves the player and his brush through 2d space by specified parameters.
     * @param dx translation in x direction
     * @param dy translation in y direction
     */
    public void move(float dx, float dy) {
        position.add(dx,dy);
        brushPosition.add(dx,dy);
        checkPosition();
    }

    public void setRed(){
        colourPalette.setCurrent(0);
    }

    public void setBlue(){
        colourPalette.setCurrent(1);
    }

    public void setYellow(){
        colourPalette.setCurrent(2);
    }

    public void checkPosition(){
        if(position.x > 9f && position.x < 61f){
            if(position.y > 99f && position.y < 151f){
                setRed();
            }
            if(position.y > 199f && position.y < 251f){
                setBlue();
            }
            if(position.y > 299f && position.y < 351f){
                setYellow();
            }

        }
    }
}
