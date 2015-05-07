package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.utils.AtomicQueue;
import nl.tudelft.contextproject.core.config.Constants;

public class MovementAPI {
    protected AtomicQueue<PlayerMovement> movementQueue;

    public MovementAPI() {
        movementQueue = new AtomicQueue<>(Constants.MOVEMENT_QUEUE_CAPACITY);
    }

    public boolean addMovement(PlayerMovement movement) {
        return movementQueue.put(movement);
    }

    public PlayerMovement nextMovement() {
        return movementQueue.poll();
    }

}
