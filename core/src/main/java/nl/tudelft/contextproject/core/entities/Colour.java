package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mitchell on 12-5-2015.
 */
public class Colour {
    @Getter
    public Color colour;
    @Getter
    @Setter
    public Colour next;

    public Colour(Color colour){
        this.colour = colour;
        next = null;
    }
}
