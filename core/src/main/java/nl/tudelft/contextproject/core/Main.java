package nl.tudelft.contextproject.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import nl.tudelft.contextproject.core.input.MovementAPI;
import nl.tudelft.contextproject.core.screens.MainMenuScreen;

public class Main extends Game {
    protected static MovementAPI movementAPI;

    @Getter
    protected SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.setScreen(new MainMenuScreen(this));
    }

    /**
     * Get the singleton instance of the movement API.
     *
     * @return The singleton instance of the movement API
     */
    public static MovementAPI getMovementAPI() {
        if (movementAPI == null) {
            synchronized (MovementAPI.class) {
                if (movementAPI == null) {
                    movementAPI = new MovementAPI();
                }
            }
        }
        return movementAPI;
    }
}
