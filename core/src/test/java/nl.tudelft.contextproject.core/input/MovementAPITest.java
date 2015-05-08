package nl.tudelft.contextproject.core.input;

import com.badlogic.gdx.utils.AtomicQueue;
import nl.tudelft.contextproject.core.config.Constants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class MovementAPITest {
    MovementAPI api = MovementAPI.getMovementAPI();

    @Before
    public void setupCleanAPI() {
        api.movementQueue = new AtomicQueue<>(Constants.MOVEMENT_QUEUE_CAPACITY);
    }

    @Test
    public void testSingletonCreation() {
        assertEquals(api, MovementAPI.movementAPI);
        assertNotNull(api);
    }

    @Test
    public void testQueueEmpty() {
        assertNull(api.nextMovement());
    }

    @Test
    public void testAddAndGet() {
        PlayerMovement movement = mock(PlayerMovement.class);
        api.addMovement(movement);

        assertEquals(movement, api.nextMovement());
        assertNull(api.nextMovement());
    }

    @Test
    public void testQueueFull() {
        PlayerMovement movement = mock(PlayerMovement.class);
        api.movementQueue = spy(api.movementQueue);

        doReturn(false).when(api.movementQueue).put(any(PlayerMovement.class));
        assertFalse(api.addMovement(movement));
    }
}
