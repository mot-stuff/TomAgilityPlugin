package safers.rooftops;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.Color;

@ConfigGroup("SafeRS-rooftops")
public interface RooftopsConfig extends Config {
	@ConfigSection(
		name = "Configuration",
		description = "Configuration",
		position = 1
	) String configuration = "configuration";

	@Alpha
	@ConfigItem(
			keyName = "tileColorInGame",
			name = "Tile Color In Game",
			description = "Color of the tile highlight in the game.",
			position = 5,
			section = configuration
	)
	default Color getTileColorInGame() {
		return new Color(96, 0, 255, 255); // Default color, can be any color
	}

	@Alpha
	@ConfigItem(
			keyName = "tileColorOnMinimap",
			name = "Tile Color On Minimap",
			description = "Color of the tile highlight on the minimap.",
			position = 4,
			section = configuration
	)
	default Color getTileColorOnMinimap() {
		return new Color(96, 0, 255, 255); // Default color, can be any color
	}
		@Alpha
		@ConfigItem(
			keyName = "obstacle_next",
			name = "Obstacle",
			description = "Color of the next obstacle.",
			position = 1,
			section = configuration
		) default Color getObstacleNextColor() { return new Color(255, 0, 255, 255); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_stop",
			name = "Stopping Obstacle",
			description = "Color of obstacle that should not be used, because Mark of grace is on the ground.",
			position = 2,
			section = configuration
		) default Color getObstacleStopColor() { return new Color(255, 0, 0, 255); }


		@Alpha
		@ConfigItem(
			keyName = "mark_of_grace",
			name = "Mark of grace",
			description = "Color of the Mark of grace.",
			position = 3,
				section = configuration
		) default Color getMarkOfGraceColor() { return new Color(175, 117, 208, 255); }
}
