package safers.rooftops;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RooftopsPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(RooftopsPlugin.class);
		RuneLite.main(args);
	}
}