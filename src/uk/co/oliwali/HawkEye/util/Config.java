package uk.co.oliwali.HawkEye.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;

import uk.co.oliwali.HawkEye.HawkEye;

/**
 * Configuration manager for HawkEye.
 * Any field with the first letter capitalized is a config option
 * @author oliverw92
 */
public class Config {

	public static Set<String> CommandFilter = new HashSet<>();
	public static Set<String> IgnoreWorlds = new HashSet<String>();
	public static Set<Integer> BlockFilter = new HashSet<Integer>();
	public static List<String> CleanseActions = new ArrayList<String>();
	public static int MaxLines = 0;
	public static int MaxRadius;
	public static int DefaultHereRadius;
	public static int DefaultEditSpeed;
	public static int MaxEditSpeed;
	public static Material ToolBlock;
	public static String[] DefaultToolCommand;
	public static String CleanseAge;
	public static String CleansePeriod;
	public static boolean SuperPick;
	public static boolean WEPlace;
	public static boolean WEBreak;
	public static boolean GiveTool;
	public static boolean CheckUpdates;
	public static boolean Debug;
	public static Util.DebugLevel DebugLevel;
	public static boolean LogIpAddresses;
	public static boolean DeleteDataOnRollback;
	public static boolean LogDeathDrops;
	public static boolean OpPermissions;
	public static boolean isSimpleTime;
	public static boolean logChest;
	public static boolean logDoubleChest;
	public static boolean logFurnace;
	public static boolean logDispenser;
	public static boolean LogHopper;
	public static boolean LogDropper;
	public static int LogDelay;
	public static int MaxLog;
	public static String DbUser;
	public static String DbPassword;
	public static String DbDatabase;
	public static String DbPort;
	public static String DbHawkEyeTable;
	public static String DbPlayerTable;
	public static String DbWorldTable;
	public static String DbHostname;
	public static int PoolSize;
	public static boolean populateCachesOnBoot;

	private static Configuration config;

	/**
	 * Loads the config from file and validates the data
	 * @param plugin
	 */
	public Config(HawkEye plugin) {

		config = plugin.getConfig().getRoot();
		config.options().copyDefaults(true);
		config.set("version", HawkEye.version);
        plugin.saveConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();

		//Load values
		CommandFilter = new HashSet<>(config.getStringList("command-filter"));
		BlockFilter = new HashSet<>(config.getIntegerList("block-filter"));
		IgnoreWorlds = new HashSet<>(config.getStringList("ignore-worlds"));
		CleanseActions = Arrays.asList(config.getString("general.cleanse-actions").split(","));
		MaxLines = config.getInt("general.max-lines");
		MaxRadius = config.getInt("general.max-radius");
		MaxLog = config.getInt("general.max-write-logs");
		DefaultEditSpeed = config.getInt("general.default-edit-speed");
		MaxEditSpeed = config.getInt("general.max-edit-speed");
		DefaultHereRadius = config.getInt("general.default-here-radius");
		ToolBlock = Material.getMaterial(Integer.parseInt(config.getString("general.tool-block")));
		DefaultToolCommand = config.getString("general.default-tool-command").split(" ");
		CleanseAge = config.getString("general.cleanse-age");
		CleansePeriod = config.getString("general.cleanse-period");
		SuperPick = config.getBoolean("log.super-pickaxe");
		WEPlace = config.getBoolean("log.worldedit-place");
		WEBreak = config.getBoolean("log.worldedit-break");
		GiveTool = config.getBoolean("general.give-user-tool");
		CheckUpdates = config.getBoolean("general.check-for-updates");
		Debug = config.getBoolean("general.debug");
		LogIpAddresses = config.getBoolean("general.log-ip-addresses");
		DeleteDataOnRollback = config.getBoolean("general.delete-data-on-rollback");
		LogDeathDrops = config.getBoolean("general.log-item-drops-on-death");
		OpPermissions = config.getBoolean("general.op-permissions");
		isSimpleTime = config.getBoolean("general.simplify-time");
		LogDelay = config.getInt("general.log-delay");
		DbUser = config.getString("mysql.username");
		DbPassword = config.getString("mysql.password");
		DbPort = config.getString("mysql.port");
		DbDatabase = config.getString("mysql.database");
		DbHawkEyeTable = config.getString("mysql.hawkeye-table");
		DbPlayerTable = config.getString("mysql.player-table");
		DbWorldTable = config.getString("mysql.world-table");
		DbHostname = config.getString("mysql.hostname");
		PoolSize = config.getInt("mysql.max-connections");
		logChest = config.getBoolean("containertransaction-filter.chest");
		logDoubleChest = config.getBoolean("containertransaction-filter.doublechest");
		logFurnace = config.getBoolean("containertransaction-filter.furnace");
		logDispenser = config.getBoolean("containertransaction-filter.dispenser");
		LogHopper = config.getBoolean("containertransaction-filter.hopper");
		LogDropper = config.getBoolean("containertransaction-filter.dropper");
		populateCachesOnBoot = config.getBoolean("general.populate-caches-onboot");

		try {
			DebugLevel = Util.DebugLevel.valueOf(config.getString("general.debug-level").toUpperCase());
		} catch (Exception ex) {
			DebugLevel = Util.DebugLevel.NONE;
		}

	}
}
