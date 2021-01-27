package uk.co.oliwali.HawkEye;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.commands.BaseCommand;
import uk.co.oliwali.HawkEye.commands.DeleteCommand;
import uk.co.oliwali.HawkEye.commands.HelpCommand;
import uk.co.oliwali.HawkEye.commands.HereCommand;
import uk.co.oliwali.HawkEye.commands.InfoCommand;
import uk.co.oliwali.HawkEye.commands.PageCommand;
import uk.co.oliwali.HawkEye.commands.PreviewApplyCommand;
import uk.co.oliwali.HawkEye.commands.PreviewCancelCommand;
import uk.co.oliwali.HawkEye.commands.PreviewCommand;
import uk.co.oliwali.HawkEye.commands.RebuildCommand;
import uk.co.oliwali.HawkEye.commands.ReloadCommand;
import uk.co.oliwali.HawkEye.commands.RollbackCommand;
import uk.co.oliwali.HawkEye.commands.SearchCommand;
import uk.co.oliwali.HawkEye.commands.ToolBindCommand;
import uk.co.oliwali.HawkEye.commands.ToolCommand;
import uk.co.oliwali.HawkEye.commands.ToolResetCommand;
import uk.co.oliwali.HawkEye.commands.TptoCommand;
import uk.co.oliwali.HawkEye.commands.UndoCommand;
import uk.co.oliwali.HawkEye.commands.WriteLogCommand;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * @author bob7l
 */
public class HawkCommand implements CommandExecutor {

    private BaseCommand[] cmds = {
            new HelpCommand(this),
            new ToolBindCommand(),
            new ToolResetCommand(),
            new ToolCommand(),
            new SearchCommand(),
            new PageCommand(),
            new TptoCommand(),
            new HereCommand(),
            new PreviewApplyCommand(),
            new PreviewCancelCommand(),
            new PreviewCommand(),
            new RollbackCommand(),
            new UndoCommand(),
            new RebuildCommand(),
            new DeleteCommand(),
            new InfoCommand(),
            new WriteLogCommand(),
            new ReloadCommand()
    };

    @Override
    public boolean onCommand(CommandSender sender, Command ignore, String s, String[] args) {

        BaseCommand command = getCommand(args);

        if (command != null) {

            if (Util.hasPerm(sender, command.getPermission())) {

                args = Arrays.copyOfRange(args, 1, args.length);

                if (args.length >= command.getArgLength()) {

                    if (sender instanceof Player)
                        return command.execute(((Player) sender), args);
                    else
                        return command.execute(sender, args);
                } else {
                    Util.sendMessage(sender, "&cWrong usage: &7/hawk " + command.getName() + " " + command.getUsage());
                }

            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }

            return true;

        } else {
            return cmds[0].execute(sender, new String[0]);
        }
    }

    public BaseCommand getCommand(String... args) {
        if (args.length > 0) {

            for (BaseCommand cmd : cmds) {
                String[] subcmds = cmd.getName().split(" "); //For sub-commands with sub-commands

                if (subcmds.length > 1 && args.length >= subcmds.length) {
                    boolean equals = false;

                    for (int i = 1; i < subcmds.length; i++)
                        equals = (subcmds[i].equalsIgnoreCase(args[i]));

                    if (equals) return cmd;

                } else if (cmd.getName().equalsIgnoreCase(args[0])) {
                    return cmd;
                }
            }

        }
        return null;
    }

    public BaseCommand[] getSubCommands() {
        return cmds;
    }
}
