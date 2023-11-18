package safers.rooftops;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WorldPointMinimapOverlay extends Overlay {
    private final Client client;
    private final RooftopsConfig config;
    private final List<WorldPoint> markedPoints = Arrays.asList(
            new WorldPoint(2625, 3677, 0),
            new WorldPoint(3103, 3279, 0),
            new WorldPoint(3295, 3188, 0),
            new WorldPoint(3273, 3195, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3210, 3407, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3036, 3340, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(2729, 3488, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3356, 2965, 0)  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates

            // Add more points here
    );

    @Inject
    public WorldPointMinimapOverlay(Client client, RooftopsConfig config) {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Color minimapColor = config.getTileColorOnMinimap(); // Get color from config
        for (WorldPoint point : markedPoints) {
            drawOnMinimap(graphics, point, minimapColor);
        }

        return null;
    }

    private void drawOnMinimap(Graphics2D graphics, WorldPoint point, Color color) {
        if (!point.isInScene(client)) {
            return;
        }

        int x = point.getX() - client.getBaseX();
        int y = point.getY() - client.getBaseY();

        x <<= Perspective.LOCAL_COORD_BITS;
        y <<= Perspective.LOCAL_COORD_BITS;

        Point mp1 = Perspective.localToMinimap(client, new LocalPoint(x, y));
        Point mp2 = Perspective.localToMinimap(client, new LocalPoint(x, y + Perspective.LOCAL_TILE_SIZE));
        Point mp3 = Perspective.localToMinimap(client, new LocalPoint(x + Perspective.LOCAL_TILE_SIZE, y + Perspective.LOCAL_TILE_SIZE));
        Point mp4 = Perspective.localToMinimap(client, new LocalPoint(x + Perspective.LOCAL_TILE_SIZE, y));

        if (mp1 == null || mp2 == null || mp3 == null || mp4 == null) {
            return;
        }

        Polygon poly = new Polygon();
        poly.addPoint(mp1.getX(), mp1.getY());
        poly.addPoint(mp2.getX(), mp2.getY());
        poly.addPoint(mp3.getX(), mp3.getY());
        poly.addPoint(mp4.getX(), mp4.getY());

        Stroke stroke = new BasicStroke(1f);
        graphics.setStroke(stroke);
        graphics.setColor(color);
        graphics.drawPolygon(poly);
    }
}

