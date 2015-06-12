package nl.tudelft.contextproject.core.playertracking;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.TreeSet;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum LineSize implements Comparable<LineSize> {
    BIG(20, 55),
    NORMAL(10, 45),
    NONE(0, Integer.MIN_VALUE);

    @Getter
    private final int brushSize;
    private final int minPlayerSize;
    private static TreeSet<LineSize> lineSizes = new TreeSet<>();

    static {
        lineSizes.addAll(Arrays.asList(LineSize.values()));
    }

    /**
     * Get the appropriate line size for a specified input radius.
     *
     * @param inputRadius The input radius to base the selection on
     * @return The appropriate LineSize or null if none was found
     */
    public static LineSize getLineSize(int inputRadius) {
        for (LineSize size : lineSizes) {
            if (inputRadius >= size.minPlayerSize) {
                return size;
            }
        }
        return null;
    }
}
