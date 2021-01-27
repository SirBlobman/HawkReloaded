package uk.co.oliwali.HawkEye.callbacks;

import java.util.List;

import uk.co.oliwali.HawkEye.LogManager;
import uk.co.oliwali.HawkEye.PlayerSession;
import uk.co.oliwali.HawkEye.entry.DataEntry;
import uk.co.oliwali.HawkEye.util.Util;

public class WriteLogCallback extends QueryCallback {

    public WriteLogCallback(PlayerSession session) {
        super(session);
        Util.sendMessage(session.getSender(), "&cSearching for matching results...");
    }

    @Override
    public void call(List<DataEntry> dataEntries) {
        session.setSearchResults(dataEntries);

        LogManager.log(session);
    }

}
