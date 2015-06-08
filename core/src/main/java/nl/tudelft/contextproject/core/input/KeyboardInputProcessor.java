package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.HashMap;
import java.util.List;

/**
 * Class that is used to process keyboard input and to create KeyBoardMovement objects for the
 * movement API.
 */
public class KeyboardInputProcessor extends InputAdapter {

    public static final float PIXELS_PER_UPDATE = 75.0f;

    protected HashMap<Integer, Boolean> keys;
    protected boolean toggled;
    protected boolean[] playerToggles = new boolean[9];
    protected boolean paintToggled;
    protected List<Player> players;
    protected int numPlayers;
    protected int activePlayerId;
    protected float[] deltaMovement = new float[2];
    protected Vector2 center;


    /**
     * Constructor to inject a keyMap
     * @param players list of players to participate in the game.
     * @param keyMap  map of keys that has been pressed in the current cycle.
     */
    protected KeyboardInputProcessor(List<Player> players, HashMap<Integer,Boolean> keyMap) {
        this.players = players;
        numPlayers = players.size();
        keys = keyMap;
        toggled = false;

        keys.put(Input.Keys.W, false);
        keys.put(Input.Keys.S, false);
        keys.put(Input.Keys.A, false);
        keys.put(Input.Keys.D, false);
        keys.put(Input.Keys.SPACE, false);
        keys.put(Input.Keys.C, false);
        for (int i = Input.Keys.NUM_1; i <= Input.Keys.NUM_9; i++) {
            keys.put(i, false);
        }
    }

    /**
     * Create a new KeyboardInputProcessor.
     */
    public KeyboardInputProcessor(List<Player> players) {
        this(players, new HashMap<>());
    }

    /**
     * Calculate the movement for the player in a certain direction.
     *
     * @param direction The Direction we are moving in.
     * @param dt        Time that has elapsed since the previous render.
     */
    public void move(Direction direction, float dt) {
        deltaMovement[direction.getAxis()] = direction.getDirection() * PIXELS_PER_UPDATE * dt;
        direction.checkBounds(players.get(activePlayerId).getPosition(), deltaMovement);
    }


    /**
     * Method that gets called by the main game loop to actually process the keyboard input.
     *
     * @param dt Time that has elapsed since the previous render.
     */
    public int update(float dt, int activePlayerId) {
        this.activePlayerId = activePlayerId;
        final Player activePlayer = players.get(this.activePlayerId);
        deltaMovement[0] = deltaMovement[1] = 0;

        if (isPressed(Input.Keys.W)) {
            move(Direction.NORTH, dt);
            paintDraw(isPaintToggled());
        }

        if (isPressed(Input.Keys.S)) {
            move(Direction.SOUTH, dt);
            paintDraw(isPaintToggled());
        }

        if (isPressed(Input.Keys.A)) {
            move(Direction.WEST, dt);
            paintDraw(isPaintToggled());
        }

        if (isPressed(Input.Keys.D)) {
            move(Direction.EAST, dt);
            paintDraw(isPaintToggled());
        }

        activePlayer.move(deltaMovement[0],deltaMovement[1]);

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
     * Method that gets called when a keys gets pressed.
     *
     * @param i The keycode of the key that was pressed
     * @return Return true to indicate we handled the key event
     */
    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SPACE:
                keys.put(i, !keys.get(i));
                paintToggled = !paintToggled;
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
        if (keys.containsKey(i) && i != Input.Keys.C && i != Input.Keys.SPACE) {
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
     * Method that enables drawing option.
     * @param drawOn Boolean that indicates the draw toggle.
     */
    public void paintDraw(boolean drawOn) {
        if (drawOn) {
            center = players.get(activePlayerId).getPosition().cpy();
            PlayerAPI.getPlayerApi().addPosition(new KeyboardPosition(center, 12f));
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public boolean isPaintToggled() {
        return paintToggled;
    }
}

