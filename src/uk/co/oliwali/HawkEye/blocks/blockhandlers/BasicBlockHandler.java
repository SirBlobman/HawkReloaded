package uk.co.oliwali.HawkEye.blocks.blockhandlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.database.Consumer;
import uk.co.oliwali.HawkEye.entry.SignEntry;
import uk.co.oliwali.HawkEye.util.BlockUtil;

public class BasicBlockHandler implements BlockHandler {

    @Override
    public void restore(Block b, int id, int data) {
        b.setTypeIdAndData(id, ((byte) data), false);
    }

    @Override
    public void logAttachedBlocks(Consumer consumer, Block b, Player p, DataType type) {
        for (BlockFace face : BlockUtil.faces) {

            Block attch = b.getRelative(face);

            if (attch.getType() == Material.WALL_SIGN) {
                if (DataType.SIGN_BREAK.isLogged())
                    consumer.addEntry(new SignEntry(p, DataType.SIGN_BREAK, attch));
            }
        }
    }

    @Override
    public Block getCorrectBlock(Block b) {
        return b;
    }

    @Override
    public boolean isTopBlock() {
        return false;
    }

    @Override
    public boolean isAttached() {
        return false;
    }


}