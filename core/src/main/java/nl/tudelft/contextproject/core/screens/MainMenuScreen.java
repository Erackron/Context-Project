package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import nl.tudelft.contextproject.core.Main;

/**
 * The main menu screen.
 */
public class MainMenuScreen implements Screen {
    protected final Main main;
    protected Stage stage;
    protected Skin skin;
    protected SpriteBatch spriteBatch;

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

        skin = new Skin();

        // Store a white texture under white
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.TEAL);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store default libgdx font under default
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        // Create a simple button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // Create the Play button
        final TextButton textButton = new TextButton("PLAY", textButtonStyle);
        textButton.setPosition(270, 100);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.setScreen(GameScreen.createDefaultGameScreen(main));
            }
        });
        stage.addActor(textButton);

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

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
