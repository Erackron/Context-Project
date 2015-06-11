package nl.tudelft.contextproject.core.playertracking;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Data;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.input.PlayerPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerTracker {
    private static final float MAX_DISTANCE = 300;
    protected List<Player> playerList = new ArrayList<>();

    /**
     * Process a list of PlayerPositions and return a list of Players.
     *
     * @param playerPositions The potential player positions to run the tracking on
     * @return The list of Players on the screen
     */
    public List<Player> trackPlayers(List<PlayerPosition> playerPositions) {
        return playerPositions.stream()
                .map(this::trackPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Process a PlayerPosition and return a Player or null if the position was invalid.
     *
     * @param playerPosition The potential player position to run the tracking on
     * @return The Player
     */
    public Player trackPlayer(PlayerPosition playerPosition) {
        Optional<Pair> playerDistPair = playerList.parallelStream()
                .map(p -> new Pair(p, distance(p, playerPosition)))
                .filter(pair -> pair.getDist() < MAX_DISTANCE)
                .min(Pair::compareTo);


        Vector2 center = playerPosition.getCenterOfPlayer();
        return new Player(ColourPalette.standardPalette(), center.x, center.y,
                playerPosition.getRadiusOfCircle());
    }

    public static float distance(Player p, PlayerPosition playerPosition) {
        return p.getPosition().dst(playerPosition.getCenterOfPlayer());
    }

    @Data
    @AllArgsConstructor
    public static class Pair implements Comparable<Pair> {
        Player p;

        float dist;

        @Override
        public int compareTo(Pair o) {
            return Float.compare(this.dist,o.dist);
        }
    }

}
