package nl.function1.polepass;

/*
 * PoleCompass 2.0.3 - Minecraft Bukkit/Spigot plugin by joppiesaus <job@function1.nl>
 * License: Public Domain. Do whatever you want. I'd love to hear what you're making with my code, though!
 * 
 * TODO:
 * GUI
 * CLICK DIRECTIONS
 * CLICK ENTITY TO FOLLOW?
 * 
 * CHANGELOG:
 * Hopefully finally fixed that stupid bug(your compass will reset no matter what now)
 * /compass reset no longer sets your compass to your bed if you have one
 * Added /compass bed, it points the compass to the bed
 * Added /setplayercompass bed
 */

import java.util.List;

import org.bukkit.metadata.Metadatable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class Compass extends JavaPlugin {

	CompassEvents compassevents;
	Commands c_cmd;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new CompassEvents(this), this);
		this.getCommand("compass").setExecutor(new Commands(this));

	}

	public void setMetadata(Metadatable object, String key, Object value, Plugin plugin) {
		object.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public void removeMetadata(Metadatable object, String key, Plugin plugin) {
		object.removeMetadata(key, plugin);
	}

	public Object getMetadata(Metadatable object, String key, Compass compass) {
		List<MetadataValue> values = object.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin() == compass) {
				return value.value();
			}
		}
		return null;
	}

	public void addTracker(Player requester, Player toTrack) {
		String data = (String) getMetadata(toTrack, "compasstarget", this);
		if (data == null) {
			data = requester.getName();
		} else {
			data += "," + requester.getName();
		}
		setMetadata(toTrack, "compasstrackers", data, this);

		setMetadata(requester, "compasstracking", toTrack.getName(), this);
	}

	public void removeRequesterFromTrackingOnly(Player p, String requester) {
		String data = (String) getMetadata(p, "compasstrackers", this);

		if (data == null) {
			return;
		}

		String[] players = data.split(",");

		String newData = "";
		for (String player : players) {
			if (player != requester) {
				newData += player + ",";
			}
		}

		if (newData == "") {
			removeMetadata(p, "compasstrackers", this);
		} else {
			newData = newData.substring(0, newData.length() - 2);
			setMetadata(p, "compasstrackers", newData, this);
		}
	}

	public void removeTrackerFromRequester(Player requester) {
		String toTrack = (String) getMetadata(requester, "compasstracking", this);
		removeMetadata(requester, "compasstracking", this);

		if (toTrack == null) {
			return;
		}
		Player p = Bukkit.getServer().getPlayer(toTrack);

		if (p != null) {
			removeRequesterFromTrackingOnly(p, requester.getName());
		}
	}
}
