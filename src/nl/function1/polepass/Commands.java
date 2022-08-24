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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("compass")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to set your own compass!");
				return false;
			}

			if (args.length < 1) {
				sender.sendMessage("No arguments specified! View \"compass help\" for help.");
				return false;
			}

			Player p = (Player) sender;

			switch (args[0].toLowerCase()) {

			case "help":
				p.sendMessage(ChatColor.GOLD + "/compass [direction]" + ChatColor.GRAY
						+ " - sets your compass direction to North, West, East or South.");
				p.sendMessage(ChatColor.GOLD + "/compass pos <x> <y> <z>" + ChatColor.GRAY
						+ " - sets your compass direction to the specific location.");
				p.sendMessage(ChatColor.GOLD + "/compass pos current" + ChatColor.GRAY
						+ " - sets your compass direction to your current position.");
				p.sendMessage(ChatColor.GOLD + "/compass follow <player>" + ChatColor.GRAY
						+ " - sets your compass direction to someone else's" + " position. Keeps updating.");
				// p.sendMessage(ChatColor.GOLD + "/compass about" + ChatColor.GRAY + " - views
				// the info of the plugin.");
				p.sendMessage(ChatColor.GOLD + "/compass bed" + ChatColor.GRAY
						+ " - sets your compass to your bed's location");
				p.sendMessage(ChatColor.GOLD + "To make your compass normal again, use /compass reset.");
				p.sendMessage(ChatColor.GOLD
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
					p.sendMessage(ChatColor.AQUA + name + " " + ver);
					p.sendMessage(ChatColor.GOLD + des);
					p.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.RESET + aut);
					p.sendMessage(ChatColor.GOLD + "URL: " + ChatColor.RESET + url);
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
					p.sendMessage(ChatColor.RED + "Syntax error: Please enter a player to point to with your compass");
					return false;
				}

				Player toFollow = Bukkit.getServer().getPlayer(args[1]);

				if (toFollow != null) {

					p.setCompassTarget(toFollow.getLocation());
					compass.addTracker(p, toFollow);
					p.sendMessage("You now follow " + toFollow.getName() + " with your compass!");
					return true;

				} else {
					p.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not found on this server");
					return false;
				}

			case "pos":
				if (args.length < 2) {
					p.sendMessage(ChatColor.RED + "Usage: /compass pos current or /compass pos <x> <y> <z>");
					return false;
				}
				if (args[1].toLowerCase().startsWith("c")) {
					p.setCompassTarget(p.getLocation());
					p.sendMessage("Your compass is now pointing to your current location.");
					return true;
				}
				if (args.length < 4) {
					p.sendMessage(ChatColor.RED + "Usage: /compass pos current or /compass pos <x> <y> <z>");
					return false;
				}

				try {
					int x = Integer.parseInt(args[1]);
					int y = Integer.parseInt(args[2]);
					int z = Integer.parseInt(args[3]);
					p.setCompassTarget(p.getWorld().getBlockAt(x, y, z).getLocation());
					p.sendMessage("Your compass is now pointing to " + x + ", " + y + ", " + z);
					return true;
				} catch (Exception ex) {
					p.sendMessage("Failed to convert positions");
					return false;
				}

			case "n":
			case "north":
				p.setCompassTarget(p.getWorld().getBlockAt((int) p.getLocation().getX(), 0, -12550820).getLocation());
				p.sendMessage("Your compass has been set to the North");
				return true;

			case "e":
			case "east":
				p.setCompassTarget(p.getWorld().getBlockAt(12550820, 0, (int) p.getLocation().getZ()).getLocation());
				p.sendMessage("Your compass has been set to the East");
				return true;

			case "s":
			case "south":
				p.setCompassTarget(p.getWorld().getBlockAt((int) p.getLocation().getX(), 0, 12550820).getLocation());
				p.sendMessage("Your compass has been set to the South");
				return true;

			case "w":
			case "west":
				p.setCompassTarget(p.getWorld().getBlockAt(-12550820, 0, (int) p.getLocation().getZ()).getLocation());
				p.sendMessage("Your compass has been set to the West");
				return true;

			case "bed":
				if (p.getBedSpawnLocation() != null) {
					p.setCompassTarget(p.getBedSpawnLocation());
					p.sendMessage("Your compass has been set to your bed's location");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "You don't have a bed");
					return false;
				}

			case "r":
			case "reset":
			case "default":
				p.setCompassTarget(p.getWorld().getSpawnLocation());
				p.sendMessage("Your compass has been set to the world's spawnpoint");
				compass.removeTrackerFromRequester(p);
				return true;

			default:
				p.sendMessage(ChatColor.RED + "\"" + args[0] + "\" is not a valid direction.");
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("setplayercompass")) {

			if (args.length < 2) {
				sender.sendMessage("Syntax error: Too few arguments!");
				return false;
			}

			Player target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				sender.sendMessage("Error: Player \"" + args[0] + "\" not found");
				return false;
			}

			switch (args[1].toLowerCase()) {

			case "help":
				// this is only hit when the first argument is a player
				sender.sendMessage(
						"/" + cmd.getName().toLowerCase() + " <player> <compass command>. See /compass help");
				return true;

			case "f":
			case "follow":
				if (args.length < 3) {
					sender.sendMessage("Syntax error: missing player to follow");
					return false;
				}

				Player toFollow = Bukkit.getServer().getPlayer(args[2]);

				if (toFollow == null) {
					sender.sendMessage("Player \"" + args[2] + " not found");
					return false;
				}

				target.setCompassTarget(toFollow.getLocation());
				compass.addTracker(target, toFollow);
				sender.sendMessage(target.getName() + "'s compass is now pointing to " + toFollow.getName());
				return true;

			case "pos":
				if (args.length < 2) {
					sender.sendMessage("Syntax error: no location specified");
					return false;
				}

				if (args[2].toLowerCase().startsWith("c")) {
					target.setCompassTarget(target.getLocation());
					sender.sendMessage(target.getName() + "'s compass is now pointing to " + target.getName()
							+ " current location");
					return true;
				}
				if (args.length < 5) {
					sender.sendMessage(ChatColor.RED + "Usage: /compass pos current or /compass pos <x> <y> <z>");
					return false;
				}

				try {
					int x = Integer.parseInt(args[2]);
					int y = Integer.parseInt(args[3]);
					int z = Integer.parseInt(args[4]);
					target.setCompassTarget(target.getWorld().getBlockAt(x, y, z).getLocation());
					sender.sendMessage(target.getName() + "'s compass is now pointing to " + x + ", " + y + ", " + z);
					return true;
				} catch (Exception ex) {
					sender.sendMessage("Failed to convert positions");
					return false;
				}

			case "n":
			case "north":
				target.setCompassTarget(
						target.getWorld().getBlockAt((int) target.getLocation().getX(), 0, -12550820).getLocation());
				sender.sendMessage(target.getName() + "'s compass has been set to the North");
				return true;

			case "e":
			case "east":
				target.setCompassTarget(
						target.getWorld().getBlockAt(12550820, 0, (int) target.getLocation().getZ()).getLocation());
				sender.sendMessage(target.getName() + "'s compass has been set to the East");
				return true;

			case "s":
			case "south":
				target.setCompassTarget(
						target.getWorld().getBlockAt((int) target.getLocation().getX(), 0, 12550820).getLocation());
				sender.sendMessage(target.getName() + "'s compass has been set to the South");
				return true;

			case "w":
			case "west":
				target.setCompassTarget(
						target.getWorld().getBlockAt(-12550820, 0, (int) target.getLocation().getZ()).getLocation());
				sender.sendMessage(target.getName() + "'s compass has been set to the West");
				return true;

			case "bed":
				if (target.getBedSpawnLocation() != null) {
					target.setCompassTarget(target.getBedSpawnLocation());
					sender.sendMessage(target.getName() + "'s compass has been set to your bed's location");
					return true;
				} else {
					sender.sendMessage(target.getName() + " doesn't have a bed");
					return false;
				}

			case "r":
			case "reset":
			case "default":
				target.setCompassTarget(target.getWorld().getSpawnLocation());
				sender.sendMessage(target.getName() + "'s compass has been set to the world's spawnpoint");
				compass.removeTrackerFromRequester(target);
				return true;

			default:
				sender.sendMessage("Syntax error: \"" + args[1] + "\" is not a valid direction or command");
				return false;
			}
		}
		return false;
	}
}
