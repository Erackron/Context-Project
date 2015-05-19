package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.HashMap;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the
 * movement API.
 */
public class KeyboardInputProcessor extends InputAdapter {

    public static final float PIXELS_PER_UPDATE = 75.0f;
    public static final double ANGLE = Math.PI / 2.0;

    protected HashMap<Integer, Boolean> keys;
    protected boolean toggled;
    protected Player player;
    protected float[] deltaMovement = new float[2];

    protected Vector2 start;
    protected Vector2 end;
    protected Vector2 center;

    protected float opacityAngle = 0.5f;
    protected float deltaRadius = 50f;

    /**
     * Create a new KeyboardInputProcessor.
     */
    public KeyboardInputProcessor(Player player) {
        this.player = player;
        keys = new HashMap<>();
        toggled = false;

        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.UP, false);
        keys.put(Input.Keys.DOWN, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.C, false);
        keys.put(Input.Keys.LEFT, false);
        keys.put(Input.Keys.RIGHT, false);
        keys.put(Input.Keys.N, false);
        keys.put(Input.Keys.M, false);
    }

    /**
     * Method that gets called by the main game loop to actually process the keyboard input.
     *
     * @param dt Time that has elapsed since the previous render.
     */
    public void update(float dt) {
        deltaMovement[0] = deltaMovement[1] = 0;
        if (isPressed(Input.Keys.W)) {
            deltaMovement[1] = PIXELS_PER_UPDATE * dt;
            if (player.getPosition().y + deltaMovement[1] > Constants.CAM_HEIGHT) {
                deltaMovement[1] = Constants.CAM_HEIGHT - player.getPosition().y + deltaMovement[1];
            }
        }
        if (isPressed(Input.Keys.S)) {
            deltaMovement[1] = -PIXELS_PER_UPDATE * dt;
            if (player.getPosition().y + deltaMovement[1] < 0) {
                deltaMovement[1] = -player.getPosition().y;
            }
        }
        if (isPressed(Input.Keys.A)) {
            deltaMovement[0] = -PIXELS_PER_UPDATE * dt;
            if (player.getPosition().x + deltaMovement[0] < 0) {
                deltaMovement[0] = -player.getPosition().x;
            }
        }
        if (isPressed(Input.Keys.D)) {
            deltaMovement[0] = PIXELS_PER_UPDATE * dt;
            if (player.getPosition().x + deltaMovement[0] > Constants.CAM_WIDTH) {
                deltaMovement[0] = Constants.CAM_WIDTH - player.getPosition().x + deltaMovement[0];
            }
        }

        player.getPosition().add(deltaMovement[0], deltaMovement[1]);
        player.getBrushPosition().add(deltaMovement[0], deltaMovement[1]);

        if (isPressed(Input.Keys.DOWN)) {
            turnBrush(ANGLE, dt);
        }

        if (isPressed(Input.Keys.UP)) {
            turnBrush(-ANGLE, dt);
        }

        if (isPressed(Input.Keys.LEFT)) {
            player.changeOpacity(-opacityAngle * dt);
        }

        if (isPressed(Input.Keys.RIGHT)) {
            player.changeOpacity(opacityAngle * dt);
        }

        if (isPressed(Input.Keys.N)) {
            player.changeRadius(-deltaRadius * dt);
            turnBrush(0, dt);
        }

        if (isPressed(Input.Keys.M)) {
            player.changeRadius(deltaRadius * dt);
            turnBrush(0, dt);
        }

        if (isToggled()){
            player.changeBrushColour();
            toggled = false;
        }
    }

    /**
     * Method used to turn the player's brush around.
     *
     * @param a The angle to turn around
     * @param dt The time that has passed since the last render
     */
    public void turnBrush(double a, float dt) {
        double angle = player.addAngle(a * dt);

        float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
        float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

        player.getBrushPosition().set(newX, newY);
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
        } else if (i == Input.Keys.C) {
            boolean b = keys.get(i);
            b = !b;
            keys.put(i, b);
            toggled = true;
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

        } else if (keys.containsKey(i) && i != Input.Keys.C) {
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
    public boolean isToggled() { return toggled; }

    /**
     * Get the player object.
     *
     * @return The player object
     */
    public Player getPlayer() {
        return player;
    }

}
