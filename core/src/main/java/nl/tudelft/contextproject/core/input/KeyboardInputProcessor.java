package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.HashMap;
import java.util.List;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the
 * movement API.
 */
public class KeyboardInputProcessor extends InputAdapter {

    public static final float PIXELS_PER_UPDATE = 75.0f;
    public static final double ANGLE = Math.PI / 2.0;

    protected HashMap<Integer, Boolean> keys;
    protected boolean toggled;
    protected boolean[] playerToggles = new boolean[9];
    protected List<Player> players;
    protected int numPlayers;
    protected int activePlayerId;
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

        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.UP, false);
        keys.put(Input.Keys.DOWN, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.C, false);
        for (int i = Input.Keys.NUM_1; i <= Input.Keys.NUM_9; i++) {
            keys.put(i, false);
        }
    }

    /**
     * Method that gets called by the main game loop to actually process the keyboard input.
     *
     * @param dt Time that has elapsed since the previous render.
     */
    public int update(float dt, int activePlayerId) {
        this.activePlayerId = activePlayerId;
        Player activePlayer = players.get(this.activePlayerId);
        deltaMovement[0] = deltaMovement[1] = 0;

        if (isPressed(Input.Keys.W)) {
            deltaMovement[1] = PIXELS_PER_UPDATE * dt;
            if (activePlayer.getPosition().y + deltaMovement[1] > Constants.CAM_HEIGHT) {
                deltaMovement[1] += Constants.CAM_HEIGHT - activePlayer.getPosition().y;
            }

        }

        if (isPressed(Input.Keys.S)) {
            deltaMovement[1] = -PIXELS_PER_UPDATE * dt;
            if (activePlayer.getPosition().y + deltaMovement[1] < 0) {
                deltaMovement[1] -= activePlayer.getPosition().y;
            }

        }
        if (isPressed(Input.Keys.A)) {
            deltaMovement[0] = -PIXELS_PER_UPDATE * dt;
            if (activePlayer.getPosition().x + deltaMovement[0] < 0) {
                deltaMovement[0] -= activePlayer.getPosition().x;
            }
        }
        if (isPressed(Input.Keys.D)) {
            deltaMovement[0] = PIXELS_PER_UPDATE * dt;
            if (activePlayer.getPosition().x + deltaMovement[0] > Constants.CAM_WIDTH) {
                deltaMovement[0] += Constants.CAM_WIDTH - activePlayer.getPosition().x;
            }

        }

        activePlayer.getPosition().add(deltaMovement[0], deltaMovement[1]);
        activePlayer.getBrushPosition().add(deltaMovement[0], deltaMovement[1]);


        if (isPressed(Input.Keys.DOWN)) {
            turnBrush(ANGLE, dt);
        }

        if (isPressed(Input.Keys.UP)) {
            turnBrush(-ANGLE, dt);
        }

        if (isToggled()) {
            activePlayer.getColourPalette().cycle();
            toggled = false;
        }

        for (int i = 0; i < playerToggles.length; i++) {
            if (playerToggles[i]) {
                activePlayerId = i;
                playerToggles[i] = false;
            }
        }

        return activePlayerId;
    }

    /**
     * Method used to turn the player's brush around.
     *
     * @param a  The angle to turn around
     * @param dt The time that has passed since the last render
     */
    public void turnBrush(double a, float dt) {
        double angle = players.get(activePlayerId).addAngle(a * dt);

        float newX = (float) Math.cos(angle) * players.get(activePlayerId).getRadius() + players
                .get(activePlayerId).getPosition().x;
        float newY = (float) Math.sin(angle) * players.get(activePlayerId).getRadius() + players
                .get(activePlayerId).getPosition().y;

        players.get(activePlayerId).getBrushPosition().set(newX, newY);

    }

    /**
     * Method that gets called when a keys gets pressed.
     *
     * @param i The keycode of the key that was pressed
     * @return Return true to indicate we handled the key event
     */
    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SPACE:
                start = (players.get(activePlayerId).getBrushPosition().cpy());
                keys.put(i, true);
                break;
            case Input.Keys.C:
                keys.put(i, !keys.get(i));
                toggled = true;
                break;
            default:
                if (i >= Input.Keys.NUM_1 && i <= Input.Keys.NUM_9) {
                    keys.put(i, !keys.get(i));
                    playerToggles[i - Input.Keys.NUM_1] = true;
                } else if (keys.containsKey(i)) {
                    keys.put(i, true);
                }
                break;
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
            center = players.get(activePlayerId).getPosition().cpy();
            end = players.get(activePlayerId).getBrushPosition().cpy();
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

    public boolean isToggled() {
        return toggled;
    }
}
