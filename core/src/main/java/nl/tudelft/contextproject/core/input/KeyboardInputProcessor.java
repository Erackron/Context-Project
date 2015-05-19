package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.*;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the
 * movement API.
 */
public class KeyboardInputProcessor extends InputAdapter {

    public static final float PIXELS_PER_UPDATE = 75.0f;
    public static final double ANGLE = Math.PI / 2.0;

    protected HashMap<Integer, Boolean> keys;
    protected boolean toggled;
    protected boolean toggle1;
    protected boolean toggle2;
    protected boolean toggle3;
    protected List<Player> players;
    protected int numPlayers;
    protected int activePlayer;
    protected float[] deltaMovement = new float[2];

    protected Vector2 start;
    protected Vector2 end;
    protected Vector2 center;

    /**
     * Create a new KeyboardInputProcesser.
     */
    public KeyboardInputProcessor(List<Player> players) {
        this.players = players;
        numPlayers = players.size();
        keys = new HashMap<>();
        toggled = false;
        toggle1 = false;
        toggle2 = false;

        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.UP, false);
        keys.put(Input.Keys.DOWN, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.C, false);
        keys.put(Input.Keys.NUM_1, false);
        keys.put(Input.Keys.NUM_2, false);
        keys.put(Input.Keys.NUM_3, false);
    }

    /**
     * Method that gets called by the main game loop to actually process the keyboard input.
     *
     * @param dt Time that has elapsed since the previous render.
     */
    public int update(float dt, int activePlayer) {
        this.activePlayer = activePlayer;
        deltaMovement[0] = deltaMovement[1] = 0;
        if (isPressed(Input.Keys.W)) {
            deltaMovement[1] = PIXELS_PER_UPDATE * dt;
            if (players.get(activePlayer).getPosition().y + deltaMovement[1] > Constants.CAM_HEIGHT) {
                deltaMovement[1] = Constants.CAM_HEIGHT - players.get(activePlayer).getPosition().y + deltaMovement[1];
            }

        }
        if (isPressed(Input.Keys.S)) {
            deltaMovement[1] = -PIXELS_PER_UPDATE * dt;
            if (players.get(activePlayer).getPosition().y + deltaMovement[1] < 0) {
                deltaMovement[1] = -players.get(activePlayer).getPosition().y;
            }

        }
        if (isPressed(Input.Keys.A)) {
            deltaMovement[0] = -PIXELS_PER_UPDATE * dt;
            if (players.get(activePlayer).getPosition().x + deltaMovement[0] < 0) {
                deltaMovement[0] = -players.get(activePlayer).getPosition().x;
            }
        }
        if (isPressed(Input.Keys.D)) {
            deltaMovement[0] = PIXELS_PER_UPDATE * dt;
            if (players.get(activePlayer).getPosition().x + deltaMovement[0] > Constants.CAM_WIDTH) {
                deltaMovement[0] = Constants.CAM_WIDTH - players.get(activePlayer).getPosition().x + deltaMovement[0];
            }

        }

        players.get(activePlayer).getPosition().add(deltaMovement[0], deltaMovement[1]);
        players.get(activePlayer).getBrushPosition().add(deltaMovement[0], deltaMovement[1]);


        if (isPressed(Input.Keys.DOWN)) {
            turnBrush(ANGLE, dt);
        }

        if (isPressed(Input.Keys.UP)) {
            turnBrush(-ANGLE, dt);
        }

        if (isToggled()){
            players.get(activePlayer).getColourPalette().cycle();
            toggled = false;
        }

        if (toggle1){
            activePlayer = 0;
            toggle1 = false;
        }

        if (toggle2){
            activePlayer = 1;
            toggle2 = false;
        }

        if (toggle3){
            activePlayer = 2;
            toggle3 = false;
        }


        return activePlayer;
    }

    /**
     * Method used to turn the player's brush around.
     * @param a The angle to turn around
     * @param dt The time that has passed since the last render
     */
    public void turnBrush(double a, float dt) {
        double angle = players.get(activePlayer).addAngle(a * dt);

        float newX = (float) Math.cos(angle) * players.get(activePlayer).getRadius() + players.get(activePlayer).getPosition().x;
        float newY = (float) Math.sin(angle) * players.get(activePlayer).getRadius() + players.get(activePlayer).getPosition().y;

        players.get(activePlayer).getBrushPosition().set(newX, newY);

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
            start = (players.get(activePlayer).getBrushPosition().cpy());
            keys.put(i, true);
        } else if (i == Input.Keys.C) {
            boolean b = keys.get(i);
            b = !b;
            keys.put(i, b);
            toggled = true;
        } else if (i == Input.Keys.NUM_1){
            boolean b = keys.get(i);
            b = !b;
            keys.put(i, b);
            toggle1 = true;
        } else if (i == Input.Keys.NUM_2) {
            boolean b = keys.get(i);
            b = !b;
            keys.put(i, b);
            toggle2 = true;
        } else if (i == Input.Keys.NUM_3) {
            boolean b = keys.get(i);
            b = !b;
            keys.put(i, b);
            toggle3 = true;
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
            center = players.get(activePlayer).getPosition().cpy();
            end = players.get(activePlayer).getBrushPosition().cpy();
            MovementAPI.getMovementAPI().addMovement(new KeyboardMovement(center, start, end));
            keys.put(i, false);

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


}
