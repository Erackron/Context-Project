package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
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
    protected DrawablePixmap draw;
    protected SpriteBatch batch;
    protected final Main main;
    protected MovementAPI movementAPI;
    protected KeyboardInputProcessor inputProcessor;
    protected int numPlayers;
    protected int activePlayer;
    protected final Texture background;
    protected List<Player> players;

    /**
     * Create a new game screen.
     *
     * @param main    The main game object for which this screen is created
     * @param players The players to add to this game screen
     */
    public GameScreen(final Main main, ArrayList<Player> players) {
        this.main = main;
        this.players = new ArrayList<>(players);
        numPlayers = players.size();
        activePlayer = 0;

        this.background = new Texture(Gdx.files.internal("sprites/List60px.png"));
        background.bind();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();

        Pixmap pixmap = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        Pixmap newPixmap = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        Texture texture = new Texture(pixmap);

        draw = new DrawablePixmap(pixmap, newPixmap, texture);
        batch = main.getBatch();

        movementAPI = MovementAPI.getMovementAPI();
        inputProcessor = new KeyboardInputProcessor(players);
        Gdx.input.setInputProcessor(inputProcessor);


        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Create a new game screen.
     *
     * @param main The main game object for which this screen is created
     */
    public static GameScreen createDefaultGameScreen(final Main main) {
        ArrayList<Player> players = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            players.add(new Player(ColourPalette.standardPalette(), 100f + 50f * i, 100f));
        }
        return new GameScreen(main, players);
    }

    protected void drawPlayerStatus(Player player, boolean isActive) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        Vector2 playerPos = player.getPosition();
        Vector2 brushPos = player.getBrushPosition();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(playerPos.x, playerPos.y, 12);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(isActive ? player.getColourPalette().getCurrentColour().getLibgdxColor() : Color.BLACK);
        shapeRenderer.circle(playerPos.x, playerPos.y, 10);
        shapeRenderer.setColor(Colour.BLACK.getLibgdxColor());
        shapeRenderer.circle(brushPos.x, brushPos.y, 2);
        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update camera
        camera.update();

        // Update the input processor
        int oldActive = activePlayer;
        activePlayer = inputProcessor.update(delta, activePlayer);
        activePlayer = activePlayer >= numPlayers ? oldActive : activePlayer;


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        draw.getNewPainting().setColor(players.get(activePlayer)
                .getColourPalette().getCurrentColour().getLibgdxColor());
        PlayerMovement movement = movementAPI.nextMovement();
        while (movement != null) {
            draw.drawTriangle(movement.getStartOfMovement(),movement.getCenterOfPlayer(),

                    movement.getEndOfMovement());
            movement = movementAPI.nextMovement();
        }

        // Update drawing if needed
        draw.update(0, 0, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(draw.getCanvas(), 0, 0);
        batch.draw(background, 0, 0);
        batch.end();

        createColourSpots();

        // Draw player status
        for (int i = 0; i < numPlayers; i++) {
            drawPlayerStatus(players.get(i), i == activePlayer);
        }
        
    }

    public void createColourSpots(){
        createRedSpot();
        createBlueSpot();
        createYellowSpot();
        createWhiteSpot();
    }

    public void createRedSpot(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colour.RED.getLibgdxColor());
        shapeRenderer.rect(13, 100, 40, 40);
        shapeRenderer.end();
    }

    public void createBlueSpot(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colour.BLUE.getLibgdxColor());
        shapeRenderer.rect(13, 200, 40, 40);
        shapeRenderer.end();
    }

    public void createYellowSpot(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colour.YELLOW.getLibgdxColor());
        shapeRenderer.rect(13, 300, 40, 40);
        shapeRenderer.end();
    }

    public void createWhiteSpot(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colour.WHITE.getLibgdxColor());
        shapeRenderer.rect(13, 400, 40, 40);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        drawing.dispose();
        shapeRenderer.dispose();
    }
}