package nl.tudelft.contextproject.core.entities;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public enum Colour {

    RED(1, 0, 0, -16776961), YELLOW(1, 1, 0, -65281), BLUE(0, 0, 1, 65535), GREEN(34/255f, 170/255f, 34/255f, 581575423),
    PURPLE(128/255f, 0, 128/255f, 2147450625), ORANGE(255/255f, 165/255f, 0, 5963521), BLACK(0, 0, 0, 255);

    @Getter
    private Color color;

    private int pixelValue;

    Colour(float r, float g, float b, int pixelValue){
        this.color = new Color(r, g, b, 1);
        this.pixelValue = pixelValue;
    }

    public static Colour getColour(int p) {
        for (Colour c : Colour.values()) {
            if (p == c.pixelValue) {
                return c;
            }
        }

        return BLACK;
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
