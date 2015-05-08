package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
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

    public static boolean DRAW;

    private Player player;

    public KeyboardInputProcessor() {
        player = new Player();
    }

    public void update(float dt) {
        if (UP) {
            player.getPosition().addY(2 * dt / 50);
        }
        if (DOWN) {
            player.getPosition().addY(-2 * dt / 50);
        }
        if (LEFT) {
            player.getPosition().addX(-2 * dt / 50);
        }
        if (RIGHT) {
            player.getPosition().addX(2 * dt / 50);
        }
        if (DRAWCLOCKWISE) {
            double angle = 90 * dt / 100;

            double newX = Math.cos(angle) * player.getRadius() + player.getPosition().getX();
            double newY = Math.sin(angle) * player.getRadius() + player.getPosition().getY();

            player.getBrushPosition().setX(newX);
            player.getBrushPosition().setY(newY);
        }

        if (DRAWCOUNTERCLOCKWISE) {
            double angle = -90 * dt/ 100;

            double newX = Math.cos(angle) * player.getRadius() + player.getPosition().getX();
            double newY = Math.sin(angle) * player.getRadius() + player.getPosition().getY();

            player.getBrushPosition().setX(newX);
            player.getBrushPosition().setY(newY);
        }

        System.out.println("(" + player.getBrushPosition().getX() + ", " + player.getBrushPosition().getY() + ")");
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
            DRAW = true;
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
            DRAW = false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

}
