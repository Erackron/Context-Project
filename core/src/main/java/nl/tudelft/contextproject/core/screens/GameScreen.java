package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import lombok.Getter;
import nl.tudelft.contextproject.core.Main;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import nl.tudelft.contextproject.core.entities.ColourSelectBox;
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
    @Getter
    protected List<Player> players;
    protected List<ColourSelectBox> colourSelectBoxes;

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

        createColourSpots();

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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(playerPos.x, playerPos.y, 12);
        shapeRenderer.end();

        Colour playerColour = player.getColourPalette().getCurrentColour();
        if (playerColour.getPixelValue() == 2139062271) {
            playerColour = Colour.WHITE;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(isActive ? playerColour.getLibgdxColor() : Color.BLACK);
        shapeRenderer.circle(playerPos.x, playerPos.y, 10);
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
            draw.drawCircle(movement.getCenterOfPlayer(), movement.getRadiusOfCircle());
            movement = movementAPI.nextMovement();
        }

        // Update drawing if needed
        draw.update(0, 0, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(draw.getCanvas(), 0, 0);
        batch.draw(background, 0, 0);
        batch.end();

        drawColourSpots();

        // Draw player status
        for (int i = 0; i < numPlayers; i++) {
            drawPlayerStatus(players.get(i), i == activePlayer);
        }

    }

    /**
     * Draws areas to change colour on screen.
     */
    public void drawColourSpots() {
        BoundingBox boundingBox;
        Vector3 min;
        Vector3 size;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (ColourSelectBox box : colourSelectBoxes) {
            boundingBox = box.getBoundingBox();
            min = boundingBox.getMin();
            size = boundingBox.getDimensions();
            Colour colour = box.getColour();
            boolean eraser = (colour.getPixelValue() == 2139062271);
            shapeRenderer.setColor(eraser ? Colour.WHITE.getLibgdxColor()
                    : colour.getLibgdxColor());
            shapeRenderer.rect(min.x, min.y, size.x, size.y);
        }
        shapeRenderer.end();
    }

    /**
     * Creates areas to change colour on screen.
     */
    public void createColourSpots() {
        List<Colour> baseColours = Colour.getBaseColours();
        colourSelectBoxes = new ArrayList<>(baseColours.size());

        for (int i = 0; i < baseColours.size(); i++) {
            colourSelectBoxes.add(
                    new ColourSelectBox(baseColours.get(i), 13, 100 + 100 * i, 53, 140 + 100 * i)
            );
        }

        getPlayers().forEach(player -> player.setColourSelectBoxes(colourSelectBoxes));
    }

    @Override
    public void resize(int width, int height) {
        // Unnecessary, but required listener method
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
        drawing.dispose();
        shapeRenderer.dispose();
    }
}