package uk.co.oliwali.HawkEye.callbacks;

import java.util.List;

import org.bukkit.command.CommandSender;

import uk.co.oliwali.HawkEye.PlayerSession;
import uk.co.oliwali.HawkEye.entry.DataEntry;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * @author bob7l
 */
public abstract class QueryCallback implements Callback<List<DataEntry>> {

    protected final PlayerSession session;

    private final CommandSender sender;

    public QueryCallback(PlayerSession session) {
        this.session = session;
        this.sender = session.getSender();
    }

    @Override
    public void call(List<DataEntry> dataEntries) {
        session.setRollbackResults(dataEntries);
    }

    @Override
    public void fail(Throwable throwable) {
        Util.sendMessage(sender, throwable.getMessage());
    }
}
