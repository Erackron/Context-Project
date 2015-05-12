package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mitchell on 12-5-2015.
 */
public enum Colour {

    RED(255, 0, 0), YELLOW(255, 255, 0), BLUE(0, 0, 255), LIGHTGREEN(0, 255, 0), GREEN(34, 170, 34), GREY(127, 127, 127),
    DAKBLUE(0, 0, 255), PURPLE(128, 0, 128), PINK(250, 105, 180), BROWN(139, 69, 69), ORANGE(255, 165, 0),
    SALMON(255, 181, 153), BLACK(0, 0, 0);

    @Getter
    private Color color;

    @Getter @Setter
    private Colour next;

    Colour(int r, int g, int b){
        this.color = new Color(r, g, b, 1);
    }
}
