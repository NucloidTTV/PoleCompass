package nl.function1.polepass;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CompassEvents implements Listener{

	public Compass compass;
	CompassEvents(Compass compassevents){
	this.compass = compassevents;	
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		Object data = compass.getMetadata(p, "compasstrackers", compass);
		if (data == null) {
			return;
		}

		String[] players = ((String) data).split(",");

		for (String pr : players) {
			Player toUpdate = Bukkit.getServer().getPlayer(pr);
			if (toUpdate != null) {
				toUpdate.setCompassTarget(p.getLocation());
			} else {
				compass.removeRequesterFromTrackingOnly(p, pr);
			}
		}
	}

	// for a future release
	/*
	 * @EventHandler public void onPlayerUse(PlayerInteractEvent e) { Player p =
	 * e.getPlayer();
	 * 
	 * // TODO: compatibility with 1.8 if
	 * (!(p.getInventory().getItemInMainHand().getType() == Material.COMPASS ||
	 * p.getInventory().getItemInOffHand().getType() == Material.COMPASS)) { return;
	 * }
	 * 
	 * float yaw = p.getLocation().getYaw() % 360.0f; p.sendMessage(yaw + "");
	 * 
	 * if (getMetadata(p, "poledirection", this) != null) { // Tell what direction
	 * the player is facing
	 * 
	 * p.sendMessage("You're currently facing the " + whatDirection(yaw) + "(" + yaw
	 * + ")"); } else { // Tell how far away in a certain direction the player from
	 * it's target is
	 * 
	 * // TODO: FIX Location pp = p.getLocation(); Location cp =
	 * p.getCompassTarget();
	 * 
	 * double px = pp.getX(); double pz = pp.getZ();
	 * 
	 * double cx = cp.getX(); double cz = cp.getZ();
	 * 
	 * // distances (delta) double xd = cx - px; double zd = cz - pz;
	 * 
	 * // distance (||delta||) double d = Math.sqrt(Math.pow(xd, 2) + Math.pow(zd,
	 * 2));
	 * 
	 * // absolute angle ignoring players rotation facing positive x-axis(east)(yaw
	 * = 270) double angle = 0.0;//Math.toDegrees(Math.tanh(zd / xd));
	 */

	// TODO: For each case(xd > 0, zd > 0) seperate tanh parameters, initial angle,
	// etc etc SO IT WORKS

	/*
	 * if (xd > 0.0) { // target is in positive x(east) angle =
	 * Math.toDegrees(Math.tanh(zd / xd)) + 270.0; } else { // target is in negative
	 * x(west) angle = Math.toDegrees(Math.tanh(-zd / xd)) + 90.0; }
	 */

	/*
	 * if (zd > 0.0) { // target is in positive z(south) } else { // target is in
	 * negative z(north) }
	 */

	// p.sendMessage(ChatColor.GOLD + "Target is ~" + Math.round(d) + " blocks in
	// the " + whatDirection((float)angle) + "(" + angle + ")");

	/*
	 * double xd = Math.abs(px - cx); double zd = Math.abs(pz - cz);
	 * 
	 * double d = Math.sqrt(Math.pow(xd, 2) + Math.pow(zd, 2)); long distance =
	 * Math.round(d);
	 * 
	 * if (px > cx) { // target is relatively west } else { // target is relatively
	 * east }
	 * 
	 * if (pz > cz) { // target is relatively north } else { // target is relatively
	 * south }
	 * 
	 * double angle = Math.tanh(zd / xd);
	 * 
	 * 
	 * // directions p.sendMessage(ChatColor.GOLD + "UR " + distance +
	 * " BLACKS AWEAY FROM TARGET"); p.sendMessage(ChatColor.GOLD + "TARGET IS " +
	 * Math.round(xd) + " " + (px > cx ? "west" : "east") + " of you");
	 * p.sendMessage(ChatColor.GOLD + "TARGET IS " + Math.round(zd) + " " + (pz > cz
	 * ? "north" : "south") + " of you");
	 */

	// }
	// }

	/*
	 * private String whatDirection(float angle) {
	 * 
	 * /*if (angle < 45.0f || angle > 315.0f) { return "south"; } else if (angle <
	 * 135.0f) { // west return "west"; } else if (angle < 225.0f){ //north return
	 * "north"; } else if (angle < 315.0f) { //east return "east"; }
	 */
	/*
	 * if (angle < 22.5f || angle > 337.5f) { return "south"; } else if (angle <
	 * 67.5f) { return "south-west"; } else if (angle < 112.5f) { return "west"; }
	 * else if (angle < 157.5f) { return "north-west"; } else if (angle < 202.5f) {
	 * return "north"; } else if (angle < 247.5f) { return "north-east"; } else if
	 * (angle < 292.5f) { return "east"; } else if (angle < 337.5f) { return
	 * "south-east"; }
	 * 
	 * return "xD"; }
	 */

	/*
	 * private enum Direction { None, North, East, South, West, Default, Bed; }
	 */
}
