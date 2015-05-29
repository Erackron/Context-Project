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

    protected Pixmap painting;
    protected Pixmap newPainting;
    protected final Texture canvas;

    @Setter(AccessLevel.NONE)
    protected boolean updateNeeded = false;

    /**
     * Create a drawable Pixmap object wrapping an actual Pixmap.
     *
     * @param
     */
    public DrawablePixmap(Pixmap painting, Pixmap newPainting, Texture canvas) {
        this.painting = painting;
        this.newPainting = newPainting;
        this.canvas = canvas;
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
            blend(0, 0, Constants.CAM_WIDTH, Constants.CAM_HEIGHT);
            painting.drawPixmap(newPainting, 0, 0);
            canvas.draw(newPainting, 0, 0);
            updateNeeded = false;
        }
    }

    /**
     * Blend pixels together to combine colours.
     * @param x The x coordinate of where to start.
     * @param y The y coordinate of where to start
     * @param width The width of the rectangle that needs to be blended.
     * @param height The height of the rectangle that needs to be blended.
     */
    public void blend(int x, int y, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int newPixel = getNewPixel(i, j);
                int oldPixel = getOldPixel(i, j);

                if (newPixel != oldPixel && oldPixel != 0 && newPixel != 0) {
                    Colour first = Colour.getColour(newPixel);
                    Colour second = Colour.getColour(oldPixel);
                    Colour blend = Colour.combine(Arrays.asList(first, second));

                    setNewPixel(i, j, blend);
                }
            }
        }
    }

    public int getNewPixel(int i, int j) {
        return newPainting.getPixel(i, j);
    }

    public void setNewPixel(int i, int j, Colour color) {
        newPainting.setColor(color.getLibgdxColor());
        newPainting.drawPixel(i, j);
    }

    public int getOldPixel(int i, int j) {
        return painting.getPixel(i, j);
    }

    @Override
    public void dispose() {
        painting.dispose();
        canvas.dispose();
    }

}
