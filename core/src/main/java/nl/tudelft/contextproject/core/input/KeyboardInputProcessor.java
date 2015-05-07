package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.positioning.Coordinate;

import java.util.HashMap;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the movement API
 */
public class KeyboardInputProcessor extends InputAdapter{

    public static double PIXELSPERUPDATE = 1.0/25.0;
    public static double ANGLE = 9.0/10.0;

    private HashMap<Integer, Boolean> keys;
    private Player player;

    private Vector2 start;
    private Vector2 end;
    private Vector2 center;

    public KeyboardInputProcessor() {
        player = new Player();
        keys = new HashMap<Integer, Boolean>();

        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.UP, false);
        keys.put(Input.Keys.DOWN, false);
        keys.put(Input.Keys.SPACE, false);
    }

    /**
     * Method that gets called by the main game loop to actually process the keyboard input.
     * @param dt Time that has elapsed since the previous render.
     */
    public void update(float dt) {
        if (isPressed(Input.Keys.W)) {
            player.getPosition().add(0, (float) PIXELSPERUPDATE * dt);
            player.getBrushPosition().add(0, (float) PIXELSPERUPDATE * dt);
        }
        if (isPressed(Input.Keys.S)) {
            player.getPosition().add(0, (float) -PIXELSPERUPDATE * dt);
            player.getBrushPosition().add(0, (float)- PIXELSPERUPDATE * dt);
        }
        if (isPressed(Input.Keys.A)) {
            player.getPosition().add((float) -PIXELSPERUPDATE * dt, 0);
            player.getBrushPosition().add((float) -PIXELSPERUPDATE * dt, 0);
        }
        if (isPressed(Input.Keys.D)) {
            player.getPosition().add((float) PIXELSPERUPDATE * dt, 0);
            player.getBrushPosition().add((float) PIXELSPERUPDATE * dt, 0);
        }

        if (isPressed(Input.Keys.DOWN)) {
            double angle = ANGLE * dt;

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }

        if (isPressed(Input.Keys.UP)) {
            double angle = -ANGLE * dt;

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }

        System.out.println("(" + player.getBrushPosition().x + ", " + player.getBrushPosition().y + ")");
    }

    /**
     * Method that gets called when a keys gets pressed.
     * @param i The keycode of the key that was pressed.
     * @return
     */
    @Override
    public boolean keyDown(int i) {

        if (i == Input.Keys.SPACE) {
            start = player.getBrushPosition();
            keys.put(i, true);
        } else if (keys.containsKey(i)) {
            keys.put(i, true);
        }

        return true;
    }

    /**
     * Method that gets called when a key gets released.
     * @param i The keycode of the key that was released.
     * @return
     */
    @Override
    public boolean keyUp(int i) {

        if (i == Input.Keys.SPACE) {
            end = player.getBrushPosition();
            keys.put(i, false);
            //send PlayerMovement object here;

        } else if (keys.containsKey(i)) {
            keys.put(i, false);
        }

        return true;
    }

    /**
     * Method that returns if a key is currently pressed.
     * @param key The keycode of the key that needs to be checked.
     * @return
     */
    public boolean isPressed(int key) {
        return keys.get(key);
    }

    public Player getPlayer() {
        return player;
    }

}