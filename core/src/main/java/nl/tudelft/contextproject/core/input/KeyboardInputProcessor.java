package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.positioning.Coordinate;

/**
 * Created by Ike on 6-5-2015.
 */
public class KeyboardInputProcessor extends InputAdapter{

    public static boolean UP;
    public static boolean DOWN;
    public static boolean LEFT;
    public static boolean RIGHT;

    public static boolean DRAWCLOCKWISE;
    public static boolean DRAWCOUNTERCLOCKWISE;
    public static boolean DRAWING;

    private Player player;
    private Vector2 start;
    private Vector2 end;
    private Vector2 center;

    public KeyboardInputProcessor() {
        player = new Player();
    }

    public void update(float dt) {
        if (UP) {
            player.getPosition().add(0, 2 * dt / 50);
            player.getBrushPosition().add(0, 2 * dt / 50);
        }
        if (DOWN) {
            player.getPosition().add(0, -2 * dt / 50);
            player.getBrushPosition().add(0, -2 * dt / 50);
        }
        if (LEFT) {
            player.getPosition().add(-2 * dt / 50, 0);
            player.getBrushPosition().add(-2 * dt / 50, 0);
        }
        if (RIGHT) {
            player.getPosition().add(2 * dt / 50, 0);
            player.getBrushPosition().add(2 * dt / 50, 0);
        }
        if (DRAWCLOCKWISE) {
            float angle = 90 * dt / 100;

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }

        if (DRAWCOUNTERCLOCKWISE) {
            float angle = -90 * dt/ 100;

            float newX = (float) Math.cos(angle) * player.getRadius() + player.getPosition().x;
            float newY = (float) Math.sin(angle) * player.getRadius() + player.getPosition().y;

            player.getBrushPosition().set(newX, newY);
        }

        System.out.println("(" + player.getBrushPosition().x + ", " + player.getBrushPosition().y + ")");
    }

    @Override
    public boolean keyDown(int i) {

        if (i == Input.Keys.W) {
            UP = true;
        } else if (i == Input.Keys.S) {
            DOWN = true;
        } else if (i == Input.Keys.A) {
            LEFT = true;
        } else if (i == Input.Keys.D) {
            RIGHT = true;
        } else if (i == Input.Keys.DOWN) {
            DRAWCLOCKWISE = true;
        } else if (i == Input.Keys.UP) {
            DRAWCOUNTERCLOCKWISE = true;
        } else if (i == Input.Keys.SPACE) {
            DRAWING = true;
            start = player.getBrushPosition();
        }

        return true;

    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.W) {
            UP = false;
        } else if (i == Input.Keys.S) {
            DOWN = false;
        } else if (i == Input.Keys.A) {
            LEFT = false;
        } else if (i == Input.Keys.D) {
            RIGHT = false;
        } else if (i == Input.Keys.DOWN) {
            DRAWCLOCKWISE = false;
        } else if (i == Input.Keys.UP) {
            DRAWCOUNTERCLOCKWISE = false;
        } else if (i == Input.Keys.SPACE) {
            DRAWING = false;
            end = player.getBrushPosition();

            //send PlayerMovement object here;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

}
