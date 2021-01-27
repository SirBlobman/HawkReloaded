package uk.co.oliwali.HawkEye;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import uk.co.oliwali.HawkEye.entry.BlockChangeEntry;
import uk.co.oliwali.HawkEye.entry.BlockEntry;
import uk.co.oliwali.HawkEye.entry.DataEntry;
import uk.co.oliwali.HawkEye.entry.EntityEntry;
import uk.co.oliwali.HawkEye.entry.HangingEntry;
import uk.co.oliwali.HawkEye.entry.ItemFrameModifyEntry;
import uk.co.oliwali.HawkEye.entry.SignEntry;
import uk.co.oliwali.HawkEye.entry.SimpleRollbackEntry;
import uk.co.oliwali.HawkEye.entry.containerentries.ContainerExtract;
import uk.co.oliwali.HawkEye.entry.containerentries.ContainerInsert;

/**
 * Enumeration class representing all the different actions that HawkEye can handle
 *
 * @author oliverw92
 */
public enum DataType {

    BLOCK_BREAK(0, BlockEntry.class, "block-break", true, true),
    BLOCK_PLACE(1, BlockChangeEntry.class, "block-place", true, true),
    SIGN_PLACE(2, SignEntry.class, "sign-place", true, true),
    CHAT(3, DataEntry.class, "chat", false, false),
    COMMAND(4, DataEntry.class, "command", false, false),
    JOIN(5, DataEntry.class, "join", false, false),
    QUIT(6, DataEntry.class, "quit", false, false),
    TELEPORT(7, DataEntry.class, "teleport", false, false),
    LAVA_BUCKET(8, BlockChangeEntry.class, "lava-bucket", true, true),
    WATER_BUCKET(9, BlockChangeEntry.class, "water-bucket", true, true),
    OPEN_CONTAINER(10, DataEntry.class, "open-container", true, false),
    DOOR_INTERACT(11, DataEntry.class, "door-interact", true, false),
    PVP_DEATH(12, DataEntry.class, "pvp-death", false, false),
    FLINT_AND_STEEL(13, BlockChangeEntry.class, "flint-steel", true, true),
    LEVER(14, DataEntry.class, "lever", true, false),
    STONE_BUTTON(15, DataEntry.class, "button", true, false),
    OTHER(16, DataEntry.class, "other", false, false),
    EXPLOSION(17, BlockEntry.class, "explosion", true, true),
    BLOCK_BURN(18, BlockEntry.class, "block-burn", true, true),
    BLOCK_FORM(19, BlockChangeEntry.class, "block-form", true, true),
    LEAF_DECAY(20, BlockEntry.class, "leaf-decay", true, true),
    MOB_DEATH(21, DataEntry.class, "mob-death", false, false),
    OTHER_DEATH(22, DataEntry.class, "other-death", false, false),
    ITEM_DROP(23, DataEntry.class, "item-drop", false, false),
    ITEM_PICKUP(24, DataEntry.class, "item-pickup", false, false),
    BLOCK_FADE(25, BlockChangeEntry.class, "block-fade", true, true),
    LAVA_FLOW(26, BlockChangeEntry.class, "lava-flow", true, true),
    WATER_FLOW(27, BlockChangeEntry.class, "water-flow", true, true),
    //CONTAINER_TRANSACTION(28, ContainerEntry.class, "container-transaction", true, true), - Removed 4/20/2016, Replaced with CONTAINER_EXTRACT/INSERT
    SIGN_BREAK(29, SignEntry.class, "sign-break", true, true),
    ITEM_BREAK(30, HangingEntry.class, "item-break", true, true),
    ITEM_PLACE(31, HangingEntry.class, "item-place", true, true),
    ENDERMAN_PICKUP(32, BlockEntry.class, "enderman-pickup", true, true),
    ENDERMAN_PLACE(33, BlockChangeEntry.class, "enderman-place", true, true),
    TREE_GROW(34, BlockChangeEntry.class, "tree-grow", true, true),
    MUSHROOM_GROW(35, BlockChangeEntry.class, "mushroom-grow", true, true),
    ENTITY_KILL(36, EntityEntry.class, "entity-kill", true, true),
    SPAWNMOB_EGG(37, DataEntry.class, "spawnmob-egg", false, false),
    HEROCHAT(38, DataEntry.class, "herochat", false, false),
    ENTITY_MODIFY(39, BlockEntry.class, "entity-modify", true, true),
    BLOCK_INHABIT(40, BlockEntry.class, "block-inhabit", true, true),
    SUPER_PICKAXE(41, BlockEntry.class, "super-pickaxe", true, true),
    WORLDEDIT_BREAK(42, BlockEntry.class, "worldedit-break", true, true),
    WORLDEDIT_PLACE(43, BlockChangeEntry.class, "worldedit-place", true, true),
    CROP_TRAMPLE(44, BlockEntry.class, "crop-trample", true, true),
    BLOCK_IGNITE(45, SimpleRollbackEntry.class, "block-ignite", true, true),
    FALLING_BLOCK(46, BlockChangeEntry.class, "fallingblock-place", true, true),
    PLAYER_LAVA_FLOW(47, BlockChangeEntry.class, "player-lava-flow", true, true),
    PLAYER_WATER_FLOW(48, BlockChangeEntry.class, "player-water-flow", true, true),
    FRAME_INSERT(49, ItemFrameModifyEntry.class, "frame-insert", true, true),
    FRAME_EXTRACT(50, ItemFrameModifyEntry.class, "frame-extract", true, true),
    CONTAINER_INSERT(51, ContainerInsert.class, "container-insert", true, true),
    CONTAINER_EXTRACT(52, ContainerExtract.class, "container-extract", true, true);

