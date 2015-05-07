package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.input.MovementAPI;
import nl.tudelft.contextproject.core.input.PlayerMovement;

public class GameScreen implements Screen {
    protected OrthographicCamera camera;
    protected ShapeRenderer shapeRenderer;
    protected final Main main;
    protected float elapsed;
    protected MovementAPI movementAPI;

    public GameScreen(final Main main) {
        this.main = main;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();

        movementAPI = Main.getMovementAPI();
    }

    @Override
    public void render(float delta) {
        elapsed += delta;

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
