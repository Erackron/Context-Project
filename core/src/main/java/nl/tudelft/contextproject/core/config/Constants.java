package nl.tudelft.contextproject.core.config;

/**
 * This class serves as an internal "configuration" for parameters that might be useful in
 * multiple (independent) locations or parameters that are likely to become subject to change.
 */
public abstract class Constants {
    // The Width and Height of the camera view
    // This corresponds with the x and y axes of the screen
    public static final int CAM_WIDTH = 1000;
    public static final int CAM_HEIGHT = 750;

    // The capacity of the movement queue for the movement api
    public static final int MOVEMENT_QUEUE_CAPACITY = 50;
}
