package uk.co.oliwali.HawkEye.callbacks;

import java.util.List;

import uk.co.oliwali.HawkEye.DisplayManager;
import uk.co.oliwali.HawkEye.PlayerSession;
import uk.co.oliwali.HawkEye.entry.DataEntry;
import uk.co.oliwali.HawkEye.util.Util;

/**
 * Implementation of BaseCallback for use in search commands
 *
 * @author oliverw92
 */
public class SearchCallback extends QueryCallback {

    public SearchCallback(PlayerSession session) {
        super(session);

        Util.sendMessage(session.getSender(), "&cSearching for matching results...");
    }

    @Override
    public void call(List<DataEntry> dataEntries) {
        session.setSearchResults(dataEntries);

        DisplayManager.displayPage(session, 1);
    }

}
