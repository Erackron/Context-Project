package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.input.KeyboardInputProcessor;
import nl.tudelft.contextproject.core.input.MovementAPI;
import nl.tudelft.contextproject.core.input.PlayerMovement;
import nl.tudelft.contextproject.core.rendering.DrawablePixmap;

/**
 * The Game screen. This is the canvas we paint on.
 */
public class GameScreen implements Screen {
    protected OrthographicCamera camera;
    protected ShapeRenderer shapeRenderer;
    protected DrawablePixmap drawing;
    protected DrawablePixmap drawing2;
    protected SpriteBatch batch;
    protected final Main main;
    protected MovementAPI movementAPI;
    protected KeyboardInputProcessor inputProcessor;
    protected Player player;
    protected Player player2;

    /**
     * Create a new game screen.
     *
     * @param main The main game object for which this screen is created
     */
    public GameScreen(final Main main) {
        this.main = main;
        ColourPalette palette = ColourPalette.standardPalette();
        player = new Player(palette, 100f, 100f);
        player2 = new Player(palette, 200f, 100f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        drawing = new DrawablePixmap(camera, player);
        drawing2 = new DrawablePixmap(camera, player2);
        batch = main.getBatch();

        movementAPI = MovementAPI.getMovementAPI();
        inputProcessor = new KeyboardInputProcessor(player, player2);
        Gdx.input.setInputProcessor(inputProcessor);


        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();

        // Update the input processor
        inputProcessor.update(delta);

        // Draw player status
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Vector2 playerPos = player.getPosition();
        Vector2 brushPos = player.getBrushPosition();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(playerPos.x, playerPos.y, 10);
        shapeRenderer.setColor(player.getColourPalette().getCurrentColour().getColor());
        shapeRenderer.circle(brushPos.x, brushPos.y, 2);
        shapeRenderer.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Vector2 playerPos2 = player2.getPosition();
        Vector2 brushPos2 = player2.getBrushPosition();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(playerPos2.x, playerPos2.y, 10);
        shapeRenderer.setColor(player2.getColourPalette().getCurrentColour().getColor());
        shapeRenderer.circle(brushPos2.x, brushPos2.y, 2);
        shapeRenderer.end();


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        drawing.getPainting().setColor(player.getColourPalette().getCurrentColour().getColor());
        PlayerMovement movement = movementAPI.nextMovement();
        while (movement != null) {
            drawing.drawLine(movement.getStartOfMovement(), movement.getEndOfMovement());
            movement = movementAPI.nextMovement();
        }

        drawing2.getPainting().setColor(player.getColourPalette().getCurrentColour().getColor());
        PlayerMovement movement2 = movementAPI.nextMovement();
        while (movement2 != null) {
            drawing2.drawLine(movement2.getStartOfMovement(), movement2.getEndOfMovement());
            movement2 = movementAPI.nextMovement();
        }

        // Update drawing if needed
        drawing.update();
        drawing2.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(drawing.getCanvas(), 0, 0);
        batch.draw(drawing2.getCanvas(), 0, 0);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(player.getColourPalette().getCurrentColour().getColor());
        shapeRenderer.rect(800, 100, 100, 100);
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {

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
        drawing.dispose();
        shapeRenderer.dispose();
    }
}