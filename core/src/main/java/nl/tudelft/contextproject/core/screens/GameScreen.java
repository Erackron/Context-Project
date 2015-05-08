package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.input.MovementAPI;
import nl.tudelft.contextproject.core.input.PlayerMovement;

/**
 * The Game screen. This is the canvas we paint on.
 */
public class GameScreen implements Screen {
    protected OrthographicCamera camera;
    protected ShapeRenderer shapeRenderer;
    protected final Main main;
    protected float elapsed;
    protected MovementAPI movementAPI;

    /**
     * Create a new game screen.
     * @param main The main game object for which this screen is created
     */
    public GameScreen(final Main main) {
        this.main = main;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();

        movementAPI = MovementAPI.getMovementAPI();
    }

    @Override
    public void render(float delta) {
        elapsed += delta;

        // Update camera
        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        Gdx.gl.glLineWidth(2);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        PlayerMovement movement = movementAPI.nextMovement();
        while (movement != null) {
            movement = movementAPI.nextMovement();
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(movement.getCenterOfPlayer().x, movement.getCenterOfPlayer().y, 2);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.line(movement.getStartOfMovement(), movement.getEndOfMovement());
        }
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
    }
}
