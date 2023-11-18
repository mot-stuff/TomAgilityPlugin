package safers.rooftops.courses;

import net.runelite.api.coords.WorldPoint;
import safers.rooftops.MarkOfGrace;

import java.util.Arrays;

public abstract class Course {
    private final String id;
    private final int[] regions;
    private final Obstacle[] obstacles;
    private final MarkOfGrace[] mark_of_graces;

    private int obstacle_index = 0;
    private boolean doing_obstacle;

    public Course(final String id, final int[] regions, final Obstacle[] obstacles, final MarkOfGrace[] mark_of_graces) {
        this.id = id;
        this.regions = regions;
        this.obstacles = obstacles;
        this.mark_of_graces = mark_of_graces;
    }

    public String getId() {
        return id;
    }

    public int[] getRegions() {
        return regions;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public MarkOfGrace[] getMarkOfGraces() {
        return mark_of_graces;
    }

    public Obstacle getNextObstacle() {
        return obstacles[obstacle_index];
    }

    public void startObstacle() {
        if (doing_obstacle) return;

        doing_obstacle = true;
        obstacle_index = (obstacle_index + 1) % obstacles.length;
    }

    public void completeObstacle() {
        doing_obstacle = false;
        obstacle_index = (obstacle_index) % obstacles.length;
        // Optionally add logic here to handle completion of the last obstacle
        // e.g., if (obstacle_index == 0) { /* reset course */ }
    }

    public void reset() {
        doing_obstacle = false;
        obstacle_index = 0;
    }

    public boolean isDoingObstacle() {
        return doing_obstacle;
    }

    public boolean isInRegion(final int region) {
        for (final int r : regions) {
            if (r == region) {
                return true;
            }
        }
        return false;
    }

    public boolean isNearRegion(final int[] regions) {
        for (final int region : regions) {
            for (final int course_region : this.regions) {
                if (course_region == region) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateCurrentObstacle(WorldPoint playerLocation) {
        if (doing_obstacle) {
            return; // If currently doing an obstacle, don't update the index
        }

        // Check distance to the next obstacle
        Obstacle nextObstacle = getNextObstacle();
        double distanceToNext = nextObstacle.getClosestPoint(playerLocation).distanceTo(playerLocation);
        if (distanceToNext < 20) {
            return; // If close to the next obstacle, keep it as the target
        }

        // If not close to the next obstacle, find the closest one
        Obstacle closestObstacle = null;
        double minDistance = Double.MAX_VALUE;
        for (Obstacle obstacle : obstacles) {
            double distance = obstacle.getClosestPoint(playerLocation).distanceTo(playerLocation);
            if (distance < minDistance) {
                closestObstacle = obstacle;
                minDistance = distance;
            }
        }

        if (closestObstacle != null) {
            obstacle_index = Arrays.asList(obstacles).indexOf(closestObstacle);
        }
    }

    }
