package uk.co.oliwali.HawkEye.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.HawkEvent;
import uk.co.oliwali.HawkEye.HawkEye;
import uk.co.oliwali.HawkEye.database.Consumer;
import uk.co.oliwali.HawkEye.entry.BlockChangeEntry;

/**
 * FallingBlockEntity listener class for HawkEye
 * Contains system for logging Fallingblocks
 * @author bob7l
 */
public class MonitorFallingBlockListener extends HawkEyeListener {

	private HashMap<Entity, String> blocks = new HashMap<Entity, String>();

	public MonitorFallingBlockListener(Consumer consumer) {
		super(consumer);
	}

	@HawkEvent(dataType = DataType.FALLING_BLOCK)
	public void onBlockPlace(BlockPlaceEvent event) {
		Material type = event.getBlock().getType();
		if ((type.equals(Material.SAND) || type.equals(Material.GRAVEL) || type.equals(Material.ANVIL)) && event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
			final BlockPlaceEvent finalEvent = event;
			Bukkit.getScheduler().scheduleSyncDelayedTask(HawkEye.getInstance(), new Runnable() {
				@Override
				public void run() {
					Location l = finalEvent.getBlock().getLocation();
					for (Entity e : l.getWorld().getEntitiesByClass(FallingBlock.class)) {
						if (l.distanceSquared(e.getLocation()) <= 0.8) {
							blocks.put(e, finalEvent.getPlayer().getName());
							return;
						}
					}
				}
			}, 6L);
		}
	}

	@HawkEvent(dataType = DataType.FALLING_BLOCK) 
	public void onEntityModifyBlock(EntityChangeBlockEvent event) {
		Entity en = event.getEntity();
		if (en instanceof FallingBlock && blocks.containsKey(en)) {
			FallingBlock fb = (FallingBlock)en;
			Block b = event.getBlock();
			String data = "" + (fb.getBlockData() == 0?fb.getBlockId():fb.getBlockId() + ":" + fb.getBlockData());
			consumer.addEntry(new BlockChangeEntry(blocks.get(en), DataType.FALLING_BLOCK, b.getLocation(), event.getBlock().getState(), data));
		}
	}
}