package safers.rooftops;


import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WorldPointMarkerOverlay extends Overlay {
    private final Client client;
    private final RooftopsConfig config;

    private final List<WorldPoint> markedPoints = Arrays.asList(
            new WorldPoint(2625, 3677, 0),
            new WorldPoint(3103, 3279, 0),
            new WorldPoint(3295, 3188, 0),
            new WorldPoint(3273, 3195, 0),  // New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3210, 3407, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3036, 3340, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(2729, 3488, 0),  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates
            new WorldPoint(3356, 2965, 0)  // New poi// New point, replace 1234, 5678, 0 with the actual coordinates



    // Add more points here
    );

    @Inject
    public WorldPointMarkerOverlay(Client client,RooftopsConfig config) {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (client.getLocalPlayer() != null && client.getPlane() == 0) {

            for (WorldPoint point : markedPoints) {
                drawTile(graphics, point);
            }
        }

        return null;
    }

    private void drawTile(Graphics2D graphics, WorldPoint point) {
        if (client.getLocalPlayer() == null || client.getPlane() != point.getPlane()) {
            return;
        }

        WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();
        if (point.distanceTo(playerLocation) > 32) {
            return; // the tile is too far away to render
        }

        Tile tile = client.getScene().getTiles()[point.getPlane()][point.getX() - client.getBaseX()][point.getY() - client.getBaseY()];
        if (tile == null) {
            return;
        }

        Polygon poly = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());
        if (poly != null) {
            Color tileColor = config.getTileColorInGame(); // Use color from config
            OverlayUtil.renderPolygon(graphics, poly, tileColor);        }
    }
}

