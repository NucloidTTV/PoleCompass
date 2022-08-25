package nl.function1.polepass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands implements CommandExecutor {

	public Compass compass;

	Commands(Compass compass) {
		this.compass = compass;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (cmd.getName().equalsIgnoreCase("compass")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.BLUE + "You must be a player to set your own compass!");
				return false;
			}

			if (args.length < 1) {
				sender.sendMessage(ChatColor.BLUE + "use /compass help");
				return false;
			}

			Player p = (Player) sender;

			switch (args[0].toLowerCase()) {

			case "help":
				p.sendMessage(ChatColor.BLUE + "/compass [direction]" + ChatColor.GRAY
						+ " - sets your compass direction to North, West, East or South.");
				p.sendMessage(ChatColor.BLUE + "/compass pos <x> <y> <z>" + ChatColor.GRAY
						+ " - sets your compass direction to the specific location.");
				p.sendMessage(ChatColor.BLUE + "/compass pos current" + ChatColor.GRAY
						+ " - sets your compass direction to your current position.");
				p.sendMessage(ChatColor.BLUE + "/compass follow <player>" + ChatColor.GRAY
						+ " - sets your compass direction to someone else's" + " position. Keeps updating.");
				// p.sendMessage(ChatColor.GOLD + "/compass about" + ChatColor.GRAY + " - views
				// the info of the plugin.");
				p.sendMessage(ChatColor.BLUE + "/compass bed" + ChatColor.GRAY
						+ " - sets your compass to your bed's location");
				p.sendMessage(ChatColor.BLUE + "To make your compass normal again, use /compass reset.");
				p.sendMessage(ChatColor.BLUE
						+ "To modify someone else's compass, use /setplayercompass and then anything listed here.");
				return true;

			case "about":
				PluginDescriptionFile pdf = compass.getDescription();
				String name = "PoleCompass";
				String des = pdf.getDescription();
				String ver = pdf.getVersion();
				String url = pdf.getWebsite();
				String aut = pdf.getAuthors().get(0);

				if (sender instanceof Player) {
					p.sendMessage(ChatColor.BLUE + name + " " + ver);
					p.sendMessage(ChatColor.BLUE + des);
					p.sendMessage(ChatColor.BLUE + "Author: " + ChatColor.RESET + aut);
					p.sendMessage(ChatColor.BLUE + "URL: " + ChatColor.RESET + url);
				} else {
					// TODO: Will never be true. Fix this. I'm lazy.
					sender.sendMessage(name + " " + ver);
					sender.sendMessage(des);
					sender.sendMessage("Author: " + aut);
					sender.sendMessage("URL: " + url);
				}
				return true;

			case "follow":
			case "f":
				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "/f or /follow playername");
					return false;
				}

				Player toFollow = Bukkit.getServer().getPlayer(args[1]);

				if (toFollow != null) {

					p.setCompassTarget(toFollow.getLocation());
					compass.addTracker(p, toFollow);
					p.sendMessage(ChatColor.BLUE + "Compass now following" + ChatColor.GOLD + toFollow.getName()
							+ ChatColor.BLUE + ".");
					return true;

				} else {
					p.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not found on this server.");
					return false;
				}

			case "pos":
				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "Usage: /compass pos current or /compass pos <x> <y> <z>.");
					return false;
				}
				if (args[1].toLowerCase().startsWith("c")) {
					p.setCompassTarget(p.getLocation());
					p.sendMessage(ChatColor.BLUE + "Compass is now pointing to your current location.");
					return true;
				}
				if (args.length < 4) {
					p.sendMessage(ChatColor.RED + "Usage: /compass pos current or /compass pos <x> <y> <z>.");
					return false;
				}

				try {
					int x = Integer.parseInt(args[1]);
					int y = Integer.parseInt(args[2]);
					int z = Integer.parseInt(args[3]);
					p.setCompassTarget(p.getWorld().getBlockAt(x, y, z).getLocation());
					p.sendMessage(ChatColor.BLUE + "Compass facing coordinates" + ChatColor.GRAY + x + ChatColor.BLUE
							+ ", " + ChatColor.GRAY + y + ChatColor.BLUE + ", " + ChatColor.GRAY + z + ChatColor.BLUE
							+ ".");
					return true;
				} catch (Exception ex) {
					p.sendMessage(ChatColor.RED + "Failed to convert positions.");
					return false;
				}

			case "n":
			case "north":
				p.setCompassTarget(p.getWorld().getBlockAt((int) p.getLocation().getX(), 0, -12550820).getLocation());
				p.sendMessage(ChatColor.BLUE + "Compass facing north.");
				return true;

			case "e":
			case "east":
				p.setCompassTarget(p.getWorld().getBlockAt(12550820, 0, (int) p.getLocation().getZ()).getLocation());
				p.sendMessage(ChatColor.BLUE + "Compass facing east.");
				return true;

			case "s":
			case "south":
				p.setCompassTarget(p.getWorld().getBlockAt((int) p.getLocation().getX(), 0, 12550820).getLocation());
				p.sendMessage(ChatColor.BLUE + "Compass facing south.");
				return true;

			case "w":
			case "west":
				p.setCompassTarget(p.getWorld().getBlockAt(-12550820, 0, (int) p.getLocation().getZ()).getLocation());
				p.sendMessage(ChatColor.BLUE + "Compass facing west.");
				return true;

			case "bed":
				if (p.getBedSpawnLocation() != null) {
					p.setCompassTarget(p.getBedSpawnLocation());
					p.sendMessage(ChatColor.BLUE + "Compass facing your bed's location.");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "You don't have a bed.");
					return false;
				}

			case "r":
			case "reset":
			case "default":
				p.setCompassTarget(p.getWorld().getSpawnLocation());
				p.sendMessage(ChatColor.BLUE + "Compass facing world's spawnpoint.");
				compass.removeTrackerFromRequester(p);
				return true;

			default:
				p.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid direction.");
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("setplayercompass.")) {

			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "/setplayercompass <playername>.");
				return false;
			}

			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Player \"" + args[0] + "\" not found.");
				return false;
			}

			switch (args[1].toLowerCase()) {

			case "help":
				// this is only hit when the first argument is a player
				sender.sendMessage(ChatColor.RED + "/" + cmd.getName().toLowerCase()
						+ " <player> <compass command>. See /compass help.");
				return true;

			case "f":
			case "follow":
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "Must include playername.");
					return false;
				}

				Player toFollow = Bukkit.getServer().getPlayer(args[2]);

				if (toFollow == null) {
					sender.sendMessage(ChatColor.RED + "Player \"" + args[2] + " not found.");
					return false;
				}

				target.setCompassTarget(toFollow.getLocation());
				compass.addTracker(target, toFollow);
				sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GOLD + "'s" + ChatColor.BLUE
						+ " compass is now pointing to " + ChatColor.GOLD + toFollow.getName() + ChatColor.BLUE + ".");
				return true;

			case "pos":
				if (args.length < 2) {
					sender.sendMessage(ChatColor.RED + "No location specified.");
					return false;
				}

				if (args[2].toLowerCase().startsWith("c")) {
					target.setCompassTarget(target.getLocation());
					sender.sendMessage(
							ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is now pointing to"
									+ ChatColor.GOLD + target.getName() + ChatColor.BLUE + " current location.");
					return true;
				}
				if (args.length < 5) {
					sender.sendMessage(ChatColor.RED + "/compass pos current or /compass pos <x> <y> <z>.");
					return false;
				}

				try {
					int x = Integer.parseInt(args[2]);
					int y = Integer.parseInt(args[3]);
					int z = Integer.parseInt(args[4]);
					target.setCompassTarget(target.getWorld().getBlockAt(x, y, z).getLocation());
					sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE
							+ "'s compass is now pointing to" + ChatColor.GRAY + x + ChatColor.BLUE + ", "
							+ ChatColor.GRAY + y + ChatColor.BLUE + ", " + ChatColor.GRAY + z + ChatColor.BLUE + ".");
					return true;
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Failed to convert positions.");
					return false;
				}

			case "n":
			case "north":
				target.setCompassTarget(
						target.getWorld().getBlockAt((int) target.getLocation().getX(), 0, -12550820).getLocation());
				sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is facing North.");
				return true;

			case "e":
			case "east":
				target.setCompassTarget(
						target.getWorld().getBlockAt(12550820, 0, (int) target.getLocation().getZ()).getLocation());
				sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is facing east.");
				return true;

			case "s":
			case "south":
				target.setCompassTarget(
						target.getWorld().getBlockAt((int) target.getLocation().getX(), 0, 12550820).getLocation());
				sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is facing south.");
				return true;

			case "w":
			case "west":
				target.setCompassTarget(
						target.getWorld().getBlockAt(-12550820, 0, (int) target.getLocation().getZ()).getLocation());
				sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is facing west.");
				return true;

			case "bed":
				if (target.getBedSpawnLocation() != null) {
					target.setCompassTarget(target.getBedSpawnLocation());
					sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE
							+ "'s compass is facing your bed's location.");
					return true;
				} else {
					sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.BLUE + "doesn't have a bed.");
					return false;
				}

			case "r":
			case "reset":
			case "default":
				target.setCompassTarget(target.getWorld().getSpawnLocation());
				sender.sendMessage(
						ChatColor.GOLD + target.getName() + ChatColor.BLUE + "'s compass is facing world's spawnpoint");
				compass.removeTrackerFromRequester(target);
				return true;

			default:
				sender.sendMessage("Syntax error: \"" + args[1] + "\" is not a valid direction or command.");
				return false;
			}
		}
		return false;
	}
}