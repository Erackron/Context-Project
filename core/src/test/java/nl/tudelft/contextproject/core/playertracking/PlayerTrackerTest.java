package nl.tudelft.contextproject.core.playertracking;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.input.PlayerPosition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTrackerTest {
    private PlayerTracker playerTracker;
    private List<Player> players;
    private List<PlayerPosition> playerPositions;

    @Mock
    private ColourPalette palette;

    @Spy
    private Vector2 position = new Vector2(300, 300);
    @Mock
    private PlayerPosition playerPosition;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        playerTracker = new PlayerTracker(players);
        playerPositions = new ArrayList<>();
        Player player = Mockito.spy(new Player(palette, position, 20));
        players.add(player);

        when(playerPosition.getCenterOfPlayer()).thenReturn(new Vector2(310, 310));
        when(playerPosition.getRadiusOfCircle()).thenReturn(30f);
        players.add(player);
        playerPositions.add(playerPosition);
    }

    @Test
    public void bestCaseScenario() {
        playerTracker.trackPlayers(playerPositions);
        Mockito.verify(position).set(new Vector2(310, 310));
    }

    @Test
    public void multiplePositionTest() {
        PlayerPosition playerPosition2 = Mockito.mock(PlayerPosition.class);
        when(playerPosition2.getCenterOfPlayer()).thenReturn(new Vector2(350, 350));
        playerPositions.add(playerPosition2);
        playerTracker.trackPlayers(playerPositions);
        Mockito.verify(position).set(new Vector2(310, 310));
    }

    @Test
    public void noOneNearTest() {
        players.clear();
        playerTracker.trackPlayers(playerPositions);
        assertEquals(players.get(0).getPosition(), new Vector2(310,310));
    }

}
