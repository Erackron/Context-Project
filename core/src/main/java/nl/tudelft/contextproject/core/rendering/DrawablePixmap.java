package nl.tudelft.contextproject.core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
import nl.tudelft.contextproject.core.entities.Player;

import java.util.Arrays;

/**
 * This class is a wrapper for Pixmap and Texture to enable storing the drawing of the players.
 */
@Data
public class DrawablePixmap implements Disposable {
    protected Color eraseColour = Color.BLACK;
    protected int brushSize = 1;
    protected Camera camera;
    protected Player player;

    protected Pixmap painting;
    protected Pixmap newPainting;
    protected final Texture canvas;

    @Setter(AccessLevel.NONE)
    protected boolean updateNeeded = false;

    /**
     * Create a drawable Pixmap object wrapping an actual Pixmap.
     *
     * @param camera The camera to use when mapping coordinates to the screen
     */
    public DrawablePixmap(Camera camera, Player player) {
        this.painting = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        painting.setColor(player.getColourPalette().getCurrentColour().getLibgdxColor());

        this.newPainting = new Pixmap(Constants.CAM_WIDTH, Constants.CAM_HEIGHT,
                Pixmap.Format.RGBA8888);
        newPainting.setColor(player.getColourPalette().getCurrentColour().getLibgdxColor());

        this.camera = camera;
        this.player = player;
        this.canvas = new Texture(painting);
        canvas.bind();
    }

    /**
     * Draws a line between two Vector2 coordinates using the currently set colour.
     * The coordinates are converted from the default coordinate system (0,0 is bottom left) to the
     * pixmap coordinate system (0,0 is top left) before being drawn.
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
        Gdx.gl.glLineWidth(brushSize);
        painting.drawLine(x1, y1, x2, y2);
        updateNeeded = true;
    }

    /**
     * Draw a triangle on this pixmap.
     * The coordinates are converted from the default coordinate system (0,0 is bottom left) to the
     * pixmap coordinate system (0,0 is top left) before being drawn.
     *
     * @param corner1 The first corner coordinates
     * @param corner2 The second corner coordinates
     * @param corner3 The third corner coordinates
     */
    public void drawTriangle(Vector2 corner1, Vector2 corner2, Vector2 corner3) {
        drawTriangle((int) corner1.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner1.y, Constants.CAM_HEIGHT)),
                (int) corner2.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner2.y, Constants.CAM_HEIGHT)),
                (int) corner3.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner3.y, Constants.CAM_HEIGHT)));
        updateNeeded = true;
    }

    private void drawTriangle(int x, int y, int x1, int i, int x2, int i1) {
        newPainting.fillTriangle(x, y, x1, i, x2, i1);
    }

    /**
     * Redraw the painting onto the canvas if needed.
     */
    public void update() {
        if (updateNeeded) {
            blend();
            painting.drawPixmap(newPainting, 0, 0);
            canvas.draw(newPainting, 0, 0);
            updateNeeded = false;
        }
    }

    /**
     * Blend the pixels together to combine colours.
     */
    public void blend() {
        for (int i = 0; i < Constants.CAM_WIDTH; i++) {
            for (int j = 0; j < Constants.CAM_HEIGHT; j++) {
                int newPixel = newPainting.getPixel(i, j);
                int oldPixel = painting.getPixel(i, j);

                if (newPixel != oldPixel && oldPixel != 0 && newPixel != 0) {
                    Colour first = Colour.getColour(newPixel);
                    Colour second = Colour.getColour(oldPixel);
                    Colour blend = Colour.combine(Arrays.asList(first, second));

                    newPainting.setColor(blend.getLibgdxColor());
                    newPainting.drawPixel(i, j);
                }
            }
        }
    }

    @Override
    public void dispose() {
        painting.dispose();
        canvas.dispose();
    }

}
