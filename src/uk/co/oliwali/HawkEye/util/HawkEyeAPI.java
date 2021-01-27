package uk.co.oliwali.HawkEye.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.HawkEye;
import uk.co.oliwali.HawkEye.SearchParser;
import uk.co.oliwali.HawkEye.callbacks.QueryCallback;
import uk.co.oliwali.HawkEye.database.userqueries.Query.SearchDir;
import uk.co.oliwali.HawkEye.database.userqueries.SearchQuery;
import uk.co.oliwali.HawkEye.entry.DataEntry;

/**
 * API for other plugins.
 * See the wiki for information on how to correctly access this API: https://github.com/oliverw92/HawkEye/wiki/
 *
 * @author oliverw92
 */
public class HawkEyeAPI {

    /**
     * Add a custom entry to the HawkEye database
     *
     * @param plugin instance of your plugin (i.e. just pass 'this' from your main class)
     * @param action action that has been performed (e.g. Go Home)
     * @param player player that performed the action
     * @param loc    location of the event
     * @param data   data relevant to the event
     */
    @Deprecated
    public static boolean addCustomEntry(JavaPlugin plugin, String action, Player player, Location loc, String data) {
        return addCustomEntry(plugin, action, player.getName(), loc, data);
    }

    @Deprecated
    public static boolean addCustomEntry(JavaPlugin plugin, String action, String player, Location loc, String data) {
        addCustomEntry(action, player, loc, data);
        return true;
    }

    public static void addCustomEntry(String action, String player, Location loc, String data) {
        if (action == null || player == null || loc == null || data == null)
            throw new NullPointerException();

        addEntry(new DataEntry(player, DataType.OTHER, loc, action + "-" + data));
    }

    /**
     * Add a standard entry to the HawkEye database
     *
     * @return true if accepted, false if not
     */
    @Deprecated
    public static boolean addEntry(JavaPlugin plugin, DataEntry entry) {
        return addEntry(entry);
    }

    public static boolean addEntry(DataEntry entry) {

        if (entry.getClass() != entry.getType().getEntryClass() ||
                entry.getPlayer() == null)
            return false;

        HawkEye.getDbmanager().getConsumer().addEntry(entry);

        return true;
    }

    /**
     * Performs a search of the HawkEye database
     *
     * @param callBack implementation of {@BaseCallback} for {@SearchQuery} to call
     * @param parser   instance of {@SearchParser} to retrieve search information
     * @param dir      direction to list results as specified in {@SearchDir}
     */
    public static void performSearch(QueryCallback callBack, SearchParser parser, SearchDir dir) {
        new SearchQuery(callBack, parser, dir);
    }

}
