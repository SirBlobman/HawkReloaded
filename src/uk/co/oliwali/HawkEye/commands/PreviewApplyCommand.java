package uk.co.oliwali.HawkEye.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.PlayerSession;
import uk.co.oliwali.HawkEye.Rollback;
import uk.co.oliwali.HawkEye.Rollback.RollbackType;
import uk.co.oliwali.HawkEye.SessionManager;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * Applies a local rollback to the world
 * Error handling for user input is done using exceptions to keep code neat.
 *
 * @author oliverw92
 */
public class PreviewApplyCommand extends BaseCommand {

    public PreviewApplyCommand() {
        name = "preview apply";
        permission = "preview";
        usage = "<- apply rollback preview";
    }

    @Override
    public boolean execute(Player sender, String[] args) {
        PlayerSession session = SessionManager.getSession(sender);

        //Check if player already has a rollback processing
        if (!session.isInPreview()) {
            Util.sendMessage(sender, "&cNo preview to apply!");
            return true;
        }

        //Undo local changes to the player
        Util.sendMessage(sender, "&cAttempting to apply rollback to world...");
        new Rollback(RollbackType.GLOBAL, session);
        session.setInPreview(false);

        return true;
    }

    @Override
    public void moreHelp(CommandSender sender) {
        Util.sendMessage(sender, "&cApplies the results of a &7/hawk preview&c globally");
        Util.sendMessage(sender, "&cUntil this command is called, the preview is only visible to you");
    }

}
