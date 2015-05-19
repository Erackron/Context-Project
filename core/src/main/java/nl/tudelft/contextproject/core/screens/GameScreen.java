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

import java.util.ArrayList;
import java.util.List;

/**
 * The Game screen. This is the canvas we paint on.
 */
public class GameScreen implements Screen {
    protected OrthographicCamera camera;
    protected ShapeRenderer shapeRenderer;
    protected DrawablePixmap drawing;
    protected List<DrawablePixmap> drawings;
    protected SpriteBatch batch;
    protected final Main main;
    protected MovementAPI movementAPI;
    protected KeyboardInputProcessor inputProcessor;
    protected Player player;
    protected Player player2;
    protected Player player3;
    protected int numPlayers;
    protected int activePlayer;

    protected List<Player> players;

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
        player3 = new Player(palette, 300f, 100f);
        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
        numPlayers = players.size();
        activePlayer = 0;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        drawings = new ArrayList<>();
        for(int i = 0; i<numPlayers; i++){
            drawings.add(new DrawablePixmap(camera, players.get(i)));
        }
        batch = main.getBatch();

        movementAPI = MovementAPI.getMovementAPI();
        inputProcessor = new KeyboardInputProcessor(players);
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
        activePlayer = inputProcessor.update(delta, activePlayer);

        // Draw player status
        for(int i = 0; i<numPlayers; i++) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Vector2 playerPos = players.get(i).getPosition();
            Vector2 brushPos = players.get(i).getBrushPosition();
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(playerPos.x, playerPos.y, 10);
            shapeRenderer.setColor(players.get(i).getColourPalette().getCurrentColour().getColor());
            shapeRenderer.circle(brushPos.x, brushPos.y, 2);
            shapeRenderer.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        for(int i = 0; i<numPlayers; i++) {
            drawings.get(i).getPainting().setColor(players.get(i).getColourPalette().getCurrentColour().getColor());
            PlayerMovement movement = movementAPI.nextMovement();
            while (movement != null) {
                drawings.get(i).drawLine(movement.getStartOfMovement(), movement.getEndOfMovement());
                movement = movementAPI.nextMovement();
            }
        }

        // Update drawing if needed
        for(int i = 0; i<numPlayers; i++) {
            drawings.get(i).update();
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i<numPlayers; i++){
            batch.draw(drawings.get(i).getCanvas(), 0, 0);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(players.get(activePlayer).getColourPalette().getCurrentColour().getColor());
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