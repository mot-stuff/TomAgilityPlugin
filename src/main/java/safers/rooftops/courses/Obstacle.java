package safers.rooftops.courses;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Obstacle {
    private final int[] ids;
    private final List<WorldPoint> locations;

    public Obstacle(final int id, final int plane, final int[][] locations) {
        this(new int[]{id}, plane, locations);
    }

    public Obstacle(final int[] ids, final int plane, final int[][] locations) {
        this.ids = ids;
        this.locations = new ArrayList<>();
        for (final int[] location : locations) {
            this.locations.add(new WorldPoint(location[0], location[1], plane));
        }
    }

    public boolean hasId(final int id) {
        return Arrays.stream(ids).anyMatch(i -> i == id);
    }

    public int[] getIds() {
        return ids;
    }

    public List<WorldPoint> getLocations() {
        return locations;
    }

    public WorldPoint getClosestPoint(WorldPoint playerLocation) {
        WorldPoint closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (WorldPoint location : locations) {
            double distance = location.distanceTo(playerLocation);
            if (distance < minDistance) {
                closestPoint = location;
                minDistance = distance;
            }
        }

        return closestPoint;
    }

}
