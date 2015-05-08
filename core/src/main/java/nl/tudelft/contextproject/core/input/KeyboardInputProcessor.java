package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.HashMap;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the
 * movement API.
 */
public class KeyboardInputProcessor extends InputAdapter {

    public static final double PIXELS_PER_UPDATE = 50.0;
    public static final double ANGLE = Math.PI / 2.0;

    protected HashMap<Integer, Boolean> keys;
    protected Player player;

    protected Vector2 start;
    protected Vector2 end;
    protected Vector2 center;

    /**
     * Create a new KeyboardInputProcesser.
     */
    public KeyboardInputProcessor() {
        player = new Player();
        keys = new HashMap<>();
        MovementAPI.getMovementAPI();

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
     *
     * @param dt Time that has elapsed since the previous render.
     */
    public void update(float dt) {
        if (isPressed(Input.Keys.W)) {
            player.getPosition().add(0, (float) PIXELS_PER_UPDATE * dt);
            player.getBrushPosition().add(0, (float) PIXELS_PER_UPDATE * dt);
        }
        if (isPressed(Input.Keys.S)) {
            player.getPosition().add(0, (float) -PIXELS_PER_UPDATE * dt);
            player.getBrushPosition().add(0, (float) -PIXELS_PER_UPDATE * dt);
        }
        if (isPressed(Input.Keys.A)) {
            player.getPosition().add((float) -PIXELS_PER_UPDATE * dt, 0);
            player.getBrushPosition().add((float) -PIXELS_PER_UPDATE * dt, 0);
        }
        if (isPressed(Input.Keys.D)) {
            player.getPosition().add((float) PIXELS_PER_UPDATE * dt, 0);
            player.getBrushPosition().add((float) PIXELS_PER_UPDATE * dt, 0);
        }

        if (isPressed(Input.Keys.DOWN)) {
            double angle = player.addAngle(ANGLE * dt);

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }

        if (isPressed(Input.Keys.UP)) {
            double angle = player.addAngle(-ANGLE * dt);

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }
    }

    /**
     * Method that gets called when a keys gets pressed.
     *
     * @param i The keycode of the key that was pressed
     * @return Return true to indicate we handled the key event
     */
    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.SPACE) {
            start = player.getBrushPosition().cpy();
            keys.put(i, true);
        } else if (keys.containsKey(i)) {
            keys.put(i, true);
        }

        return true;
    }

    /**
     * Method that gets called when a key gets released.
     *
     * @param i The keycode of the key that was released
     * @return Return true to indicate we handled the key event
     */
    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.SPACE) {
            center = player.getPosition().cpy();
            end = player.getBrushPosition().cpy();
            keys.put(i, false);
            MovementAPI.getMovementAPI().addMovement(new KeyboardMovement(center, start, end));

        } else if (keys.containsKey(i)) {
            keys.put(i, false);
        }

        return true;
    }

    /**
     * Method that returns if a key is currently pressed.
     *
     * @param key The keycode of the key that needs to be checked
     * @return Whether the specified key is currently pressed.
     */
    public boolean isPressed(int key) {
        return keys.get(key);
    }

    /**
     * Get the player object.
     *
     * @return The player object
     */
    public Player getPlayer() {
        return player;
    }

}
