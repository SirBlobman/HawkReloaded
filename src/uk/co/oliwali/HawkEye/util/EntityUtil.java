package uk.co.oliwali.HawkEye.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.entry.HangingEntry;

public class EntityUtil {

	public static int getFace(BlockFace block) {
		switch(block) {
		case SOUTH: return 0;
		case WEST: return 1;
		case NORTH: return 2;
		case EAST: return 3;
		default:
			return 0;
		}
	}

	public static BlockFace getFaceFromInt(int block) {
		switch(block) {
		case 0: return BlockFace.SOUTH;
		case 1: return BlockFace.WEST;
		case 2: return BlockFace.NORTH;
		case 3: return BlockFace.EAST;
		default:
			return BlockFace.SOUTH;
		}
	}


	public static void setEntityString(Block b, String data) {
		EntityType type = EntityType.fromName(data);
		Location loc = b.getLocation();
		try {
			loc.getWorld().spawnEntity(loc, type);
		} catch (Exception e) {
			Util.warning("Unable to spawn " + data + " at: " + loc.toString());
		}
	}

	public static String entityToString(Entity e) {
		if (e == null || e.getType() == null) return "Environment";
		if (e instanceof Player) return ((Player)e).getName();
		return e.getType().name();
	}

	public static HangingEntry getHangingEntry(DataType type, Entity e, String remover) {

		if (e instanceof ItemFrame) {
			ItemFrame frame = (ItemFrame) e;
			return new HangingEntry(remover, type, e.getLocation().getBlock().getLocation(), 389, getFace(frame.getAttachedFace()), frame.getItem());
		} else if (e instanceof Painting) {
			Painting paint = (Painting) e;
			return new HangingEntry(remover, type, e.getLocation().getBlock().getLocation(), 321, getFace(paint.getAttachedFace()), Integer.toString(paint.getArt().getId()));
		}
		return null;
	}

	public static List<Entity> getEntitiesAtLoc(Location l) {
		List<Entity> entities = new ArrayList<Entity>(1);

		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();

		for (Entity e : l.getChunk().getEntities()) {
			Location el = e.getLocation();

			if (el.getBlockX() == x && el.getBlockZ() == z && el.getBlockY() == y) {
				 entities.add(e);
			}
		}

		return entities;
	}

}