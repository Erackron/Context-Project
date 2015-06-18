package nl.tudelft.contextproject.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import nl.tudelft.contextproject.core.input.PlayerAPI;
import nl.tudelft.contextproject.core.input.PlayerPosition;
import nl.tudelft.contextproject.core.playertracking.PlayerTracker;
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
    protected PlayerAPI playerAPI;
    protected int numPlayers;
    protected final Texture paintingFrame;
    protected final BitmapFont font;
    @Getter
    protected List<ColourSelectBox> colourSelectBoxes;
    protected PlayerTracker playerTracker;

    /**
     * Create a new game screen.
     *
     * @param main    The main game object for which this screen is created
     * @param players The players to add to this game screen
     */
    public GameScreen(final Main main, ArrayList<Player> players) {
        this.main = main;
        numPlayers = players.size();

        this.paintingFrame = new Texture(Gdx.files.internal("sprites/List60px.png"));
        paintingFrame.bind();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        Pixmap pixmap = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        Pixmap newPixmap = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        Texture texture = new Texture(pixmap);

        draw = new DrawablePixmap(pixmap, newPixmap, texture);
        batch = main.getBatch();
        batch.setProjectionMatrix(camera.combined);

        playerAPI = PlayerAPI.getPlayerApi();

        playerTracker = new PlayerTracker(new ArrayList<>());

        createColourSpots();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font = new BitmapFont();
    }

    /**
     * Create a new game screen.
     *
     * @param main The main game object for which this screen is created
     */
    public static GameScreen createDefaultGameScreen(final Main main) {
        ArrayList<Player> players = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            players.add(new Player(ColourPalette.standardPalette(), 100f + 50f * i, 100f, 12f));
        }
        return new GameScreen(main, players);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        batch.begin();
        batch.draw(draw.getCanvas(), 0, 0);
        batch.draw(paintingFrame, 0, 0);
        batch.end();

        List<PlayerPosition> playerPositions = playerAPI.nextPositionFrame();
        while (playerPositions != null) {
            drawFrame(playerTracker.trackPlayers(playerPositions));
            playerPositions = playerAPI.nextPositionFrame();
        }

        draw.update(0, 0, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);

        drawColourSpots();
    }

    /**
     * Draw a frame of players.
     *
     * @param detectedPlayers The detected players to draw
     */
    protected void drawFrame(List<Player> detectedPlayers) {
        batch.begin();
        detectedPlayers.forEach(player ->
                        font.draw(batch, String.valueOf(player.getPlayerIndex() + 1),
                                player.getPosition().x,
                                player.getPosition().y)
        );
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        detectedPlayers.forEach(this::drawPlayer);
        shapeRenderer.end();
    }

    /**
     * Draw a Player.
     *
     * @param player The player to draw
     */
    protected void drawPlayer(Player player) {
        Color currentColor = player.getColourPalette().getCurrentColour().getLibgdxColor();
        draw.getNewPainting().setColor(currentColor);
        draw.drawCircle(player.getPosition(), player.getLineSize().getBrushSize());
        Vector2 playerPos = new Vector2(player.getPosition());

        float radius = player.getRadius();
        float diameter = radius * 2;
        Vector2 bottomLeft = playerPos.sub(radius, radius);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(bottomLeft.x, bottomLeft.y, diameter, diameter);
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

        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(0), 13, 10, 53, 310)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(0), 53, 10, 313, 50)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(1), 13, 440, 53, 700)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(1), 13, 700, 313, 740)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(2), 947, 10, 987, 310)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(2), 687, 10, 987, 50)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(3), 947, 440, 987, 740)
        );
        colourSelectBoxes.add(
                new ColourSelectBox(baseColours.get(3), 687, 700, 987, 740)
        );

        playerTracker.setColourSelectBoxes(colourSelectBoxes);
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
