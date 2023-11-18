package safers.rooftops;

import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class TomsAgilityOverlay extends OverlayPanel {
    private final PanelComponent panelComponent = new PanelComponent();

    public TomsAgilityOverlay() {
        super();
        // Initialize your panel with any other necessary settings here
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        // Clear previous components
        panelComponent.getChildren().clear();

        // Add a title component to the panel
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Tom's Agility")
                .build());

        // Optional: Add other components or customization here

        // Set the size of the panel (adjust as necessary)
        panelComponent.setPreferredSize(new Dimension(150, 100));

        // Add the panel component to the overlay
        return panelComponent.render(graphics);
    }
}
