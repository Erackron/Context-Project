package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuButton extends TextButton {
    protected Skin skin;

    /**
     * Constructor method.
     *
     * @param text The text to display on the button
     * @param style The style of the button
     */
    public MenuButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    /**
     * Create a new MenuButton with our default style.
     *
     * @param text The text to display on the button
     * @return The newly created MenuButton
     */
    public static MenuButton createMenuButton(String text) {
        Skin skin = new Skin();

        // Store a white texture under white
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.TEAL);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store default libgdx font under default
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        // Create a simple button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        return new MenuButton(text, textButtonStyle);
    }

    /**
     * Method that returns the skin.
     *
     * @return The skin
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Method that set a new skin.
     *
     * @param skin The skin to be set
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
        super.setSkin(skin);
    }
}
