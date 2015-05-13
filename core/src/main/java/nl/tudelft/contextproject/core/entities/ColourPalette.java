package nl.tudelft.contextproject.core.entities;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC on 13/05/15.
 */
@Data
public class ColourPalette {
    @NotNull
    private final List<Colour> colours;

    private int current =0;

    public Colour cycle(){
        if(current == colours.size()-1){
            current=0;
        }else{
            current++;
        }
        return colours.get(current);
    }

    public Colour getCurrentColour(){
        return colours.get(current);
    }

    public static ColourPalette standardPalette(){
        List<Colour> colours = new ArrayList<>();
        colours.add(Colour.RED);
        colours.add(Colour.BLUE);
        colours.add(Colour.YELLOW);
        return new ColourPalette(colours);
    }
}
