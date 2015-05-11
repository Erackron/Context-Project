package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
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
    protected SpriteBatch batch;
    protected final Main main;
    protected MovementAPI movementAPI;
    protected KeyboardInputProcessor inputProcessor;

    /**
     * Create a new game screen.
     *
     * @param main The main game object for which this screen is created
     */
    public GameScreen(final Main main) {
        this.main = main;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        drawing = new DrawablePixmap(camera);
        batch = main.getBatch();

        movementAPI = MovementAPI.getMovementAPI();
        inputProcessor = new KeyboardInputProcessor();
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
        Vector2 playerPos = inputProcessor.getPlayer().getPosition();
        Vector2 brushPos = inputProcessor.getPlayer().getBrushPosition();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(playerPos.x, playerPos.y, 10);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(brushPos.x, brushPos.y, 2);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        drawing.setBrushColour(Color.BLUE);
        for (int x = 0; x < Constants.CAM_WIDTH; x += 100) {
            Gdx.gl.glLineWidth(x / 100 + 1);
            shapeRenderer.line(x, 0, x, Constants.CAM_HEIGHT);
            drawing.drawLine(x, 0, x, Constants.CAM_HEIGHT);
        }
        for (int y = 0; y < Constants.CAM_HEIGHT; y += 100) {
            Gdx.gl.glLineWidth(y / 100 + 1);
            shapeRenderer.line(0, y, Constants.CAM_WIDTH, y);
            drawing.drawLine(0, y, Constants.CAM_WIDTH, y);
        }
        shapeRenderer.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        PlayerMovement movement = movementAPI.nextMovement();
        while (movement != null) {
            drawing.drawLine(movement.getStartOfMovement(), movement.getEndOfMovement());
            movement = movementAPI.nextMovement();
        }

        // Update drawing if needed
        drawing.setBrushColour(Color.RED);
        drawing.update();

        batch.begin();
        batch.draw(drawing.getCanvas(), 0, 0);
        batch.end();
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
