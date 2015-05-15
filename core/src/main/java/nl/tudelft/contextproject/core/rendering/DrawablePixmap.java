package nl.tudelft.contextproject.core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
import nl.tudelft.contextproject.core.entities.Player;

import java.nio.ByteBuffer;

/**
 * This class is a wrapper for Pixmap and Texture to enable storing the drawing of the players.
 */
@Data
public class DrawablePixmap implements Disposable {
    protected Color eraseColour = Color.BLACK;
    protected int brushSize = 20;
    protected Camera camera;
    protected Player player;

    protected final Pixmap painting;
    protected final Pixmap newPainting;
    protected final Texture canvas;

    @Setter(AccessLevel.NONE)
    protected boolean updateNeeded = false;

    /**
     * Create a drawable Pixmap object wrapping an actual Pixmap.
     * @param camera The camera to use when mapping coordinates to the screen
     */
    public DrawablePixmap(Camera camera, Player player) {
        this.painting = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        painting.setBlending(Pixmap.Blending.SourceOver);
        painting.setColor(player.getBrush().getColor());

        this.newPainting = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        newPainting.setBlending(Pixmap.Blending.SourceOver);
        newPainting.setColor(player.getBrush().getColor());

        this.camera = camera;
        this.player = player;

        this.canvas = new Texture(painting);
        canvas.bind();
    }

    /**
     * Draws a line between two Vector2 coordinates using the currently set colour.
     * The coordinates are passed through {@link Camera#project(Vector3)} before being drawn.
     *
     * @param start The first point
     * @param end   The second point
     */
    public void drawLine(Vector2 start, Vector2 end) {
        drawLine((int) start.x, (int) (Constants.CAM_HEIGHT - start.y),
                (int) end.x, (int) (Constants.CAM_HEIGHT - end.y));
    }

    /**
     * Draws a line between the given coordinates using the currently set colour.
     *
     * @param x1 The x-coodinate of the first point
     * @param y1 The y-coordinate of the first point
     * @param x2 The x-coordinate of the second point
     * @param y2 The y-coordinate of the second point
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        Gdx.gl20.glLineWidth(brushSize);
        newPainting.drawLine(x1, y1, x2, y2);
        updateNeeded = true;
    }

    public void drawTriangle(Vector2 start, Vector2 center, Vector2 end) {
        drawTriangle((int) start.x, (int) (Constants.CAM_HEIGHT - start.y),
                     (int) center.x, (int) (Constants.CAM_HEIGHT - center.y),
                     (int) end.x, (int) (Constants.CAM_HEIGHT - end.y));
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        newPainting.fillTriangle(x1, y1, x2, y2, x3, y3);
        updateNeeded = true;
    }

    /**
     * Redraw the painting onto the canvas if needed.
     */
    public void update() {
        if (updateNeeded) {

            for (int i = 0; i < Constants.CAM_WIDTH; i++) {
                for (int j = 0; j < Constants.CAM_HEIGHT; j++) {
                    int pixelOld = painting.getPixel(i, j);
                    int pixelNew = newPainting.getPixel(i, j);

                    if (pixelOld != pixelNew && pixelOld != 0) {

                        Color oldC = new Color();
                        Color.rgba8888ToColor(oldC, pixelOld);

                        Color newC = new Color();
                        Color.rgba8888ToColor(newC, pixelNew);

                        Color blend = oldC.lerp(newC, 0.5f);

                        newPainting.setColor(blend);
                        newPainting.drawPixel(i, j);
                        
                    }
                }
            }

            painting.drawPixmap(newPainting, 0, 0);

            canvas.draw(newPainting, 0, 0);
            updateNeeded = false;
        }
    }

    @Override
    public void dispose() {
        painting.dispose();
        canvas.dispose();
    }

}
