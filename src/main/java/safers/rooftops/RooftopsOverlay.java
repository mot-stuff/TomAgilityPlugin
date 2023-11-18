package safers.rooftops;

import net.runelite.api.Client;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;

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
        if (course_manager.getCourse() == null || !course_manager.isNearCourse()) return null;

        // Obstacles.
// Obstacles.
        for (final TileObject obstacle : course_manager.getObstacles()) {
            final Color color;
            if (course_manager.isStoppingObstacle(obstacle.getId())) {
                color = config.getObstacleStopColor();
            } else if (course_manager.getCourse().getNextObstacle().hasId(obstacle.getId())) {
                if (course_manager.getCourse().isDoingObstacle()) {
                    // Set the color to transparent
                    color = new Color(0, 0, 0, 0); // Transparent color
                } else {
                    color = config.getObstacleNextColor();
                }
            } else {
                // Set the color to transparent
                color = new Color(0, 0, 0, 0); // Transparent color
            }

            renderShape(graphics, obstacle.getClickbox(), color);
        }

        // Mark of graces.
        for (final Tile mark : course_manager.getMarksOfGraces()) {
            renderShape(graphics, mark.getItemLayer().getCanvasTilePoly(), config.getMarkOfGraceColor());
        }

        return null;
    }

    private void renderShape(final Graphics2D graphics, final Shape shape, final Color color) {
        if (shape == null || color.getAlpha() == 0) return;

        try {
            // Get the bounds of the shape.
            Rectangle bounds = shape.getBounds();

            // Calculate the center point.
            int centerX = bounds.x + bounds.width / 2;
            int centerY = bounds.y + bounds.height / 2;

            // Define the size of the square (10x10).
            int squareSize = 5;

            // Calculate the top left corner of the square.
            int squareX = centerX - squareSize / 2;
            int squareY = centerY - squareSize / 2;

            // Set the color for the square.
            graphics.setColor(color);

            // Draw the square.
            graphics.fillRect(squareX, squareY, squareSize, squareSize);
        } catch (final Exception ignored) {}
    }
}
