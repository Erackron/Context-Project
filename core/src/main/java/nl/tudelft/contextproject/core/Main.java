package nl.tudelft.contextproject.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import nl.tudelft.contextproject.core.screens.GameScreen;

/**
 * Main game class.
 * This starts the MainMenuScreen.
 */
public class Main extends Game {
    @Getter
    protected SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.setScreen(GameScreen.createDefaultGameScreen(this));
    }
}
