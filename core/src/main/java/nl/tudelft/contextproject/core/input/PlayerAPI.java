package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.AtomicQueue;
import nl.tudelft.contextproject.core.config.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The API class which enables the input layer to communicate brush strokes with the core.
 */
public final class PlayerAPI {
    // The singleton instance of this API
    static final PlayerAPI PLAYER_API = new PlayerAPI();
    protected AtomicQueue<List<PlayerPosition>> playerQueue;
    protected Vector2 cameraInputSize = null;

    /**
     * The protected constructor of this API.
     */
    private PlayerAPI() {
        playerQueue = new AtomicQueue<>(Constants.MOVEMENT_QUEUE_CAPACITY);
    }

    /**
     * Add a new player position frame to the input queue.
     *
     * @param playerPositionFrame The playerPosition Frame to add
     * @return Whether the movement was added to the queue
     */
    public boolean addPositionFrame(List<? extends PlayerPosition> playerPositionFrame) {
        List<PlayerPosition> playerPositions = new ArrayList<>(playerPositionFrame.size());
        playerPositions.addAll(playerPositionFrame);
        return playerQueue.put(playerPositions);
    }

    /**
     * Add a new player position frame to the input queue.
     *
     * @param playerPositionFrame The playerPosition Frame to add
     * @return Whether the movement was added to the queue
     */
    public boolean addPositionFrame(PlayerPosition... playerPositionFrame) {
        return addPositionFrame(Arrays.asList(playerPositionFrame));
    }

    /**
     * Get the next player position frame from the input queue.
     *
     * @return A List of PlayerPositions or null if the queue is empty
     */
    public List<PlayerPosition> nextPositionFrame() {
        return playerQueue.poll();
    }

    /**
     * Set the camera input size.
     *
     * @param width  The width of the camera input
     * @param height The height of the camera input
     */
    public void setCameraInputSize(int width, int height) {
        setCameraInputSize(new Vector2(width, height));
    }

    /**
     * Set the camera input size.
     *
     * @param cameraInputSize The size of the camera input
     */
    public void setCameraInputSize(Vector2 cameraInputSize) {
        this.cameraInputSize = cameraInputSize;
    }

    /**
     * Get the camera input size.
     *
     * @return The size of the camera input
     */
    public Vector2 getCameraInputSize() {
        return cameraInputSize != null ? new Vector2(cameraInputSize) : null;
    }

    /**
     * Get the singleton instance of the movement API.
     *
     * @return The singleton instance of the movement API
     */
    public static PlayerAPI getPlayerApi() {
        return PLAYER_API;
    }
}
