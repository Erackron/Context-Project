package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.entities.MenuButton;

/**
 * The main menu screen.
 */
public class MainMenuScreen implements Screen {
    protected final Main main;
    protected Stage stage;
    protected SpriteBatch spriteBatch;
    protected MenuButton playButton;

    /**
     * Create a new main menu screen.
     * @param main The main game object for which this screen is created.
     */
    public MainMenuScreen(final Main main) {
        this.main = main;
        create();
    }

    /**
     * Create the actual main menu.
     */
    public void create() {
        spriteBatch = main.getBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create the Play button
        playButton = MenuButton.createMenuButton("PLAY");
        playButton.setPosition(270, 100);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.setScreen(GameScreen.createDefaultGameScreen(main));
            }
        });
        stage.addActor(playButton);

        Texture img = new Texture(Gdx.files.internal("conceptlogo1.png"));
        Image actor = new Image(img);
        actor.setPosition(190, 210);
        stage.addActor(actor);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        // Unnecessary, but required listener method
    }

    @Override
    public void hide() {
        // Unnecessary, but required listener method
    }

    @Override
    public void pause() {
        // Unnecessary, but required listener method
    }

    @Override
    public void resume() {
        // Unnecessary, but required listener method
    }

    @Override
    public void dispose() {
        stage.dispose();
        playButton.getSkin().dispose();
    }
}
