package safers.rooftops;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public class MovementStatusOverlay extends OverlayPanel {

	private final Client client;
	private WorldPoint lastPosition;
	private int lastAnimation = -1;
	private long lastMoveTime;

	@Inject
	public MovementStatusOverlay(Client client) {
		super(null);
		this.client = client;
		setPosition(OverlayPosition.TOP_LEFT);
		this.lastPosition = null;
		this.lastMoveTime = System.currentTimeMillis();
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		panelComponent.getChildren().clear();

		// Set a preferred width for the panel
		int preferredWidth = 130; // Adjust this value as needed


		panelComponent.getChildren().add(TitleComponent.builder()
				.text("SafeRS Agility")
				.color(new Color(0, 128, 192)) // Color #0080C0
				.build());

		Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null) {
			return super.render(graphics);
		}

		WorldPoint currentPosition = localPlayer.getWorldLocation();
		int currentAnimation = localPlayer.getAnimation();

		// Update the last move time if the player is animating
		if (currentAnimation != -1) {
			lastMoveTime = System.currentTimeMillis();
		} else if (lastPosition != null && !currentPosition.equals(lastPosition)) {
			// Update the last move time if the player has moved and not currently animating
			lastMoveTime = System.currentTimeMillis();
		}

		lastPosition = currentPosition;
		lastAnimation = currentAnimation;

		long timeSinceLastActivity = System.currentTimeMillis() - lastMoveTime;
		boolean isMoving = timeSinceLastActivity <= 1850;

		String status = isMoving ? "Moving" : "Not Moving";
		Color statusColor = isMoving ? Color.GREEN : Color.RED;

		panelComponent.getChildren().add(LineComponent.builder()
				.left("Status:")
				.leftColor(Color.WHITE)
				.right(status)
				.rightColor(statusColor)
				.build());

		panelComponent.setPreferredSize(new Dimension(preferredWidth, 0));

		return super.render(graphics);
	}
}
