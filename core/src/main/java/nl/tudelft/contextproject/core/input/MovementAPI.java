package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.utils.AtomicQueue;
import nl.tudelft.contextproject.core.config.Constants;

/**
 * The API class which enables the input layer to communicate brush strokes with the core.
 */
public final class MovementAPI {
    // The singleton instance of this API
    static MovementAPI movementAPI = new MovementAPI();
    protected AtomicQueue<PlayerMovement> movementQueue;

    /**
     * The protected constructor of this API.
     */
    protected MovementAPI() {
        movementQueue = new AtomicQueue<>(Constants.MOVEMENT_QUEUE_CAPACITY);
    }

    /**
     * Add a new player movement to the input queue.
     *
     * @param movement The movement to add
     * @return Whether the movement was added to the queue
     */
    public boolean addMovement(PlayerMovement movement) {
        return movementQueue.put(movement);
    }

    /**
     * Get the next movement from the input queue.
     *
     * @return A PlayerMovement instance or null if the queue is empty
     */
    public PlayerMovement nextMovement() {
        return movementQueue.poll();
    }

    /**
     * Get the singleton instance of the movement API.
     *
     * @return The singleton instance of the movement API
     */
    public static MovementAPI getMovementAPI() {
        return movementAPI;
    }
}
