package safers.rooftops;

import net.runelite.api.Client;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;
import java.util.List;

public class RooftopsOverlay extends Overlay {
    private final Client client;
    private final RooftopsConfig config;
    private final RooftopsCourseManager course_manager;

    public RooftopsOverlay(final Client client, final RooftopsConfig config, final RooftopsCourseManager course_manager) {
        this.client = client;
        this.config = config;
        this.course_manager = course_manager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (course_manager.getCourse() == null || !course_manager.isNearCourse()) {
            return null;
        }

        for (final TileObject obstacle : course_manager.getObstacles()) {
            final Color color;
            boolean isNextObstacle = course_manager.getCourse().getNextObstacle().hasId(obstacle.getId());
            boolean isClosestToPlayer = false;

            if (isNextObstacle) {
                WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();
                isClosestToPlayer = isClosestObstacleToPlayer(obstacle, playerLocation, course_manager.getObstacles());
            }

            if (course_manager.isStoppingObstacle(obstacle.getId())) {
                color = config.getObstacleStopColor();
            } else if (isNextObstacle && !course_manager.getCourse().isDoingObstacle() && isClosestToPlayer) {
                color = config.getObstacleNextColor();
            } else {
                color = new Color(0, 0, 0, 0); // Transparent color
            }

            renderShape(graphics, obstacle.getClickbox(), color);
        }

        for (final Tile mark : course_manager.getMarksOfGraces()) {
            renderShape(graphics, mark.getItemLayer().getCanvasTilePoly(), config.getMarkOfGraceColor());
        }

        return null;
    }

    private boolean isClosestObstacleToPlayer(TileObject obstacle, WorldPoint playerLocation, List<TileObject> obstacles) {
        TileObject closestObstacle = null;
        double minDistance = Double.MAX_VALUE;

        for (TileObject obj : obstacles) {
            if (obj.getId() == obstacle.getId()) {
                double distance = obj.getWorldLocation().distanceTo(playerLocation);
                if (distance < minDistance) {
                    closestObstacle = obj;
                    minDistance = distance;
                }
            }
        }

        return obstacle == closestObstacle;
    }

    private void renderShape(final Graphics2D graphics, final Shape shape, final Color color) {
        if (shape == null || color.getAlpha() == 0) return;

        Rectangle bounds = shape.getBounds();
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        int squareSize = 5;
        int squareX = centerX - squareSize / 2;
        int squareY = centerY - squareSize / 2;

        graphics.setColor(color);
        graphics.fillRect(squareX, squareY, squareSize, squareSize);
    }
}
