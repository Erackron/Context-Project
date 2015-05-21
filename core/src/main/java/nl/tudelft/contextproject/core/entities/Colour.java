package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mitchell on 12-5-2015.
 */
public enum Colour {

    RED(255, 0, 0), YELLOW(255, 255, 0), BLUE(0, 0, 255), LIGHTGREEN(0, 255, 0), GREEN(34, 170, 34), GREY(127, 127, 127),
    DAKBLUE(0, 0, 255), PURPLE(128, 0, 128), PINK(250, 105, 180), BROWN(139, 69, 69), ORANGE(255, 165, 0),
    SALMON(255, 181, 153), BLACK(0, 0, 0);

    @Getter
    private Color color;

    Colour(int r, int g, int b){
        this.color = new Color(r, g, b, 1);
    }

    public static boolean areComplementary(Collection<Colour> colours) {
        if (colours.size() != 2) {
            return false;
        } else {
            if (    colours.contains(RED) && colours.contains(GREEN) ||
                    colours.contains(YELLOW) && colours.contains(PURPLE) ||
                    colours.contains(BLUE) && colours.contains(ORANGE)) {
                return true;
            }

            return false;
        }
    }
    public static Colour combine(Collection<Colour> colours){

        if (colours.size() != 2) {
            return BLACK;
        } else {

            if (colours.contains(RED) && colours.contains(BLUE)) {
                return PURPLE;
            } else if (colours.contains(RED) && colours.contains(YELLOW)) {
                return ORANGE;
            } else if (colours.contains(BLUE) && colours.contains(YELLOW)) {
                return GREEN;
            } else if(areComplementary(colours)) {
                return BLACK;
            } else {

                Iterator<Colour> it = colours.iterator();
                Colour first = it.next();
                Colour second = it.next();

                if (first == RED || first == BLUE || first == YELLOW) {
                    return first;
                } else {
                    return second;
                }
            }
        }
    }
}
