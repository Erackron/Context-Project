package nl.tudelft.contextproject.core.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Player;

/**
 * This class is a wrapper for Pixmap and Texture to enable storing the drawing of the players.
 */
@Data
public class DrawablePixmap implements Disposable {
    protected Color eraseColour = Color.BLACK;
    protected int brushSize = 1;
    protected Camera camera;
    protected Player player;

    protected final Pixmap painting;
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
        painting.setColor(player.getColourPalette().getCurrentColour().getColor());
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
        Gdx.gl.glLineWidth(brushSize);
        painting.drawLine(x1, y1, x2, y2);
        updateNeeded = true;
    }

    public void drawTriangle(Vector2 start, Vector2 center, Vector2 end) {
        drawTriangle((int) start.x,
                (int) (Constants.CAM_HEIGHT - Math.min(start.y, Constants.CAM_HEIGHT)),
                (int) center.x,
                (int) (Constants.CAM_HEIGHT - Math.min(center.y, Constants.CAM_HEIGHT)),
                (int) end.x,
                (int) (Constants.CAM_HEIGHT - Math.min(end.y, Constants.CAM_HEIGHT)));
        updateNeeded= true;
    }

    private void drawTriangle(int x, int y, int x1, int i, int x2, int i1) {
        painting.fillTriangle(x,y,x1,i,x2,i1);
    }

    /**
     * Redraw the painting onto the canvas if needed.
     */
    public void update() {
        if (updateNeeded) {
            canvas.draw(painting, 0, 0);
            updateNeeded = false;
        }
    }

    @Override
    public void dispose() {
        painting.dispose();
        canvas.dispose();
    }

}
