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

public class PlayerAPITest {
    PlayerAPI api = PlayerAPI.getPlayerApi();

    @Before
    public void setupCleanAPI() {
        api.playerQueue = new AtomicQueue<>(Constants.MOVEMENT_QUEUE_CAPACITY);
    }

    @Test
    public void testSingletonCreation() {
        assertEquals(api, PlayerAPI.PLAYER_API);
        assertNotNull(api);
    }

    @Test
    public void testQueueEmpty() {
        assertNull(api.nextPositionFrame());
    }

    @Test
    public void testAddAndGet() {
        PlayerPosition movement = mock(PlayerPosition.class);
        api.addPositionFrame(movement);

        assertEquals(movement, api.nextPositionFrame().get(0));
        assertNull(api.nextPositionFrame());
    }

    @Test
    public void testQueueFull() {
        PlayerPosition movement = mock(PlayerPosition.class);
        api.playerQueue = spy(api.playerQueue);

        doReturn(false).when(api.playerQueue).put(any());
        assertFalse(api.addPositionFrame(movement));
    }
}
