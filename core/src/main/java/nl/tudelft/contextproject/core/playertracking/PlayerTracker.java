package nl.tudelft.contextproject.core.playertracking;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import nl.tudelft.contextproject.core.entities.ColourPalette;
import nl.tudelft.contextproject.core.entities.ColourSelectBox;
import nl.tudelft.contextproject.core.entities.Player;
import nl.tudelft.contextproject.core.input.PlayerPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

public class PlayerTracker {
    private static final float MAX_DISTANCE_SQUARED = 150 * 150;
    protected List<Player> playerList = new ArrayList<>();
    @Setter
    protected List<ColourSelectBox> colourSelectBoxes = new ArrayList<>();

    public PlayerTracker(List<Player> players) {
        playerList = players;
    }

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
        Player player;
        Optional<Pair> optPlayerDistPair = findClosestPlayerPair(playerPosition);
        Vector2 center = playerPosition.getCenterOfPlayer();
        if (optPlayerDistPair.isPresent()) {
            player = optPlayerDistPair.get().getPlayer();
            player.moveTo(center);
            player.setRadius(playerPosition.getRadiusOfCircle());
        } else {
            player = new Player(ColourPalette.standardPalette(), center.x, center.y,
                    playerPosition.getRadiusOfCircle());
            playerList.add(player);
            player.setPlayerIndex(playerList.size() - 1);
            player.setColourSelectBoxes(colourSelectBoxes);
        }
        return player;
    }

    /**
     * Find the closest Player object to a certain player position.
     *
     * @param position PlayerPosition to compare to.
     * @return An optional pair containing the Player and it's distance to the position.
     */
    public Optional<Pair> findClosestPlayerPair(PlayerPosition position) {
        return playerList.parallelStream()
                .map(p -> new Pair(p, distance(p, position)))
                .filter(pair -> pair.getDistSquared() < MAX_DISTANCE_SQUARED)
                .min(Pair::compareTo);
    }

    public static float distance(Player p, PlayerPosition playerPosition) {
        return p.getExpectedPosition().dst2(playerPosition.getCenterOfPlayer());
    }

    @Data
    @AllArgsConstructor
    public static class Pair implements Comparable<Pair> {
        private Player player;
        private float distSquared;

        @Override
        public int compareTo(Pair o) {
            return Float.compare(this.distSquared, o.distSquared);
        }
    }

}