    private int id;
    private boolean canHere;
    private String configName;
    private boolean canRollback;
    private boolean isLogged;
    private Class<? extends DataEntry> entryClass;
    private Constructor<? extends DataEntry> entryConstructor;

    private static final Map<String, DataType> nameMapping = new HashMap<>();
    private static final DataType[] idTable;

    static {
        //Mapping to enable quick finding of DataTypes by name or id

        /*
          Allocate array to the max DataType id + 1, this assumes the DataType enum at the end of the list
          has the largest ID!
        */
        idTable = new DataType[values()[values().length - 1].id + 1];

        for (DataType type : values()) {
            nameMapping.put(type.configName, type);
            idTable[type.id] = type;
        }
    }

    DataType(int id, Class<? extends DataEntry> entryClass, String configName, boolean canHere, boolean canRollback) {
        this.id = id;
        this.entryClass = entryClass;
        this.canHere = canHere;
        this.configName = configName;
        this.canRollback = canRollback;
        this.isLogged = HawkEye.getInstance().getConfig().getBoolean("log." + configName);

        try {
            this.entryConstructor = entryClass.getConstructor(String.class, Timestamp.class, int.class, DataType.class, String.class, String.class, int.class, int.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the id of the DataType
     *
     * @return int id of the DataType
     */
    public int getId() {
        return id;
    }

    /**
     * Get the config name of the DataType
     *
     * @return String config name
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * Get the class to be used for DataEntry
     *
     * @return String name of entry class
     */
    public Class<?> getEntryClass() {
        return entryClass;
    }

    /**
     * Get a matching DataType from the supplied config name
     *
     * @param name DataType config name to search for
     * @return {@link DataType}
     */
    public static DataType fromName(String name) {
        return nameMapping.get(name);
    }

    /**
     * Get the DataType from the supplied  id (Constant time)
     *
     * @param id DataType id to get
     * @return {@link DataType}
     */
    public static DataType fromId(int id) {
        return idTable[id];
    }

    /**
     * Check if the DataType can be rolled back
     *
     * @return true if it can be, false if not
     */
    public boolean canRollback() {
        return canRollback;
    }

    /**
     * Check if the DataType can be used in 'here' searches
     *
     * @return true if it can be, false if not
     */
    public boolean canHere() {
        return canHere;
    }

    public Constructor<? extends DataEntry> getEntryConstructor() {
        return entryConstructor;
    }

    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Reloads enum's isLogged values from disk
     */
    public void reload() {
        this.isLogged = HawkEye.getInstance().getConfig().getBoolean("log." + configName);
    }
}
